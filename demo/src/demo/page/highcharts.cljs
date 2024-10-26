(ns demo.page.highcharts
  (:require
   [reagent.core :as r]
   [ui.highcharts :as highchart-old]
   [rtable.viewer.highcharts :refer [highstock highstock-ds]]
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

    ;; old version that is using pinkie-box
   [:p.bg-red-500.p-3 "OLD ui.highcharts"]
   [sample-selector
    {:sm [highchart-old/highchart {:data highchart-spec :box :sm}]
     :md [highchart-old/highchart {:data highchart-spec :box :md}]
     :lg [highchart-old/highchart {:data highchart-spec :box :lg}]
     :annotation [highchart-old/highchart {:data annotations/spec :box :lg}]}]

    ;; new version that provides :style and :class
   [:p.bg-red-500.p-3 "NEW rtable.viewer.highcharts"]
   [sample-selector
    {:lg-new
     [highstock {:style {:width "1000px"
                         :height "500px"}
                 :data highchart-spec}]
     :annotation-new
     [highstock {:style {:width "500px"
                         :height "500px"}
                 :class "bg-red-300"
                 :data annotations/spec}]
     :annotation-new-big
     [highstock {:style {:width "1000px"
                         :height "500px"}
                 :class "bg-red-500"
                 :data annotations/spec}]

     :chartspec
     [highstock-ds {:style {:width "1200px"
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
                    :url  "/r/bars-1m-full.transit-json"}]
     :chartspec-candle
     [highstock-ds {:style {:width "1200px"
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
                             {:volume :column}
                             {:low :column}]
                    :url  "/r/bars-1m-full.transit-json"}]
     
     :chartspec-scroll-panes
     [highstock-ds {:style {:width "1200px"
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
                             ]
                    :url  "/r/bars-1m-full.transit-json"}]


     ;
     }]])

(defn highchart-full-page  [{:keys [route-params query-params handler] :as route}]
  [:div
   ;[:div.text-green-300 "grid1"]
   [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
    [highchart-old/highchart {:data highchart-spec :box :fl}]]])
