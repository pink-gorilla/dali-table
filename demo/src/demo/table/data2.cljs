(ns demo.table.data2
  (:require
   [reagent.core :as r]
   [ipsum :refer [random-paragraph]]))

(defn person [i]
  {:id i
   :first-name "John"
   :last-name (str "Doe " i)
   :desc (random-paragraph 2)
   :age (rand 100)
   :sex (if (> 0.5 (rand))
          :man
          :woman)
   :x (rand 100)
   :y (rand 100)
   :z (rand 100)
   :a (rand 100)
   :b (rand 100)
   :c (rand 100)
   :1 (rand 100)
   :2 (rand 100)
   :3 (rand 100)})

(def table-data
  (into []
        (map person (range 1000))))

(def table-data2
  (r/atom table-data))