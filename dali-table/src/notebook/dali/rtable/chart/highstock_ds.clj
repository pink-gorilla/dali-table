(ns notebook.dali.rtable.chart.highstock-ds
  (:require
   [rtable.plot :as plot]
   [notebook.dali.rtable.random-bars :refer [random-bar-ds]]))

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



