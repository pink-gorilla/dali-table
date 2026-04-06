(ns rtable.plot.cheetah
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   ))

(defn set-url [data url]
  (update data assoc :load {:url url}))

(defn cheetah-ds
  "plot techml dataset via cheetah grid"
  [{:keys [style class columns]
    :or {style {:width "100%" :height "100%"}
         class ""}} ds]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
     :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
     :data {:style style
            :class class
            :columns columns}
     :store-format :transit-json
     :store-data ds
     :store-set-url set-url}))
