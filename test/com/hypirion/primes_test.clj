(ns com.hypirion.primes-test
  (:use clojure.test
        com.hypirion.pregenerated-primes)
  (:require [com.hypirion.primes :as p]))

(deftest serial-check
  (doseq [prime hundred-thousand]
    (is (p/prime? prime))))
