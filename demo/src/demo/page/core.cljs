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
    [item "aggrid" 'demo.page.aggrid/page]
    [item "cheetah" 'demo.page.cheetah/page]
    [item "highchart"  'demo.page.highcharts/highchart-page]
    [item "highchart-full" 'demo.page.highcharts/highchart-full-page]
    [item "canvas-paint" 'demo.page.paint/page]
    [item "pixi" 'demo.page.pixi/pixi-page]]])

