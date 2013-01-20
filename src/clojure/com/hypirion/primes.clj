(ns com.hypirion.primes
  (:import [com.hypirion.primes Primes])
  (:refer-clojure :exclude [take get]))

(defn prime? [^long n]
  (Primes/isPrime n))

(defn take [n]
  (Primes/take n))

(defn get [n]
  (Primes/get n))

(defn take-below [n]
  (Primes/takeUnder n))
