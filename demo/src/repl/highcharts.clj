(ns repl.highcharts
  (:require
   [rtable.plot.highcharts :refer [highchart]]
   [demo.highcharts.spec :refer [highchart-spec]]))




(tap>  (highchart {:style {:width "100%"
                           :height "100%"}
                   :data highchart-spec}))


