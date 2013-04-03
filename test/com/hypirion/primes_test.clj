(ns com.hypirion.primes-test
  (:use clojure.test)
  (:require [com.hypirion.primes :as p]
            [clojure.java.io :as io])
  (:import [com.hypirion.primes Primes]))

(def hundred-thousand
  (-> "pregen.txt" io/resource slurp read-string))

(deftest serial-prime?-check
  (Primes/clear)
  (doseq [prime hundred-thousand]
    (is (p/prime? prime))))

(deftest serial-take
  (Primes/clear)
  (dorun
   (map #(is (= %1 %2))
        (p/take 100008) hundred-thousand)))

(deftest serial-primes
  (Primes/clear)
  (dorun
   (map #(is (= %1 %2))
        (p/primes) hundred-thousand)))

(deftest proper-factorization
  (Primes/clear)
  (dotimes [n 100000]
    (let [factorized (Primes/factorize n)]
      (is (= n (reduce * factorized))))))
