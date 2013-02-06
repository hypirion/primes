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
        createAllUnder(v);
        int i = Collections.binarySearch(primeList, v);
        if (i < 0)
            i = ~i;
        int p = primeList.get(i);
        if (v <= p)
            p = primeList.get(i-1);
        return p;
    }

    public static boolean isPrime(long n){
        if (n == 1)
            return false;

        int sqrt = (int) Math.sqrt(n);
        createAllUnder(sqrt+1);

        for (int prime : primeList){
            if (sqrt < prime)
                break;
            else if (n % prime == 0)
                return false;
        }

        return true;
    }

    private static void ensureExists(int n){
        while (size <= n)
            addPrime();
    }

    private static void createAllUnder(int n){
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
