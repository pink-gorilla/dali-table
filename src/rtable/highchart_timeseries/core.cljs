(ns rtable.highchart-timeseries.core
  (:require
   [rtable.highchart-timeseries.default :refer [default-template default-chart-with-volume]]
   [rtable.highchart-timeseries.series :refer [->series]]))

(defn highchart-spec [{:keys [template
                              charts]
                       :or {template default-template
                            charts default-chart-with-volume}}]
  ;(let [template (set-chart-height template charts)]
  (assoc template
           ;:yAxis (y-axis chart panes)
         :series (->series charts)))

