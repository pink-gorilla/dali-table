(ns demo.page.layout
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
                          :name "cheetah"
                          :component "clj"
                          :icon "/r/images/article.svg"
                          :id "cheetah"}
                         {:type "tab"
                          :name "aggrid"
                          :component "clj"
                          :id "aggrid"}
                         {:type "tab"
                          :name "highcharts"
                          :component "clj"
                          :id "highcharts"}
                         {:type "tab"
                          :name "highcharts-random"
                          :component "clj"
                          :id "highcharts-random"}
                         {:type "tab"
                          :name "vegalite1"
                          :component "clj"
                          :id "vegalite1"}
                         {:type "tab"
                          :name "vegalite2"
                          :component "clj"
                          :id "vegalite2"}
                         {:type "tab"
                          :name "vega"
                          :component "clj"
                          :id "vega"}
                         {:type "tab"
                          :name "pixi"
                          :component "clj"
                          :id "pixi"}
                         {:type "tab"
                          :name "rtable"
                          :component "clj"
                          :id "rtable"}
                         {:type "tab"
                          :name "text"
                          :id "text99"
                          :component "text"}]}]}
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
                   "cheetah" {:fun 'demo.service.cheetah/stock-csv}
                   "aggrid" {:fun 'demo.service.aggrid/stock-csv}
                   "highcharts" {:fun 'demo.service.highcharts/highstock-static}
                   "highcharts-random" {:fun 'demo.service.highcharts/random-bars}
                   "vegalite1" {:fun 'demo.service.vega/vegalite1}
                   "vegalite2" {:fun 'demo.service.vega/vegalite2}
                   "vega" {:fun 'demo.service.vega/vega1}
                   "pixi" {:fun 'demo.service.pixi/pixi-static}
                   "rtable" {:fun 'demo.service.rtable/rtable-data}
                   "text99" "hello\r\nI come from the options!"}}))

(defn page [{:keys [route-params query-params handler] :as route}]
  [:div.h-screen.w-screen
   {:style {:display "flex"
            :flex-direction "column"
            :flex-grow 1}}
   [:div {:dir "ltr"
          :style {:margin "2px"
                  :display "flex"
                  :align-items "center"}}

    [:button
     {:on-click #(add-node m {:component "panel"
                              :icon "/r/images/article.svg",
                              :name "panel-added"})
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add panel"]

    [:button
     {:on-click #(add-node m {:component "grid"
                              :icon "/r/images/article.svg",
                              :name "Grid-added"})
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add unknown-component"]

    [:button
     {:on-click #(add-node m {:component "data"
                              :icon "/r/images/article.svg",
                              :name "Grid-added"})
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add data"]

    [:button
     {:on-click #(add-node m {:component "url"
                              :icon "/r/images/article.svg",
                              :name "duck"
                              :options "https://kibot.com"
                              :id "duck1"})
      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add duck"]

    [:button
     {:on-click #(add-node m {:component "algo"
                              :icon "/r/images/article.svg",
                              :name "algo1"
                              :id "algo1"
                              :options {[0 :asset] "USD/JPY",
                                        [2 :trailing-n] 120,
                                        [2 :atr-n] 10,
                                        [2 :percentile] 70,
                                        [2 :step] 1.0E-4,
                                        [4 :max-open-close-over-low-high] 0.3}
                              :edit [{:type :select
                                      :path [0 :asset],
                                      :name "asset",
                                      :spec
                                      ["EUR/USD" "USD/CHF" "GBP/USD" "USD/SEK" "USD/NOK" "USD/CAD" "USD/JPY"
                                       "AUD/USD" "NZD/USD" "USD/MXN" "USD/ZAR" "EUR/JPY" "EUR/CHF" "EUR/GBP" "GBP/JPY"]}
                                     {:type :select :path [2 :trailing-n], :name "DailyLoad#", :spec [2 5 10 20 30 50 80 100 120 150]}
                                     {:type :select :path [2 :atr-n], :name "dATR#", :spec [5 10 20 30]}
                                     {:type :select :path [2 :percentile], :name "dPercentile", :spec [10 20 30 40 50 60 70 80 90]}
                                     {:type :select :path [2 :step], :name "dStep", :spec [0.001 1.0E-4 4.0E-5]}
                                     {:type :select :path [4 :max-open-close-over-low-high], :name "doji-co/lh max", :spec [0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9]}]})

      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add algo"]

    [:button
     {:on-click #(add-node m {:component "saying"
                              :name "saying"
                              :id "saying1"
                              :options {:id 0}
                              :edit [{:type :select
                                      :path :id,
                                      :name "saying-id",
                                      :spec
                                      (into [] (range 20))}]})

      :style {:border-radius "5px"
              :border "1px solid lightgray"}}
     "add saying"]

; end of top menu
    ]

   [:div {:style {:display "flex"
                  :flex-grow "1"
                  :position "relative"
                  :border "1px solid #ddd"}}
    [layout m]]])