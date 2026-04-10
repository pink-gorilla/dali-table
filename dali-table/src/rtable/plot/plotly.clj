(ns rtable.plot.plotly
  (:require
   [dali.spec :refer [create-dali-spec]]))

(defn default-size [{:keys [width height] :as style}]
  (merge
   style
   {:width (or width "800px")
    :height (or height "600px")}))

(defn plotly
  "plot data via plotly"
  [{:keys [style] :as opts}]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.plotly/plotly
    :data (assoc opts :style (default-size style))}))
