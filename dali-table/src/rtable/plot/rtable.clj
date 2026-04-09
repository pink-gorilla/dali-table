(ns rtable.plot.rtable
  (:require
   [tech.v3.dataset :as tds]
   [tablecloth.api :as tc]
   [dali.spec :refer [create-dali-spec]]))

(defn default-size [{:keys [width height] :as style}]
  (merge
   style
   {:width (or width "800px")
    :height (or height "600px")}))

(defn rtable-cols [opts]
  (map :path (:columns opts)))

(defn rtable-spec? [opts]
  (and (map? opts)
       (:columns opts)
        ; TODO: all cols have :path
       ))

(defn ds->map [ds]
  ;(tc/rows :as-maps) ; this does not work, type of it is a reified dataset. 
  ; this works in repl, but when sending data to the browser it fails.
  (into []
        (tds/mapseq-reader ds)))



(defn rtable-ds
  "returns a dali specification 
   plot shows a table with specified columns (with custom formatting)
   opts must follow rtable-spec format.
   bar-signal-ds must be a tml/dataset with columns as specified."
  [{:keys [style class columns]
    :or {class "table-head-fixed padding-sm table-blue table-striped table-hover"}
    :as opts} ds]
  (assert (rtable-spec? opts) "rtable-spec needs to have :columns key")
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.rtable/rtable
    :transform-fn 'rtable.transform.rtable/transform-rtable
    :data {:style (default-size style)
           :class class
           :columns columns
           :rows  (-> ds
                      (tc/select-columns (rtable-cols opts))
                      ds->map)}}))



(defn rtable
  "returns a dali specification 
   plot shows a table with specified columns (with custom formatting)
   opts must follow rtable-spec format.
   bar-signal-ds must be a tml/dataset with columns as specified."
  [{:keys [style class columns]
    :or {class "table-head-fixed padding-sm table-blue table-striped table-hover"}
    :as opts} rows]
  (assert (rtable-spec? opts) "rtable-spec needs to have :columns key")
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.rtable/rtable
    :transform-fn 'rtable.transform.rtable/transform-rtable
    :data {:style (default-size style)
           :class class
           :columns columns
           :rows  rows}}))