(ns rtable.default.dali-converter
  (:require
   [tech.v3.dataset.print :refer [dataset->str]]
   [tech.v3.dataset.impl.dataset]
   [tech.v3.dataset.impl.column]
   [dali.plot.text :refer [text]]
   [dali.type.protocol :refer [dali-convertable]]))

(defn add-techml-dali-converter []
  (println "adding techml dali converter ..")

  (extend-type tech.v3.dataset.impl.column.Column
    dali-convertable
    (to-dali [v]
      (text {:text (str v)})))

  (extend-type tech.v3.dataset.impl.dataset.Dataset
    dali-convertable
    (to-dali [v]
      (text {:text (dataset->str v)}))))


