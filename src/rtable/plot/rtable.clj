(ns rtable.plot.rtable
  (:require
   [tech.v3.dataset :as tds]
   [tablecloth.api :as tc]
   [de.otto.nom.core :as nom]
   [dali.spec :refer [create-dali-spec]]
   [dali.plot.anomaly :as plot]))

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

(defn rtable-ds-impl [{:keys [style class columns]
                       :or {style {:width "100%" :height "100%"}
                            class "table-head-fixed padding-sm table-blue table-striped table-hover"}
                       :as opts} ds]
  (assert (rtable-spec? opts) "rtable-spec needs to have :columns key")
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.rtable/rtable
    :transform-fn 'rtable.transform.rtable/transform-rtable
    :data {:style style
           :class class
           :columns columns
           :rows  (-> ds
                      (tc/select-columns (rtable-cols opts))
                      ds->map)}}))

(defn rtable-ds
  "returns a dali specification 
   plot shows a table with specified columns (with custom formatting)
   opts must follow rtable-spec format.
   bar-signal-ds must be a tml/dataset with columns as specified."
  [opts bar-signal-ds]
  (if (nom/anomaly? bar-signal-ds)
    (plot/anomaly bar-signal-ds)
    (rtable-ds-impl opts bar-signal-ds)))

(defn rtable-impl [{:keys [style class columns]
                    :or {style {:width "100%" :height "100%"}
                         class "table-head-fixed padding-sm table-blue table-striped table-hover"}
                    :as opts} rows]
  (assert (rtable-spec? opts) "rtable-spec needs to have :columns key")
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.rtable/rtable
    :transform-fn 'rtable.transform.rtable/transform-rtable
    :data {:style style
           :class class
           :columns columns
           :rows  rows}}))

(defn rtable
  "returns a dali specification 
   plot shows a table with specified columns (with custom formatting)
   opts must follow rtable-spec format.
   bar-signal-ds must be a tml/dataset with columns as specified."
  [opts rows]
  (if (nom/anomaly? rows)
    (plot/anomaly rows)
    (rtable-impl opts rows)))