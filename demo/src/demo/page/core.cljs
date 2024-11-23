(ns demo.page.core
  (:require
   [re-frame.core :as rf]))

(defn item [text page]
  [:div
   {:class "hover:bg-blue-400 border rounded cursor-pointer w-64 bg-blue-200 m-5"
    :on-click #(rf/dispatch [:bidi/goto page :query-params {}])}
   text])

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [:grid.grid-cols-2.grid-flow-row.w-full-h-full
    ; viewer
    [item "rtable" ' demo.page.viewer.rtable/page]
    [item "aggrid" 'demo.page.viewer.aggrid/page]
    [item "cheetah" 'demo.page.viewer.cheetah/page]
    [item "highchart"  'demo.page.viewer.highcharts/page]
    [item "highchart-full" 'demo.page.viewer.highcharts/highchart-full-page]
    [item "canvas-paint" 'demo.page.viewer.paint/page]
    [item "pixi" 'demo.page.viewer.pixi/page]
    [item "html-table" 'demo.page.viewer.htmltable/page]
    [item "html-table-full" 'demo.page.viewer.htmltable/page-full-menu]

    ; layout
    [item "layout" 'demo.page.layout/page]
    [item "layout-viewer" 'demo.page.layout-viewer/page]

    ; docy 
    [item "docy" 'docy.page/docy-page]
    ]])

