(ns rtable.transform.pixi
  (:require
   [clojure.set]
   [tick.core :as t]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [dali.transform.load :refer [load-transit]]))

(defn add-epoch [ds]
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

(defn load-and-transform-pixi [{:keys [load]
                                :as opts}]
  (let [ds-p (load-transit load)]
    (p/then ds-p (fn [ds]
                   (let [ds (add-epoch ds)]
                     (-> opts
                         (dissoc :load)
                         (assoc :data ds)))))))
