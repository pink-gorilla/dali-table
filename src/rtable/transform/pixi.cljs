(ns rtable.transform.pixi
  (:require
   [clojure.set]
   [tick.core :as t]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [dali.transform.transit :refer [load-transit]]))

(defn add-epoch [ds]
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

(defn load-and-transform-pixi [{:keys [url]
                                :as opts}]
  (let [ds-p (load-transit url)]
    (p/then ds-p (fn [ds]
                   (let [ds (add-epoch ds)]
                     (-> opts
                         (dissoc :url)
                         (assoc :data ds)))))))
