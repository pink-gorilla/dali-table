


(ns rtable.default.dali-converter
  (:require
   [dali.plot.text :refer [text]]
   [reval.type.protocol :refer [dali-convertable]]
   [tech.v3.dataset.impl.dataset]
   [tech.v3.dataset.impl.column]))

(defn add-techml-dali-converter []
  (println "adding techml dali converter .."))

(extend-type tech.v3.dataset.impl.column.Column
  dali-convertable
  (to-dali [v _env]
    (text {:text v})))

(extend-type tech.v3.dataset.impl.dataset.Dataset
  dali-convertable
  (to-dali [v _env]
    (text {:text v})))


