(ns demo.page.core
  (:require
   [demo.link :refer [link]]))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [:div.grid.grid-cols-2.grid-flow-row.w-full-h-full
    ; viewer
    [link ['demo.page.viewer.rtable/page] "rtable"]
    [link ['demo.page.viewer.aggrid/page] "aggrid"]
    [link ['demo.page.viewer.cheetah/page] "cheetah"]
    [link ['demo.page.viewer.highcharts/page] "highchart"]
    [link ['demo.page.viewer.highcharts/highchart-full-page] "highchart-full"]
    [link ['demo.page.viewer.paint/page] "canvas-paint"]
    [link ['demo.page.viewer.pixi/page] "pixi"]
    [link ['demo.page.viewer.htmltable/page] "html-table"]
    [link ['demo.page.viewer.htmltable/page-full-menu] "html-table-full"]

    ; layout
    [link ['demo.page.layout/page] "layout"]
    [link ['demo.page.layout-viewer/page] "layout-viewer"]

    ; rtable
    [link ['demo.page.reagent-table-simple/page] "rtable simple"]
    [link ['demo.page.reagent-table-complex/page] "rtable complex"]

    ; dali tap
    [link ['dali.flowy.tap/page] "tap-viewer"]
; 'demo.page.reagent-table-simple/page-simple-menu
;  'demo.page.reagent-table-simple2/page-simple2
;  'demo.page.reagent-table-complex/page-complex-menu
; 'demo.page.reagent-table-simple2/page-simple2-menu
    ;homemade
; 'demo.page.homemade/page-table-menu
; html t
; 'demo.page.htmltable-static/page-static-menu
; 'demo.page.htmltable-static/page-static-full-menu

    ; tml
    ; 'demo.page.tml/page
    ; 'demo.page.tml-rtable/page

    ; docy 
    ;[link ['docy.page/docy-page]  "docy" ]
    ]])