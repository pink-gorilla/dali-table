(ns demo.page.viewer.highcharts
  (:require
   [reagent.core :as r]
   [dali.viewer :refer [viewer2]]
   [rtable.viewer.highcharts :refer [highchart highstock]]
   [demo.highcharts.spec :refer [highchart-spec]]
   [demo.highcharts.spec-annotations :as annotations]
   [demo.helper.ui :refer [sample-selector]]))

;   [link-dispatch [:css/set-theme-component :highcharts true] "default"]
;[link-dispatch [:css/set-theme-component :highcharts "dark-unica"] "dark-unica"]
;[link-dispatch [:css/set-theme-component :highcharts "grid-light"] "grid-light"]
;[link-dispatch [:css/set-theme-component :highcharts "sand-signika"] "sand-signika"]



(defn page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
   [sample-selector
    {:lg-new
     [highchart {:style {:width "100%"
                         :height "100%"}
                 :data-js (clj->js highchart-spec)}]
     :annotation
     [highchart {:style {:width "300px"
                         :height "300px"}
                 :class "bg-red-300"
                 :dynamic-height false
                 :data-js (clj->js annotations/spec)}]
     :annotation-big
     [highchart {:style {:width "100%"
                         :height "100%"}
                 :class "bg-red-500"
                 :dynamic-height false
                 :data-js (clj->js annotations/spec)}]
     :bollinger-band-only
     [viewer2 {:viewer-fn 'rtable.viewer.highcharts/highstock
               :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
               :data {:load {:url  "/r/bars-1m-full.transit-json"}
                      :style {:width "100%"
                              :height "100%"
                              :border "3px solid green"}
                      :class "bg-red-500"
                      :charts [{:bar {:type :ohlc
                                      :mode :candle}
                                :close {:type :line}}]}}]

     :bollinger-volume
     [viewer2 {:viewer-fn 'rtable.viewer.highcharts/highstock
               :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
               :data {:load {:url  "/r/bars-1m-full.transit-json"}
                      :style {:width "100%"
                              :height "100%" ; 600 + (100 + 100) + 100
                              :border "3px solid green"
                              :overflow-y "scroll"
                              :overflow-x "hidden"}
                      :class "bg-red-500"
                      :charts [{:bar {:type :ohlc
                                      :mode :candle} ; :ohlc 
                                :close {:type :line}}
                               {:volume :column}]}}]


     ;
     }]])

(defn highchart-full-page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
   [highstock {:style {:width "100%"
                       :height "100%"}
               :data-js (clj->js highchart-spec)}]])
