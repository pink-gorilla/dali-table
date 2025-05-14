(ns rtable.plot.vega
  (:require
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   [dali.store :refer [write]]
   [tablecloth.api :as tc]
   [tech.v3.dataset :as tds]))

(defn vegalite 
  "3 arity is outdated. 
   opts needs to contain :spec and :data
   :data needs to be a map with named datasources. 
   in :spec each plot needs to have :data {:name :table}"
  ([{:keys [dali-store]} opts]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.vega/vegalite
     :data opts}))
  ([{:keys [dali-store]}
    {:keys [style]
     :or {style {:width "100%" :height "100%"}}
     :as opts} data]
   (create-dali-spec
    {:viewer-fn 'rtable.viewer.vega/vegalite
     :data (merge
            {:style style
             :data data}
            opts)})))

   ;:load (write dali-store "transit-json" ds)

(defn convert-data 
  "converts relevant cols (columns) of a techml dataset
   to a format that is understood by vega/vega-lite"
  [ds cols]
  (->> (tc/select-columns ds cols)
       (tds/mapseq-reader)
       (into [])))

(defn vegalite-ds
  "plot techml dataset via vega/vega-lite"
  [{:keys [dali-store]} {:keys [style cols spec] :as opts} ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vegalite
    :data (merge
           opts
           {:style style
            :spec (assoc spec :data {:values (convert-data ds cols)})})}))

(defn vega
  "plot techml dataset via vega/vega"
  [{:keys [dali-store]}
   {:keys [style]
    :or {style {:width "100%" :height "100%"}}
    :as opts} data]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.vega/vega
    :data (merge
           {:style style
            :data data}
           opts)}))