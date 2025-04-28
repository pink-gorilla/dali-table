(ns demo.repl
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [transit.cljs-ajax :refer [GET]]
   [cquant.tmlds :refer [ds-txt]]
   [rtable.data :refer [load-dataset]]))

; cljs repl is on port 8002

(def ds
  (tmlds/->dataset
   {:a (range 100)
    :b (take 100 (cycle [:a :b :c]))
    :c (take 100 (cycle ["one" "two" "three"]))}))

ds

(tmlds/rows ds)
;; => #reader[[:persistent-map 100][{:a 0, :b :a, :c "one"} {:a 1, :b :b, :c "two"} {:a 2, :b :c, :c "three"} {:a 3, :b :a, :c "one"} {:a 4, :b :b, :c "two"} {:a 5, :b :c, :c "three"} {:a 6, :b :a, :c "one"} {:a 7, :b :b, :c "two"} {:a 8, :b :c, :c "three"} {:a 9, :b :a, :c "one"} ... {:a 90, :b :a, :c "one"} {:a 91, :b :b, :c "two"} {:a 92, :b :c, :c "three"} {:a 93, :b :a, :c "one"} {:a 94, :b :b, :c "two"} {:a 95, :b :c, :c "three"} {:a 96, :b :a, :c "one"} {:a 97, :b :b, :c "two"} {:a 98, :b :c, :c "three"} {:a 99, :b :a, :c "one"}]]

(->> (tmlds/rows ds)
     (into []))

(tmlds/columns ds)
;; => (#column[[:float64 100][0 1 2 3 4 5 6 7 8 9 ... 90 91 92 93 94 95 96 97 98 99]] #column[[:keyword 100][:a :b :c :a :b :c :a :b :c :a ... :a :b :c :a :b :c :a :b :c :a]] #column[[:string 100][one two three one two three one two three one ... one two three one two three one two three one]])

;(tmlds/)

(-> (load-dataset  "/r/bars-1m-full.transit-json")
    (p/then (fn [ds]
              (println "YES. SUCCESS!"))))

; ;"/r/signal-no-date.transit-json"
