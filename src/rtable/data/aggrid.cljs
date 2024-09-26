(ns rtable.data.aggrid
  (:require
   [clojure.set]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [rtable.data :refer [load-and-transform2 load-dataset]]
    ;[rtable.resolve :refer [resolve-col]]
   ))
(defn adjust-column [col]
  (clojure.set/rename-keys col {:path :field
                                :header :headerName}))

(defn hack-col-spec [cols]
  (->> cols
       (map adjust-column)
       (into [])))

(defmethod load-and-transform2 :aggrid [{:keys [columns url] :as opts}]
  (let [ds-p (load-dataset url)]
    (p/then ds-p (fn [ds]
                   (-> opts
                       (dissoc :columns :url :datatype)
                       (assoc :columnDefs (hack-col-spec columns)
                              :rowData (->> (tmlds/rows ds)
                                            (into []))))))))


