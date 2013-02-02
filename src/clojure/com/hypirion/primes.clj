(ns com.hypirion.primes
  (:import [com.hypirion.primes Primes])
  (:refer-clojure :exclude [take get]))

(defn primes
  "Returns a lazy sequence of all primes in sorted order."
  []
  (seq Primes/PRIME))

(defn prime?
  "Checks whether an integer (long or lower) is a prime. Returns true if yes,
  false otherwise."
  [^long n]
  (Primes/isPrime n))

(defn take
  "Returns the n first primes as an unmodifiable list."
  [n]
  (Primes/take n))

(defn get
  "Returns the nth prime number."
  [n]
  (Primes/get n))

(defn take-below
  "Returns all primes below n as an unmodifiable list."
  [n]
  (Primes/takeUnder n))