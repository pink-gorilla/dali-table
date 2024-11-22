(ns demo.notebook.highchart
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [demo.random-bars :refer [random-bar-ds]]
   [demo.env :refer [env]]))


(def ds
  (random-bar-ds 100))


(plot/highstock-ds
 env {:style {:width "600px"
              :height "300px"}
      :charts [{:close {:type :line}
                :bar {:type :ohlc
                      :mode :candle}}
               {:volume :column}]}
 ds)



