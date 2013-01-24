(ns com.hypirion.primes-test
  (:use clojure.test
        com.hypirion.pregenerated-primes)
  (:require [com.hypirion.primes :as p])
  (:import [com.hypirion.primes Primes]))

(deftest serial-prime?-check
  (Primes/clear)
  (doseq [prime hundred-thousand]
    (is (p/prime? prime))))

(deftest serial-take
  (Primes/clear)
  (doall
   (map #(is (= %1 %2))
        (p/take 100008) hundred-thousand)))

(deftest serial-primes
  (Primes/clear)
  (doall
   (map #(is (= %1 %2))
        (p/primes) hundred-thousand)))
