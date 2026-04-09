(ns rtable.plot.cheetah
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

(defn cheetah-ds
  "plot techml dataset via cheetah grid"
  [{:keys [style class columns]
    :or {class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
    :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
    :data {:style (default-size style)
           :class class
           :columns columns}
    :store-format :transit-json
    :store-data ds
    :store-set-url set-url}))


