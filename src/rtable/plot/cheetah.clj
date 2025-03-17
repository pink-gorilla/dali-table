(ns rtable.plot.cheetah
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.cache :refer [dali-cache-store]]
   [dali.store.file.transit] ; side effects
   [dali.store :refer [write]]))

(defn cheetah-ds
  "plot techml dataset via cheetah grid"
  ([opts ds]
   (cheetah-ds dali-cache-store opts ds))
  ([{:keys [dali-store]}
    {:keys [style class columns]
     :or {style {:width "100%" :height "100%"}
          class ""}} ds]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
     :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
     :data {:style style
            :class class
            :columns columns
            :load (write dali-store "transit-json" ds)}})))
