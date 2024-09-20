(ns rtable.tmlds.aggrid
  (:require
   [clojure.set]
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [rtable.aggrid :as ag]
   [cquant.tmlds :refer [GET]]))

(defn adjust-column [col]
  (clojure.set/rename-keys col {:path :field
                                :header :headerName}))

(defn hack-col-spec [cols]
  (->> cols
       (map adjust-column)
       (into [])))

(defn aggrid [{:keys [theme style columns]
               :or {style {:width "800px" :height "600px"}}
               :as opts} ds]

  (when ds
    [ag/aggrid
     {:style style
      :theme theme
      :rowData (->> (tmlds/rows ds)
                    (into []))
      :columnDefs (hack-col-spec columns)}]))

(defn aggrid-url [{:keys [style columns]
                   :or {style {:width "800px" :height "600px"}}
                   :as opts} url]
  (let [ds-a (r/atom nil)]
    (-> (GET url)
        (p/then (fn [ds]
                  (reset! ds-a ds)))
        (p/catch (fn [err]
                   (println "could not load ds " url " err: " err)
                   (reset! ds-a :load-error))))
    (fn [{:keys [style columns]
          :or {style {:width "800px" :height "600px"}}} url]
      (cond
        (nil? @ds-a)
        [:p "nil"]
        (= @ds-a :load-error)
        [:p "load error!"]
        :else
        [aggrid opts @ds-a]))))



