(ns demo.page.complex
  (:require
   [reagent.core :as r]
   [demo.helper.ui :refer [link-dispatch link-href link-fn block2]]
   [demo.helper.menu :refer [wrap-menu]]
   [reagent-table.core :refer [reagent-table]]
   [rtable.cell :refer [cell-fn]]
   [rtable.sort :refer [sort-fn]]
   [demo.table.columns :refer [columns]]
   [demo.table.data :refer [table-data]]))

(defn- row-key-fn
  "Return the reagent row key for the given row"
  [row row-num]
  (get-in row [:Animal :Name]))

(def table-state (r/atom {:draggable true}))

(defn table-complex []
  [:div.container {:style {:font-size 16 :margin-top 10}
                   :height "100%"}
   [reagent-table
    table-data
    {; css customization
     :table {:class "table table-hover table-striped table-bordered table-transition bg-red-100"
             :style {:border-spacing 0
                     :border-collapse "separate"}}
     :table-container {:style {:border "1px solid green"}}
     :th {:style {:border "1px solid white"
                  :background-color "bg-green-500"}}
     :scroll-height "80vh"
     :column-selection {:ul
                        {:li {:class "btn"
                              :style {:display "inline-block"}}}}
                              ;:caption [:caption "Test caption"]
                              ; data
     :table-state  table-state
     :column-model columns
     :row-key      row-key-fn
     :render-cell  cell-fn
     :sort         sort-fn}]])

(defn page-complex [_]
  [:div
   [:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-button.css"}]
   [:link {:rel "stylesheet" :type "text/css" :href "/r/bootstrap-table.css"}]
   [:div.w-full.h-full
    [table-complex]]])

(def page-complex-menu (wrap-menu page-complex))


