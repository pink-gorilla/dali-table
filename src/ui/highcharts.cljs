(ns ui.highcharts
  "Highchart renderer is a pure javascript renderer, the conversion
   of the spec as clj-data to javascript is done in the render-js component"
  (:require
   [rtable.highcharts-render :refer [render-highchart render-highstock]]
   [pinkie.ui.core :refer [render-js]]))

(defn highchart
  "reagent component to render highchart-spec via highcharts
   Usage:  [ui.highcharts/highchart spec-as-clj-data]"
  [spec]
  [render-js (assoc spec :f render-highchart)])

(defn highstock
  "reagent component to render highchart-spec via highcharts
   Usage:  [ui.highcharts/highchart spec-as-clj-data]"
  [spec]
  [render-js (assoc spec :f render-highstock)])

