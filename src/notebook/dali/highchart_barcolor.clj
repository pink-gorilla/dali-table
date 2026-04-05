(ns notebook.dali.highchart-barcolor
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [notebook.dali.random-bars :refer [random-bar-ds]]))

(def ds
  (random-bar-ds 30))

(def ds-signal
  (tc/add-column ds
                 :signal
                 (map (fn [p]
                        (if (> (rand-int 100) 90)
                          true
                          false)) (:close ds))))


 (plot/highstock-ds
   {:style {:width "600px"
               :height "300px"}
    :charts [{:bar {:type :ohlc
                    :mode :candle
                    :barcolor {:column :signal
                               :color {true "green-5"}}}}
             {:volume :column}]}
  ds-signal)

;ds-signal

(tc/select-rows ds-signal #(:signal %))
