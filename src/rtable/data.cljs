(ns rtable.data
  (:require
   [promesa.core :as p]
   [reagent.core :as r]
   [tech.v3.dataset :as tmlds]
   [cquant.tmlds :refer [GET]]))

(defmulti load-and-transform2
  (fn [opts] (:datatype opts)))

(defn load-dataset [url]
  (println "loading dataset from url: " url)
  (let [load-promise (GET url)]
    (-> load-promise
        (p/then (fn [ds]
                  (println "ds from url " url " loaded successfully. rows: " (tmlds/row-count ds) "cols: " (tmlds/column-names ds))
                  ds))
        (p/catch (fn [err]
                   (println "could not load ds from url " url " err: " err))))
    ; give back the original promise
    load-promise))

(defn loading-ui [opts datatype render-fn]
  (let [a (r/atom nil)]
    (-> (load-and-transform2 (assoc opts :datatype datatype))
        (p/then (fn [ds]
                  (reset! a ds)))
        (p/catch (fn [err]
                   (println "load and transform datatype: " datatype " error: " err)
                   (reset! a :load-error))))
    (fn [opts datatype render-fn]
      (cond
        (nil? @a)
        [:p "nil"]
        (= @a :load-error)
        [:p "load error!"]
        :else
        [render-fn @a]))))

