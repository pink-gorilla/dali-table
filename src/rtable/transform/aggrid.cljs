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

(defn load-and-transform-aggrid [{:keys [columns url] :as opts}]
  (let [ds-p (load-transit url)]
    (p/then ds-p (fn [ds]
                   (-> opts
                       (dissoc :columns :url :datatype)
                       (assoc :columnDefs (hack-col-spec columns)
                              :rowData (->> (tmlds/rows ds)
                                            (into []))))))))


