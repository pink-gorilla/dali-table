(ns rtable.plot.pixi
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   [dali.store :refer [write]]))

(defn pixi-ds
  "plot techml dataset via pixi.js chart renderer"
  [{:keys [dali-store]}
   {:keys [style class charts]
    :or {style {:width "100%" :height "100%"}
         class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.pixi/pixi
    :transform-fn 'rtable.transform.pixi/load-and-transform-pixi
    :data {:style style
           :class class
           :columns charts
           :load (write dali-store "transit-json" ds)}}))
