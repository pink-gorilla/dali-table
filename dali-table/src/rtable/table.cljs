(ns rtable.table
  (:require
   ;[reagent-table.core :as core]
   [rtable.core :as core]
   [rtable.cell :refer [cell-fn]]))

(def default-config
  {:render-cell cell-fn})

(defn add-header [{:keys [path header] :as column}]
  (if (nil? header)
    (assoc column :header (str path))
    column))

(defn column-model-with-default-header [{:keys [column-model]}]
  {:column-model (into []
                       (map add-header column-model))})

(defn reagent-table [data-atom config]
  (core/reagent-table data-atom (merge
                                 default-config
                                 (column-model-with-default-header config)
                                 (dissoc config :column-model))))


