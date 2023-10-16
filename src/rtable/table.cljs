(ns rtable.table
  (:require
   [reagent-table.core :as core]
   [rtable.cell :refer [cell-fn]]))

(def default-config
  {:render-cell cell-fn})

(defn reagent-table [data-atom config]
  (core/reagent-table data-atom (merge default-config config)))


