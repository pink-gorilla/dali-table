(ns rtable.plot.echarts
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn default-size [{:keys [width height] :as style}]
  (merge
   style
   {:width (or width "800px")
    :height (or height "600px")}))

(defn echarts
  "plot data via echats"
  [{:keys [style class data] :as _opts}]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.echarts/echarts
    :data {:style (default-size style)
           :class class
           :data data}}))
