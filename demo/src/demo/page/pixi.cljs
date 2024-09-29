(ns demo.page.pixi
  (:require
   [tech.v3.dataset :as tmlds]
   [rtable.render.pixi :refer [pixi pixi-ds]]
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
   [:p.bg-red-500.p-3 "rtable.render.pixi"]
   [sample-selector
    {:test-dataset
     [pixi {:style {:width "1000px"
                    :height "500px"}
            :data ds}]
     :big-datasset
     [pixi-ds {:style {:width "1200px"
                       :height "800px"
                       :border "3px solid green"}
               :class "bg-red-500"
               :charts [{;:bar :candlestick ; :ohlc 
                         ;:close {:type :line}
                         ;
                         :daily-atr-lower {:type :line :color "blue-3"}
                         :daily-atr-upper {:type :line :color "blue-7"}
                         :p0-low {:type :line :color "red-3"}
                         :p1-low {:type :line :color "red-4"}
                         :pweek-low {:type :line :color "red-5"}
                         :p0-high {:type :line :color "red-6"}
                         :p1-high {:type :line :color "red-7"}
                         :pweek-high {:type :line :color "red-8"}
                         }
                        {:daily-atr-mid {:type :line :color "green-5"}
                         ;:volume :column
                         
                         }
                        {;:low :column
                         :low {:type :line :color "green-9"}

                         }]
               :url  "/r/bars-1m-full.transit-json"}]
     ;
     }]])


