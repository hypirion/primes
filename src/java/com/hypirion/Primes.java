package com.hypirion.primes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public final class Primes implements Iterable<Integer>{
    private static volatile ArrayList<Integer> primeList =
        new ArrayList<Integer>(Arrays.asList(new Integer[]{2, 3, 5, 7, 11}));
    private static volatile int size = 5;

    private static final ReentrantLock lock = new ReentrantLock();

    public static List<Integer> takeUnder(int n){
        createAllUnder(n);
        int i = Collections.binarySearch(primeList, n);
        if (i < 0)
            i = ~i;
        return take(i);
    }

    public static List<Integer> take(int n){
        ensureExists(n);
        return Collections.unmodifiableList(primeList.subList(0, n));
    }

    public static int get(int n){
        ensureExists(n);
        return primeList.get(n);
    }

    public static int lastBelow(int v){
        if (v <= 2)
            throw new RuntimeException("There are no primes below 2.");
        if (v <= primeList.get(size-1)) {
            int i = Collections.binarySearch(primeList, v);
            if (i < 0)
                i = ~i;
            int p = primeList.get(i);
            if (v <= p)
                p = primeList.get(i-1);
            return p;
        }
        else {
            int from;
            if ((v & 1) == 1)
                from = v - 2;
            else
                from = v - 1;
            for (int i = from;; i -= 2)
                if (isPrime(i))
                    return i;
        }
    }

    public static int firstAbove(int v){
        int p;
        if (v < primeList.get(size-1)) {
            int i = Collections.binarySearch(primeList, v);
            if (i < 0)
                i = ~i;
            p = primeList.get(i);
            if (p <= v){
                ensureExists(i+1);
                p = primeList.get(i+1);
            }
            return p;
        }
        else {
            int from;
            if ((v & 1) == 1)
                from = v + 2;
            else
                from = v + 1;
            for (int i = from;; i +=2)
                if (isPrime(i))
                    return i;
        }
    }

    public static boolean isPrime(long n){
        if (n == 1)
            return false;

        int sqrt = (int) Math.sqrt(n);
        for (int prime : PRIME) {
            if (sqrt < prime)
                break;
            else if (n % prime == 0)
                return false;
        }

        return true;
    }

    public static List<Long> factorize(long n) {
        ArrayList<Long> al = new ArrayList<Long>();
        if (n == 1) {
            al.add((long) 1);
            return Collections.unmodifiableList(al);
        }

        int sqrt = (int) Math.sqrt(n);

        for (int prime : PRIME) {
            if (n == 1 || sqrt < prime)
                break;
            else {
                while (n % prime == 0) {
                    n /= prime;
                    al.add((long) prime);
                }
            }
        }
        if (n != 1) {
            al.add(n);
            // This would only happen if the number is prime, right?
        }
        return Collections.unmodifiableList(al);
    }

    private static void ensureExists(int n){
        if (size <= n) {
            lock.lock();
            try {
                int toGenerate = 1 + n - size;
                int possiblePrime = primeList.get(size - 1);
                for (int i = 0; i < toGenerate; i++) {
                    do possiblePrime += relativeNext();
                    while (!isPrime(possiblePrime));

                    primeList.add(possiblePrime);
                    size++;
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    private static void createAllUnder(int n){
        if (primeList.get(size - 1) < n) {
            lock.lock();
            try {
                int possiblePrime = primeList.get(size - 1);
                while (possiblePrime <= n) {
                    do possiblePrime += relativeNext();
                    while (!isPrime(possiblePrime));

                    primeList.add(possiblePrime);
                    size++;
                }
            }
            finally {
                lock.unlock();
            }
        }
        while (primeList.get(size - 1) < n)
            addPrime();
    }

    private static void addPrime(){
        boolean gotLock = lock.tryLock();
        if (!gotLock){
            lock.lock();
        }
        try {
            if (gotLock){
                int possiblePrime = primeList.get(size - 1);

                do possiblePrime += relativeNext();
                while (!isPrime(possiblePrime));

                primeList.add(possiblePrime);
                size++;
            }
        }
        finally {
            lock.unlock();
        }
    }

    private static final int[] wheel = {2, 4, 2, 4, 6, 2, 6, 4, 2, 4, 6,
                                        6, 2, 6, 4, 2, 6, 4, 6, 8, 4, 2,
                                        4, 2, 4, 8, 6, 4, 6, 2, 4, 6, 2,
                                        6, 6, 4, 2, 4, 6, 2, 6, 4, 2, 4,
                                        2, 10, 2, 10};

    private static int pos = 0;

    private static int relativeNext(){
        int n = wheel[pos];
        pos++;
        if (pos == wheel.length)
            pos = 0;
        return n;
    }

    public static void clear(){
        primeList = new ArrayList<Integer>(Arrays.asList
                                           (new Integer[]{2, 3, 5, 7, 11}));
        pos = 0;
        size = 5;
    }

    private Primes(){}
    public static final Primes PRIME = new Primes();

    public Iterator<Integer> iterator(){
        return new PrimeIter();
    }

    private static class PrimeIter implements Iterator<Integer> {
        private int pos, atLeast;

        public PrimeIter(){
            pos = 0;
            atLeast = size;
        }

        @Override
        public boolean hasNext(){
            return true;
        }

        @Override
        public Integer next(){
            while (atLeast <= pos){
                addPrime();
                atLeast = size;
            }
            return primeList.get(pos++);
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException
                ("Can't remove primes from a prime list");
        }
    }
}
