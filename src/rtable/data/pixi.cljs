(ns rtable.data.pixi
  (:require
   [clojure.set]
   [tick.core :as t]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [rtable.data :refer [load-and-transform2 load-dataset]]
  ))

(defn add-epoch [ds]
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

(defn debug-template [template-js]
  (set! (.-HHH js/window) template-js))

(defmethod load-and-transform2 :pixi [{:keys [url]
                                       :as opts}]
  (let [ds-p (load-dataset url)]
    (p/then ds-p (fn [ds]
                   (let [ds (add-epoch ds)]
                     (-> opts
                         (dissoc :url)
                         (assoc :data ds)))))))
