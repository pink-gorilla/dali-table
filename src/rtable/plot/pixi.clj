(ns rtable.plot.pixi
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   ))

(defn set-url [data url]
  (update data assoc :load {:url url}))

(defn pixi-ds
  "plot techml dataset via pixi.js chart renderer"
  [{:keys [style class charts]
    :or {style {:width "100%" :height "100%"}
         class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.pixi/pixi
    :transform-fn 'rtable.transform.pixi/load-and-transform-pixi
    :data {:style style
           :class class
           :columns charts}
    :dali.store/format :transit-json
    :dali.store/data ds
    :dali.store/set-url set-url
    
    }))
