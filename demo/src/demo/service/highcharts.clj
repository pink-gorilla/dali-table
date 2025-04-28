(ns demo.service.highcharts
  (:require
   [dali.spec :refer [create-dali-spec]]
   [rtable.plot.highcharts :refer [highstock-ds]]
   [demo.env :refer [env]]
   [demo.random-bars :refer [random-bar-ds]]))

(def opts {:style {:width "100%"
                   :height "100%"
                   :border "3px solid green"}
           :class "bg-red-500"
           :charts [{:close {:type :line}
                     :bar {:type :ohlc
                           :mode :candle} ; :ohlc 
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
                     :entry {:type :flags
                             :fillColor "black"
                             :width 10
                             :height 10
                             :v2style {:long "url(/r/flags/arrow-up.svg)"
                                       true "url(/r/flags/arrow-down.svg)" ;"flags
                                       :short "url(/r/flags/arrow-down.svg)"}}}
                    {:volume :column}
                    {:low :column}]
           :load {:url  "/r/bars-1m-full.transit-json"}})

(defn highstock-static []
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data opts}))

(defn random-bars []
  (let [ds (random-bar-ds 300)]
    (highstock-ds env {:charts [{:close {:type :line}
                                 :bar {:type :ohlc
                                       :mode :candle}}
                                {:volume :column}]}
                  ds)))

(comment
  (random-bars)

; 
  )
