(ns demo.page.simple
  (:require
   [rtable.table :refer [reagent-table]]
   [demo.helper.menu :refer [wrap-menu]]
   [demo.table.columns :refer [columns]]
   [demo.table.data :refer [table-data]]))

(defn- row-key-fn
  "Return the reagent row key for the given row"
  [row row-num]
  (get-in row [:Animal :Name]))

(defn table-simple []
  ;[:div.container {:style {:font-size 16  :margin-top 10}
  ;                 :height "100%"
  ;                 :width "100%"
  ;                 :class "bg-blue-200"}
  [reagent-table
   table-data
   {; css customization
    :table-container {:style {:border "3px solid green"
                              :max-height "100%"}
                      :class "w-full.h-full"}
    :table {:class "table table-hover table-striped table-bordered table-transition bg-red-100 w-full h-full"
            :style {:border-spacing 0
                    :border-collapse "separate"}}
    :th {:style {:border "1px solid white"
                 :background-color "bg-green-500"}}
    :scroll-height "100%"
     ; data
    :column-model columns
    :row-key      row-key-fn}]
     ;]
  )

(defn page-simple [_]
  [:<>
   ;[:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-button.css"}]
   ;[:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-table.css"}]
   [:link {:rel "stylesheet" :type "text/css" :href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"}]

   [:div.w-full.h-full
    [table-simple]]])

(def page-simple-menu (wrap-menu page-simple))