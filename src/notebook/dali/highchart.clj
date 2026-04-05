(ns notebook.dali.highchart
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [notebook.dali.random-bars :refer [random-bar-ds]]
   ))

(def ds
  (random-bar-ds 100))


(plot/highstock-ds
  {:style {:width "600px"
           :height "300px"}
   :charts [{:close {:type :line}
             :bar {:type :ohlc
                   :mode :candle}}
            {:volume :column}]}
  ds)



