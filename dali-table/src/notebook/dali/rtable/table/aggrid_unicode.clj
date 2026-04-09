(ns notebook.dali.rtable.table.aggrid-unicode
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]   
   [notebook.dali.rtable.random-bars :refer [random-bar-ds]]))

(def ds
  (-> (random-bar-ds 10)
      (tc/add-column :action ["😀" "" "" "★" ""
                              "" "❂" "" "" ""])))

ds

(def opts
  {:style {:width "800px" :height "600px"}
   :timezone "America/Panama"
   :columns [{:field :date}
             {:field :open}
             {:field :action}]})

(plot/aggrid-ds opts ds)

