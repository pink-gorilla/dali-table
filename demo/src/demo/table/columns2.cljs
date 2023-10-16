(ns demo.table.columns2
  (:require
   [reagent.core :as r]))

;the column model
(def columns2 [{:path :id
                :header "Id"}
               {:path :first-name
                :header "First Name"}
               {:path :last-name
                :header "Last Name"}])