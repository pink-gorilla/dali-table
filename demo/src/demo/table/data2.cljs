(ns demo.table.data2
  (:require
   [reagent.core :as r]))

(defn person [i]
  {:id i
   :first-name "John"
   :last-name (str "Doe " i)
   :age (rand 100)
   :sex (if (> 0.5 (rand))
          :man
          :woman)})

(def table-data2
  (r/atom
   (into []
         (map person (range 1000)))))