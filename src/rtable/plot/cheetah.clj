(ns rtable.plot.cheetah
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.transform.transit :refer [save-transit]]))

(defn cheetah-ds [{:keys [style class columns]
                   :or {style {:width "100%" :height "100%"}
                        class ""}} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
    :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
    :data {:style style
           :class class
           :columns columns
           :load (save-transit ds)}}))
