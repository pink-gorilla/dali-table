(ns demo.page.highcharts
  (:require
   [reagent.core :as r]
   [dali.viewer :refer [viewer2]]
   [rtable.viewer.highcharts :refer [highstock]]
   [demo.highcharts.spec :refer [highchart-spec]]
   [demo.highcharts.spec-annotations :as annotations]
   [demo.helper.ui :refer [link-href link-dispatch sample-selector]]))

(defn highchart-page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
   [:div
    [link-href "/" "main"]
    [:p "highchart with different sizes"]]
   [link-dispatch [:css/set-theme-component :highcharts true] "default"]
   [link-dispatch [:css/set-theme-component :highcharts "dark-unica"] "dark-unica"]
   [link-dispatch [:css/set-theme-component :highcharts "grid-light"] "grid-light"]
   [link-dispatch [:css/set-theme-component :highcharts "sand-signika"] "sand-signika"]

    ;; new version that provides :style and :class
   [:p.bg-red-500.p-3 "NEW rtable.viewer.highcharts"]
   [sample-selector
    {:lg-new
     [highstock {:style {:width "1000px"
                         :height "500px"}
                 :data-js (clj->js highchart-spec)}]
     :annotation-new
     [highstock {:style {:width "500px"
                         :height "500px"}
                 :class "bg-red-300"
                 :data-js (clj->js  annotations/spec)}]
     :annotation-new-big
     [highstock {:style {:width "1000px"
                         :height "500px"}
                 :class "bg-red-500"
                :data-js (clj->js  annotations/spec)}]
     :bollinger-band-only
     [viewer2 {:viewer-fn 'rtable.viewer.highcharts/highstock
              :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
              :data {:load {:url  "/r/bars-1m-full.transit-json"}
                     :style {:width "1200px"
                             :height "800px"
                             :border "3px solid green"}
                     :class "bg-red-500"
                     :charts [{;:bar :candlestick ; :ohlc 
                               :close {:type :line}
                               ; band
                               :atr-band-mid {:type :point :color "orange"}
                               :atr-band-lower {:type :line :color "black"}
                               :atr-band-upper {:type :line :color "black"}}]}}]
     :bollinger-volume
     [viewer2 {:viewer-fn 'rtable.viewer.highcharts/highstock
              :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
              :data {:load {:url  "/r/bars-1m-full.transit-json"}
                     :style {:width "1200px"
                            :height "950px" ; 600 + (100 + 100) + 100
                            :border "3px solid green"}
                    :class "bg-red-500"
                    :charts [{:bar {:type :ohlc} ; :ohlc 
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
                             {:volume :column}]}}]
     
     :bollinger-volume-2
      [viewer2 {:viewer-fn 'rtable.viewer.highcharts/highstock
               :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
               :data {:load {:url  "/r/bars-1m-full.transit-json"}
                      :style {:width "1200px"
                            :height "700px" ; 600 + (100 + 100) + 100
                            :border "3px solid green"
                            :overflow-y "scroll"
                            :overflow-x "hidden"
                            }
                    :class "bg-red-500"
                    :charts [{:bar {:type :ohlc} ; :ohlc 
                              :close {:type :line}
                              ; band
                              :atr-band-mid {:type :point :color "orange"}
                              :atr-band-lower {:type :line :color "black"}
                              :atr-band-upper {:type :line :color "black"}}
                             {:volume :column}
                             ]}}]


     ;
     }]])

(defn highchart-full-page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
    [highstock {:style {:width "100%"
                        :height "100%"}
                :data highchart-spec}]])
