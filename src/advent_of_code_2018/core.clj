(ns advent-of-code-2018.core
  (:require [clojure.test]))

(defn seq-contains?
  {:test (fn []
           (clojure.test/is (seq-contains? [1 2 3] 2))
           (clojure.test/is (not (seq-contains? [1 2 3] 4))))}
  [coll x]
  (boolean (some (fn [item]
                   (= item x))
                 coll)))
