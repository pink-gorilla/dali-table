(ns rtable.plot.vega
  (:require
   [tablecloth.api :as tc]
   [tech.v3.dataset :as tds]
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   ))

(defn default-size [{:keys [width height] :as style}]
  (merge
   style
   {:width (or width "800px")
    :height (or height "600px")}))


(defn vegalite
  "opts needs to contain :spec and :data
   :data needs to be a map with named datasources. 
   in :spec each plot needs to have :data {:name :table}"
  ([{:keys [style] :as opts}]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.vega/vegalite
     :data (merge
            {:style (default-size style)}
            opts)}))
  ([opts data]
   (vegalite (assoc opts :data data))))

(defn convert-data
  "converts relevant cols (columns) of a techml dataset
   to a format that is understood by vega/vega-lite"
  [ds cols]
  (->> (tc/select-columns ds cols)
       (tds/mapseq-reader)
       (into [])))

(defn vegalite-ds
  "plot techml dataset via vega/vega-lite"
  [{:keys [style cols spec] :as opts} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vegalite
    :data (merge
           opts
           {:style style
            :spec (assoc spec :data {:values (convert-data ds cols)})})}))

(defn vega
  "plot techml dataset via vega/vega"
  ([opts]
   (vega opts (:data opts)))
  ([{:keys [style] :as opts} data]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.vega/vega
     :data (merge
            {:style (default-size style)
             :data data}
            opts)})))