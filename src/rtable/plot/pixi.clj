(ns rtable.plot.pixi
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.transform.transit :refer [save-transit]]))

(defn pixi-ds [{:keys [style class charts]
                :or {style {:width "100%" :height "100%"}
                     class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.pixi/pixi
    :transform-fn 'rtable.transform.pixi/load-and-transform-pixi
    :data {:style style
           :class class
           :columns charts
           :load (save-transit ds)}}))
