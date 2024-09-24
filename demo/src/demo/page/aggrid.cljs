(ns demo.page.aggrid
  (:require
   [reagent.core :as r]
   [rtable.aggrid :refer [aggrid]]))

(def data [{"make" "Toyota" "model" "Celica" "price" 35000}
           {"make" "Ford" "model" "Mondeo"  "price" 32000}
           {"make" "Porsche" "model" "Boxter"  "price" 72000}])

(defonce clicked? (r/atom false))

(defn page [_]
  [:div
   ;; aggrid that has its data
   [aggrid {:style {:width "800px" :height "600px"}
            :columnDefs [{:field "make"}
                         {:field "model"}
                         {:field "price"}]
            :rowData data}]])