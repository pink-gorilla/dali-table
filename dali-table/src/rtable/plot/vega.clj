(ns rtable.plot.vega
  (:require
   [tablecloth.api :as tc]
   [tech.v3.dataset :as tds]
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   ))


(def default-embed-opts
  {; see:https://vega.github.io/vega-embed/interfaces/EmbedOptions.html
   :width 800 ; int
   :height 600
   :theme "quartz"})

:embed/opts

(defn with-default-embed-opts [opts]
  (assoc opts :embed/opts (merge default-embed-opts (or (:embed/opts opts) {}))))


(defn vegalite
  "opts needs to contain :spec and :data
   :data needs to be a map with named datasources. 
   in :spec each plot needs to have :data {:name :table}"
  [{:keys [style] :as opts}]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vegalite
    :data (with-default-embed-opts opts)}))


(defn convert-data
  "converts relevant cols (columns) of a techml dataset
   to a format that is understood by vega/vega-lite"
  [ds cols]
  (->> (tc/select-columns ds cols)
       (tds/mapseq-reader)
       (into [])))

(defn vegalite-ds
  "plot techml dataset via vega/vega-lite"
  [{:keys [cols] :as spec} ds]
  (let [spec (-> spec
                 (dissoc :cols)
                 (assoc :data {:values (convert-data ds cols)})
                 (with-default-embed-opts))]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vegalite
    :data spec})))
  
(defn vega
  "opts needs to contain :spec and :data
   :data needs to be a map with named datasources. 
   in :spec each plot needs to have :data {:name :table}"
  [spec]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vega
    :data (with-default-embed-opts spec)}))