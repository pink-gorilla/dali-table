(ns demo.page.simple2
  (:require
   [demo.helper.menu :refer [wrap-menu]]
   [rtable.table :refer [reagent-table]]
   [demo.table.columns2 :refer [columns2]]
   [demo.table.data2 :refer [table-data2]]))

(defn table-simple2 []
  ;[:div.container {:style {:font-size 16  :margin-top 10}
  ;                 :height "100%"
  ;                 :width "100%"
  ;                 :class "bg-blue-200"}
  [reagent-table
   table-data2
   {; css customization
    :table-container {:style {:border "3px solid green"
                              :max-height "100%"}
                      :class "w-full.h-full"}
    :table {:class "table table-hover table-striped table-bordered table-transition bg-red-100 w-full h-full"
            :style {:border-spacing 0
                    :border-collapse "separate"}}
    :th {:style {:border "1px solid white"
                 :background-color "bg-green-500"}}
     ;:scroll-height "80vh"
     ; data
    :column-model columns2}]
     ;]
  )

(defn page-simple2 [_]
  [:div
   [:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-button.css"}]
   [:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-table.css"}]
   [:div.w-full.h-full
    [table-simple2]]])

(def page-simple2-menu (wrap-menu page-simple2))