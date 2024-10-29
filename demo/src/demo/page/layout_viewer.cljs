(ns demo.page.layout-viewer
  (:require
   [ui.flexlayout :refer [create-model layout add-node]]
   [demo.helper.daliclj] ; side-effects to register components
   ))

(def model
  {:global {:tabEnableRename true
            :tabEnableClose true
            :tabEnableFloat true
            :tabSetEnableActiveIcon true}
   :layout {:type "row"
            :weight 100
            :children [{:type "tabset"
                        :weight 50
                        :children [{:type "tab"
                                    :name "One"
                                    :component "panel"
                                    :enableClose false,
                                    :icon "/r/images/bar_chart.svg"}]}
                       {:type "tabset"
                        :weight 100
                        :selected 1
                        :children
                        [{:type "tab"
                          :name "wikipedia"
                          :component "url"
                          :icon "/r/images/add.svg"
                          :helpText "this tab has helpText defined"
                          :id "wikipedia1"}
                          {:type "tab"
                          :name "text"
                          :id "text99"
                          :component "text"}
                         
                         {:type "tab"
                          :name "cheetah"
                          :component "viewer"
                          :icon "/r/images/article.svg"
                          :id "cheetah"}
                         {:type "tab"
                          :name "aggrid"
                          :component "viewer"
                          :id "aggrid"}
                         {:type "tab"
                          :name "highcharts"
                          :component "viewer"
                          :id "highcharts"}
                         {:type "tab"
                          :name "pixi"
                          :component "viewer"
                          :id "pixi"}
                        ]}]}
   :borders [{:type "border"
              ;:selected 13,
              :size 350
              :location "left"
              :children [{:type "tab"
                          :id "#1"
                          :name "Options"
                          :component "option"
                          :enableClose false}]}]})

(def m (create-model
        {:model model
         :options {"wikipedia1" "https://en.wikipedia.org/wiki/Main_Page"
                   "cheetah" {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
                              :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
                              :data {:load {:url  "/r/bars-1m-full.transit-json"}
                                     :style {:width "100%" :height "100%"}
                                     :columns [; bar
                                               {:field "asset" :caption "a" :width 90}
                                               {:field "date" :caption "d" :width 220
                                                       ;:style 'demo.page.cheetah/bad-fn
                                                }
                                               {:field "open" :caption "o" :width 90
                                                :style 'demo.helper.format2/red-color}
                                               {:field "high" :caption "h" :width 90
                                                :format 'demo.helper.format2/format-hidden}
                                               {:field "low" :caption "l" :width 90
                                                          ;; this namespace does not work.
                                                :format 'demo.helper.format2/format-number
                                                :format-args ["Low: %.5f"]}
                                               {:field "close" :caption "c" :width 90
                                                :style 'demo.helper.format2/blue-color
                                                          ;:format format
                                                :format-args "Cost: %.2f"}
                                               {:field "volume" :caption "v" :width 90}
                                                      ;
                                               {:field "atr-band-lower" :caption "BL" :width 50}
                                               {:field "atr-band-upper" :caption "BU" :width 50}
                                               {:field  "doji" :caption "doji" :width 50}
                             
                                               {:field  "below-band" :caption "B?" :width 50
                                                :style {:bgColor "#5f5"}}
                                               {:field  "cross-down" :caption "XD" :width 50}
                                               {:field  "cross-down-c" :caption "XD_" :width 50}
                                               {:field  "long-signal" :caption "LS" :width 50}
                             
                                               {:field  "above-band" :caption "A?" :width 50 :format 'demo.helper.format2/format-bool}
                                               {:field  "cross-up" :caption "XU" :width 50 :format 'demo.helper.format2/format-bool}
                                               {:field  "cross-up-c" :caption "XU_" :width 50
                                                :style 'demo.helper.format2/bool-color
                                                :format 'demo.helper.format2/format-bool2
                                                :format-args [false]}
                                               {:field  "short-signal" :caption "SS" :width 50
                                                :format 'demo.helper.format2/format-bool2
                                                :format-args [true]}
                                               {:field "entry" :caption "entry" :width 50}]}}
                   "aggrid" {:viewer-fn 'rtable.viewer.aggrid/aggrid
                            :transform-fn 'rtable.transform.aggrid/load-and-transform-aggrid
                            :data {:load {:url "/r/signal-no-date.transit-json"}
                                   :style {:width "100%" :height "100%"}
                                   :columns [{:field "close" :header "C" :resizable true}
                                             {:field "spike"
                                              :cellStyle {:color "red" :background-color "green"}
                                              :resizable true}
                                             {:field "doji"
                                              :type "rightAligned"
                                              :resizable true
                                                                        ;:valueGetter: p => p.data.athlete
                                                                        ; A Value Getter is a function that gets called for each row to return the Cell Value for a Column. 
                                              }
                                             {:field "spike-doji" :width 70
                                              :resizable true}
                                             {:field "doji-v" :width 70
                                              :cellClass "shaded-class"
                                              :resizable true}
                                             {:field "spike-doji-v" :width 70 :resizable true}
                                             {:field "long" :width 70
                                              :resizable true
                                              :cellStyle {:fontWeight "bold"}}
                                             {:field "short" :width 70
                                              :resizable true
                                              :cellClassRules {"bg-blue-500" (fn [p]
                                                                               ;(println "ccr: " p)
                                                                               (nil? (.-value p)))}}]}}
                   "highcharts" {:viewer-fn 'rtable.viewer.highcharts/highstock
                                 :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
                                 :data {:load {:url  "/r/bars-1m-full.transit-json"}
                                        :style {:width "100%"
                                                :height "100%"
                                                :border "3px solid blue"
                                                :overflow-y "scroll"
                                                }
                                        :class "bg-red-500"
                                        :charts [{:bar {:type :ohlc
                                                        :mode :candle} ; :ohlc 
                                                  :close {:type :line}}
                                                 {:volume :column}
                                                 {:volume :point}
                                                 ]}}
                   "pixi"  {:viewer-fn 'rtable.viewer.pixi/pixi
                           :transform-fn 'rtable.transform.pixi/load-and-transform-pixi
                           :data {:load {:url  "/r/bars-1m-full.transit-json"}
                                  :style {:width "100%"
                                          :height "100%"
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
                                            {:type :point :col :high :color "green-9"}]]}}
                   "text99" "hello\r\nI come from the options!"}}))



(defn page [{:keys [route-params query-params handler] :as route}]
  [:div.h-screen.w-screen
   {:style {:display "flex"
            :flex-direction "column"
            :flex-grow 1}}
   [:div {:dir "ltr"
          :style {:margin "2px"
                  :display "flex"
                  :align-items "center"}}]

   [:div {:style {:display "flex"
                  :flex-grow "1"
                  :position "relative"
                  :border "1px solid #ddd"}}
    [layout m]]])