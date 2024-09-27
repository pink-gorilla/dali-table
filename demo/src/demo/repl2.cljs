(ns demo.repl2
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [tick.core :as t]
   [tech.v3.dataset :as tmlds]
   ;[cquant.tmlds :refer [GET ds-txt]]
   [rtable.highchart-timeseries.core :refer [highchart-spec]]
   [rtable.data.highcharts.data :as d]
   [rtable.data.highcharts.util :refer [chart->series]]))

(highchart-spec
 {:charts [{:bar :ohlc
              ; band
            :atr-band-mid {:type :point :color "orange"}
            :atr-band-lower {:type :line :color "black"}
            :atr-band-upper {:type :line :color "black"}
              ;pivots
            :p0-low {:type :step :color "red"}
            :p1-low {:type :step :color "red"} ; :step does not work with gaps.
            :pweek-low {:type :step :color "red"} ; :step does not work with gaps.
            :p0-high {:type :step :color "red"}
            :p1-high {:type :step :color "red"} ; :step does not work with gaps.
            :pweek-high {:type :step :color "red"} ; :step does not work with gaps.
            }
           {:volume :column}
           {:atr :column}]})

;(def ds-a (GET "/r/bars-1m-full.transit-json"))

;@ds-a

;(meta @ds-a)

;(meta (:date @ds-a))

;(def ds
;  (tmlds/column-map @ds-a :epoch #(-> % t/instant t/long) [:date]))

(def ds
  (tmlds/->dataset
   {:date (take 4 (cycle [(t/instant) (t/instant)]))
    :a (range 4)
    :b (take 4 (cycle [10 20 30]))
    :c (take 4 (cycle ["one" "two" "three"]))}))

(def ds-epoch
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

ds-epoch

(def ts-spec
  {:charts [{:zzz {:type :ohlc}
             :a {:type :point}
             :b {:type :line}}
            {:c {:type :column}}]})

(def spec (highchart-spec ts-spec))

(def spec-js (clj->js spec))

spec-js

(.-series spec-js)

(chart->series (:charts ts-spec))
;; => ({:axis 0, :column :a, :type :point} 
;;     {:axis 0, :column :b, :type :line} 
;;     {:axis 1, :column :c, :type :column})

(d/cols->series ds-epoch [:epoch :a])

(d/add-series-to-spec-js spec-js ds-epoch (:charts ts-spec))

ds
(col->series ds :close)

(d/set-series-data spec-js 0 ds :close)
(set-series-data spec-js 4 ds :close)

spec-js
