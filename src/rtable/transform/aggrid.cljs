(ns rtable.transform.aggrid
  (:require
   [clojure.set]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [dali.transform.transit :refer [load-transit]]
   ;[rtable.resolve :refer [resolve-col]]
   ))
(defn adjust-column [col]
  (clojure.set/rename-keys col {:path :field
                                :header :headerName}))

(defn hack-col-spec [cols]
  (->> cols
       (map adjust-column)
       (into [])))

(defn load-and-transform-aggrid [{:keys [columns load] :as opts}]
  (let [ds-p (load-transit load)]
    (p/then ds-p (fn [ds]
                   (-> opts
                       (dissoc :columns :load)
                       (assoc :columnDefs (hack-col-spec columns)
                              :rowData (->> (tmlds/rows ds)
                                            (into []))))))))


