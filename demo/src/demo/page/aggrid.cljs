(ns demo.page.aggrid
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [rtable.render.aggrid :refer [aggrid aggrid-ds]]
   [demo.helper.ui :refer [sample-selector]]))

;(rf/dispatch [:css/set-theme-component :aggrid "material"])
;(rf/dispatch [:css/set-theme-component :aggrid "alpine"])
;(rf/dispatch [:css/set-theme-component :aggrid "balham-dark"])

(rf/dispatch [:css/set-theme-component :aggrid true]) ; default

(def data [{"make" "Toyota" "model" "Celica" "price" 35000}
           {"make" "Ford" "model" "Mondeo"  "price" 32000}
           {"make" "Porsche" "model" "Boxter"  "price" 72000}])

(defonce clicked? (r/atom false))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100.flex.flex-col
   [sample-selector
    {:small
     [aggrid {:style {:width "400px" :height "400px"}
              :columnDefs [{:field "make"}
                           {:field "model"}
                           {:field "price"}]
              :rowData data}]
     :big
     [aggrid {:style {:width "800px" :height "800px"}
              :columnDefs [{:field "make"}
                           {:field "model"}
                           {:field "price"}]
              :rowData data}]

     :dataset
     [aggrid-ds {:style {:width "1200px" :height "600px"}
                                  ;:theme "material" ;
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
                                                             (println "ccr: " p)
                                                             (nil? (.-value p)))}}]
                 :url "/r/signal-no-date.transit-json"}]}]])