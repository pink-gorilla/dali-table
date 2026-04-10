(ns notebook.dali.rtable.chart.vegalite
  (:require
   [rtable.plot.vega :refer [vega vegalite]]))

;; point plot
(vegalite  {:data {:values [{:x 3 :y 4}
                            {:x 7 :y 1}
                            {:x 5 :y 7}
                            {:x 2 :y 6}]}
            :mark :point
            :encoding {:x {:field :x :type :quantitative}
                       :y {:field :y :type :quantitative}}})

;; bar plot
(def data
  {:values [{:a "A" :b 28} {:a "B" :b 55} {:a "C" :b 43} {:a "D" :b 91}
            {:a "E" :b 81} {:a "F" :b 53} {:a "G" :b 19} {:a "H" :b 87}
            {:a "I" :b 52} {:a "J" :b 127}]})

(vegalite  {;:$schema "https://vega.github.io/schema/vega-lite/v4.json"
            :description "A simple bar chart with embedded data."
            :mark {:type "bar"
                    ;:tooltip true
                   :tooltip {:content "data"}}
            :encoding {:x {:field "a" :type "ordinal"}
                       :y {:field "b" :type "quantitative"}}
            :data data})



(vegalite
 {:data {:url "/r/data/sp500.csv"}
  :vconcat
  [{;:width 480
    :mark "area"
    :encoding {:x {:field "date"
                   :type "temporal"
                   :scale {:domain {:selection "brush"}}
                   :axis {:title ""}}
               :y {:field "price", :type "quantitative"}}}
   {;:width 480
    :height 60
    :mark "area"
    :selection
    {:brush {:type "interval", :encodings ["x"]}}
    :encoding {:x {:field "date", :type "temporal"}
               :y
               {:field "price"
                :type "quantitative"
                :axis {:tickCount 3, :grid false}}}}]})


(vegalite
 {:embed/opts {:width 1400 :height 600}
  :description "Stock prices of 5 Tech Companies over Time."
  :data {:url "/r/data/stocks.csv"}
  :transform [;{:filter "datum.symbol==='GOOG'"},
              {:filter {:field "date", :timeUnit "year", :range [2007, 2010]}}]
  :mark "line"
  :selection   {:brush {:type "interval"}} ; :encodings ["x"]
  :encoding  {;:row {:field "symbol" :type "nominal"}
    ;:x {:field "date", :type "temporal"   :scale {:domain {:selection "brush"}}}
              :x {:field "date" :type "temporal"
                  :axis {:tickCount 8
                         :labelAlign "left"
                         :labelExpr "[timeFormat(datum.value, '%b'), timeFormat(datum.value, '%m') == '01' ? timeFormat(datum.value, '%Y') : '']"
                         :labelOffset 4
                         :labelPadding -24
                         :tickSize 30
                         :gridDash {:condition {:test {:field "value" :timeUnit "month", :equal 1}, :value []}
                                    :value [2,2]}
                         :tickDash {:condition {:test {:field "value", :timeUnit "month", :equal 1}, :value []}
                                    :value [2,2]}}}
              :y {:field "price", :type "quantitative"}
              :color {:field "symbol", :type "nominal"}}})
