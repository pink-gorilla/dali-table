(ns demo.page.pixi
  (:require
   [tech.v3.dataset :as tmlds]
   [rtable.viewer.pixi :refer [pixi pixi-ds]]
   [demo.helper.ui :refer [link-href link-dispatch sample-selector]]))


(def ds
  (tmlds/->dataset
   {:open [3 4 5 3 4 5 6 7 8 12 5 9]
    :high [6 6 6 7 6 7 8 9 10 14 6 10]
    :low  [3 4 5 3 4 5 6 7 8 12 5 9]
    :close [3 4 5 3 4 5 6 7 8 12 5 9]}))

(defn pixi-page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
   [link-href "/" "main"]
    ;; new version that provides :style and :class
   [:p.bg-red-500.p-3 "rtable.viewer.pixi"]
   [sample-selector
    {:test-dataset
     [pixi {:style {:width "1000px"
                    :height "500px"}
            :data ds}]
     :big-dataset
     [pixi-ds {:style {:width "1200px"
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
                         {:type :line :col :pweek-high :color "red-8"}
                         ; :doji :below-band :cross-down :cross-down-c 
                         ; :long-signal :above-band :cross-up :cross-up-c :short-signal :entry
                         {:type :signal :col :doji :color "slate-5"}
                         {:type :signal :col :cross-down-c :color "red-8"}
                         {:type :signal :col :cross-up-c :color "red-8"}
                         {:type :signal :col :entry :color "blue-8"}
                         ]
                        [{:type :line :col :daily-atr-mid :color "green-5"}
                                                  ;:volume :column
                         ]
                        [;:low :column
                         {:type :line :col :low :color "green-2"}
                         {:type :point :col :high :color "green-9"}]]
               :url  "/r/bars-1m-full.transit-json"}]
     :big-dataset-x-full
     [pixi-ds {:style {:width "100%"
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
                         {:type :point :col :high :color "green-9"}]]
               :url  "/r/bars-1m-full.transit-json"}]


     ;
     }]])


