(ns demo.app
  (:require
   [frontend.css :refer [css-loader]]
   [frontend.notification :refer [notification-container]]
   [frontend.dialog :refer [modal-container]]
   [webly.spa.env :refer [get-resource-path]]))

(defn wrap-app [page match]
  [:div
   [modal-container]
   [notification-container]
   [css-loader (get-resource-path)]
   [page match]])

(def routes
  [["/" {:name 'demo.page.core/page}]
   ; VIEWER
   ["/viewer/"
    ["aggrid" {:name 'demo.page.viewer.aggrid/page}]
    ["highcharts" {:name 'demo.page.viewer.highcharts/page}]
    ["highcharts-full" {:name 'demo.page.viewer.highcharts/highchart-full-page}]
    ["canvas-paint" {:name 'demo.page.viewer.paint/page}]
    ["cheetah" {:name 'demo.page.viewer.cheetah/page}]
    ["pixi" {:name 'demo.page.viewer.pixi/page}]
    ["html-static" {:name 'demo.page.viewer.htmltable/page}]
    ["html-static-full" {:name 'demo.page.viewer.htmltable/page-full-menu}]
    ["rtable-home-made" {:name 'demo.page.viewer.rtable/page}]]
   ;layout
   ["/layout/"
    ["layout" {:name 'demo.page.layout/page}]
    ["layout-viewer" {:name 'demo.page.layout-viewer/page}]]
   ; reagent table
   ["/rtable/"
    ["rt-complex" {:name 'demo.page.reagent-table-complex/page-complex-menu}]
    ["rt-simple" {:name 'demo.page.reagent-table-simple/page-simple-menu}]
    ["rt-simple2" {:name 'demo.page.reagent-table-simple2/page-simple2-menu}]
    ["rt-simple2-nomenu" {:name 'demo.page.reagent-table-simple2/page-simple2}]]
   ; tml ds
   ["/tml/"
    ["tml" {:name 'demo.page.tml/page}]
    ["tml-rtable" {:name 'demo.page.tml-rtable/page}]]

   ; dali tap
   ["/dali/tap" {:name 'dali.flowy.tap/page}]])

