(ns demo.service.pixi
  (:require
   [transit.io :refer [decode]]
   [rtable.plot.pixi :refer [pixi-ds]]
   [demo.env :refer [env]]))

(def opts {:style {:width "100%"
                   :height "800px"
                   :border "3px solid green"}
           :class "bg-red-500"
           :charts [[;:bar :candlestick ; :ohlc 
                                        ;:close {:type :line}
                     {:type :line :col :daily-atr-lower :color "blue-3"}
                     {:type :line :col :daily-atr-upper :color "blue-7"}
                     {:type :range :col [:daily-atr-lower :daily-atr-upper] :color "red-3"}
                     {:type :line :col :p0-low  :color "red-3"}
                     {:type :line :col :p1-low  :color "red-4"}
                     {:type :line :col :pweek-low :color "red-5"}
                     {:type :line :col :p0-high :color "red-6"}
                     {:type :line :col :p1-high :color "red-7"}
                     {:type :line :col :pweek-high :color "red-8"}]
                    [{:type :line :col :daily-atr-mid :color "green-5"}
                                                                 ;:volume :column
                     ]
                    [;:low :column
                     {:type :line :col :low :color "green-2"}
                     {:type :point :col :high :color "green-9"}]]})

(defn pixi-static []
  (let [ds (-> "resources/public/bars-1m-full.transit-json"
               slurp
               decode)]
    (pixi-ds env opts ds)))


