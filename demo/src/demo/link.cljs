(ns demo.link
  (:require
   [reagent.core :as r]
   [reitit.frontend.easy :as rfe]))

(defn create-link [page & opts]
  (try (if opts
         (apply rfe/href page opts)
         (rfe/href page))
       (catch :default ex ; js/Exception ex
         (println "error in link for: " page opts)
         "")))

(defn link [[page & opts] text]
  (let [href (apply create-link page opts)]
    (println "link href: " href " page: " page " opts: " opts)
    [:a {:href href
         :style {:background "yellow"}}
     [:span {:style {:margin "2px"}}
      " [ " text " ] "]]))

