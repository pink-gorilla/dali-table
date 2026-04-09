(ns rtable.plot.pixi
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   ))

(defn set-url [data url]
  (let [data (or data {})]
    (assoc data :load {:url url})))


(defn default-size [{:keys [width height] :as style}]
  (merge
   style
   {:width (or width "800px")
    :height (or height "600px")}))


(defn pixi-ds
  "plot techml dataset via pixi.js chart renderer"
  [{:keys [style class charts]
    :or {class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.pixi/pixi
    :transform-fn 'rtable.transform.pixi/load-and-transform-pixi
    :data {:style (default-size style)
           :class class
           :columns charts}
    :store-format :transit-json
    :store-data ds
    :store-set-url set-url
    
    }))
