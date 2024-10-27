(ns demo.service.highcharts
  (:require
   [tick.core :as t]
   [tech.v3.dataset :as ds]
   [tablecloth.api :as tc]
   [rtable.plot.highcharts :refer [highstock-ds]]
   [dali.spec :refer [create-dali-spec]]
   ))

(def opts {:style {:width "1200px"
                   :height "800px"
                   :border "3px solid green"}
           :class "bg-red-500"
           :charts [{;:bar :candlestick ; :ohlc 
                     :close {:type :line}
                     ; band
                     :atr-band-mid {:type :point :color "orange"}
                     :atr-band-lower {:type :line :color "black"}
                     :atr-band-upper {:type :line :color "black"}
                                                   ;pivots
                                                   ;:p0-low {:type :step :color "red"}
                                                   ;:p1-low {:type :step :color "red"} ; :step does not work with gaps.
                                                   ;:pweek-low {:type :step :color "red"} ; :step does not work with gaps.
                                                   ;:p0-high {:type :step :color "red"}
                                                   ;:p1-high {:type :step :color "red"} ; :step does not work with gaps.
                                                   ;:pweek-high {:type :step :color "red"} ; :step does not work with gaps.
                     }
                    {:volume :column}
                    {:low :column}]
           :load {:url  "/r/bars-1m-full.transit-json"}})

(defn highstock-static []
   (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data opts}))


