# primes
[![Build Status](https://travis-ci.org/hyPiRion/primes.png)](https://travis-ci.org/hyPiRion/primes)

Fetch, locate and use prime numbers with this library. There's nothing more to
it than that.

The library is designed to rapidly generate primes by being a Clojure-wrapper on
top of a thread-safe Java class. You don't have to worry about anything, just
ride the wave and enjoy the view.

## Usage

To use primes within your own Clojure programs and libraries, add this to
your `project.clj` dependencies:

```clj
[com.hypirion/primes "0.2.1"]
```

From there, it's really easy to get working with primes. Have a look at
[the documentation][doc] for a list of different functions.

[doc]: http://hypirion.github.com/primes/0.2.0/com.hypirion.primes.html

### Example usage

```clj
(require '[com.hypirion.primes :as p])

(p/prime? 13)
#_=> true

(p/get 543210) ;; The 543210th prime, 0-indexed
#_=> 8054927

(p/take-below 100) ;; All primes below 100
#_=> #<UnmodifiableRandomAccessList [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 
    37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]>

(p/take 20) ;; The 20 first primes
#_=> #<UnmodifiableRandomAccessList [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31,
    37, 41, 43, 47, 53, 59, 61, 67, 71]>

;; Lazy sequence of all primes ending with 1
(filter #(= 1 (mod % 10)) (p/primes))
#_=> (11 31 41 61 71 101 131 151 181 191 ...)
```

## License

Copyright © 2013 Jean Niklas L'orange

Distributed under the Eclipse Public License, the same as Clojure.
