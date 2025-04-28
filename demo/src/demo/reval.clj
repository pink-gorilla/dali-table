(ns demo.reval
  (:require
   [tech.v3.dataset.print :refer [dataset->str]]
   [tech.v3.dataset.impl.dataset]
   [tech.v3.dataset.impl.column]
   [dali.plot.text :refer [text]]
   [reval.type.protocol :refer [dali-convertable]]))

;; techml 

(extend-type tech.v3.dataset.impl.dataset.Dataset
  dali-convertable
  (to-dali [v _env]
    (text {:text (dataset->str v)})))

(defn quanta-default-reval-ui []
      ; this function is called just for the side-effects above.
  (println "** adding techml render ui .."))
