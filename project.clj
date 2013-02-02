(defproject com.hypirion/primes "0.2.0-SNAPSHOT"
  :description "Fetch, locate and use prime numbers."
  :url "http://github.com/hyPiRion/primes"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]

  :warn-on-reflection true

  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :plugins [[codox "0.6.4"]]
  :codox {:output-dir "codox"})
