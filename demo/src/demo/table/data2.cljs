(ns demo.table.data2
  (:require
   [reagent.core :as r]
   [ipsum :refer [random-paragraph]]
   ))

(defn person [i]
  {:id i
   :first-name "John"
   :last-name (str "Doe " i)
   :desc (random-paragraph 2)
   :age (rand 100)
   :sex (if (> 0.5 (rand))
          :man
          :woman)})

(def table-data2
  (r/atom
   (into []
         (map person (range 1000)))))