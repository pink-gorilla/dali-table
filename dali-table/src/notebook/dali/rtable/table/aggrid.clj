(ns notebook.dali.rtable.table.aggrid
  (:require
   [rtable.plot :as plot]
   [notebook.dali.rtable.random-bars :refer [random-bar-ds]]
   ))

(def ds (random-bar-ds 200))

(def opts
  {:style {:width "100%"} ; make it as wide as the notebook-viewer
   :timezone "America/Panama"
   :columns [{:field :date}
             {:field :open
              :cellStyle {:color "red" :background-color "green"}
              :resizable true}
             {:field :high
              :type "rightAligned"
              :resizable true
              ;:valueGetter: p => p.data.athlete
              ; A Value Getter is a function that gets called for each row to return the Cell Value for a Column. 
              }
             {:field :low :width 70
              :cellClass "shaded-class"
              :resizable true}
             {:field :close :header "C" :resizable true
              ;:cellClassRules {"bg-blue-500" (fn [p]
              ;                                 (println "ccr: " p)
              ;                                 (nil? (.-value p)))}
              }
             {:field :volume :width 70
              :resizable true
              :cellStyle {:fontWeight "bold"}}]})

(plot/aggrid-ds opts ds)

