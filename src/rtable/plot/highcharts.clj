(ns rtable.plot.highcharts
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   [dali.store :refer [write]]))

(defn highstock-ds [{:keys [dali-store]}
                    {:keys [style class charts]
                     :or {style {:width "100%" :height "100%"}
                          class ""}}
                    ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data {:style style
           :class class
           :charts charts
           :load (write dali-store "transit-json" ds)}}))
