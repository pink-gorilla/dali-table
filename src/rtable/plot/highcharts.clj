(ns rtable.plot.highcharts
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.transform.transit :refer [save-transit]]))

(defn highstock-ds [{:keys [style class charts]
                     :or {style {:width "100%" :height "100%"}
                          class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data {:style style
           :class class
           :charts charts
           :load (save-transit ds)}}))
