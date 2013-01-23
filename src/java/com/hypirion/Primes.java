package com.hypirion.primes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class Primes {
    private static ArrayList<Integer> primeList = new ArrayList<Integer>
        (Arrays.asList(new Integer[]{2, 3, 5, 7, 11}));

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
        for (int i = primeList.size(); i <= n; i++)
            addPrime();
    }

    private static void createAllUnder(int n){
        while (primeList.get(primeList.size() -1) < n)
            addPrime();
    }

    private static void addPrime(){
        int possiblePrime = primeList.get(primeList.size() - 1);

        do possiblePrime += relativeNext();
        while (!isPrime(possiblePrime));

        primeList.add(possiblePrime);
    }

    static final int[] wheel = {2, 4, 2, 4, 6, 2, 6, 4, 2, 4, 6,
                                6, 2, 6, 4, 2, 6, 4, 6, 8, 4, 2,
                                4, 2, 4, 8, 6, 4, 6, 2, 4, 6, 2,
                                6, 6, 4, 2, 4, 6, 2, 6, 4, 2, 4,
                                2, 10, 2, 10};

    static int pos = 0;

    private static int relativeNext(){
        int n = wheel[pos];
        pos++;
        if (pos == wheel.length)
            pos = 0;
        return n;
    }
}
