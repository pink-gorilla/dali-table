(ns rtable.plot.echarts
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn echarts
  "plot data via echats"
  [{:keys [style class data]
    :or {style {:width "100%" :height "100%"}}
    :as opts}]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.echarts/echarts
    :data {:style style
           :class class
           :data data}}))
