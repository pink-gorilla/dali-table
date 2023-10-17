(ns demo.page.simple2
  (:require
   [rtable.table :refer [reagent-table]]
   [demo.helper.menu :refer [wrap-menu]]
   [demo.table.columns2 :refer [columns2]]
   [demo.table.data2 :refer [table-data2]]))

(defn- row-key-fn
  "Return the reagent row key for the given row"
  [row row-num]
  (get-in row [:id]))

(defn table-simple2 []
  [reagent-table
   table-data2
   {; css customization
    :table-container {:style {:font-size 16
                              :margin-top 0 ; margin makes problem with scrolling
                              :border "3px solid green"
                              :max-height "100%"
                              :overflow-x "auto"
                              :width "50vw"
                              :height "50vh"}
                      ;:class "tableFixHead"
                      }
    :table {;:class (str "table"
                       ; " table-hover table-striped table-bordered table-transition table-responsive"
             ;           " bg-red-100"
              ;          )
            :style {:border-spacing 0
                    :border-collapse "separate"
                    :width "100%"}}
    :thead {;:style {;:position "sticky" ; make the table heads sticky
                ;        :top "0px" ; table head will be placed from the top of the table and sticks to it
                ;        }
               ; :class "tableFixedHead bg-red-300"
            }
    :th {:style {:border "1px solid red"
                 ;:width "70"
                 }
         :class "bg-green-500"}

    :tbody {:width "100%"
            :display "table"}

; :scroll-height "100%"
     ; data
    :row-key      row-key-fn
    :column-model columns2}])

(defn page-simple2 [_]
  [:<>
   ;[:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-button.css"}]
   ;[:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-table.css"}]
   ;[:link {:rel "stylesheet" :type "text/css" :href "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"}]
   [table-simple2]])

(def page-simple2-menu (wrap-menu page-simple2))