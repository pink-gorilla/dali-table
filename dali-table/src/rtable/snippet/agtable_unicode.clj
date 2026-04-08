(ns rtable.snippet.agtable-unicode
  (:require 
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [rtable.snippet.data.random-bars :refer [random-bar-ds]]))

(def ds
  (-> (random-bar-ds 10)
      (tc/add-column :action ["😀"
                              ""
                              ""
                              "★"
                              ""
                              ""
                              "❂"
                              ""
                              ""
                              ""])))

ds

(def opts
  {:style {;:width "800px" :height "600px"
           :width "800px" :height "600px"
           ;:width "100%" :height "100%"
           }
   :timezone "America/Panama"
   :columns [{:field :date}
             {:field :open}
             {:field :action}]})

(plot/aggrid-ds opts ds)



