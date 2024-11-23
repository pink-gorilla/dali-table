(ns rtable.snippet.highstock
  (:require
   [reval.core :refer [*env*]]
   [rtable.plot :as plot]
   [rtable.snippet.data.random-bars :refer [random-bar-ds]]))

(def ds (random-bar-ds 200))

ds

(plot/highstock-ds
 *env*
 {:charts [{:bar {:type :ohlc
                  :mode :candle}
            :close :line}
           {:volume :column}
           {:close :line}]}
 ds)