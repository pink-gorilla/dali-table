(ns rtable.highcharts
  (:require
   [reagent.core :as reagent]
   [reagent.dom]
   [rtable.highcharts-render :refer [render-highchart render-highstock]]))

(defn highcharts-impl
  [{:keys [style class data-js]
    :or {style {}
         class ""}}]
  (let [uuid 28]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
    (reagent/create-class
     {:display-name "highcharts-reagent"
      :reagent-render (fn [{:keys [style class data-js]
                            :or {style {}
                                 class ""}}] ;; remember to repeat parameters
                        [:div {:style style
                               :class class}])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                             (render-highstock (reagent.dom/dom-node this) data-js))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [data-js]} arg1]
                                ;(println "component did update: " this "argv: " new-argv)
                                (render-highstock (reagent.dom/dom-node this) data-js)))})))

(defn highstock
  [{:keys [data] :as opts}]
  [highcharts-impl (assoc opts :data-js (clj->js data))])

