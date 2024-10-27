(ns rtable.plot.highcharts
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.transform.transit :refer [save-transit]]))

(defn highstock-ds [{:keys [style class columns]
                     :or {style {:width "100%" :height "100%"}
                          class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data {:style style
           :class class
           :columns columns
           :load (save-transit ds)}}))
