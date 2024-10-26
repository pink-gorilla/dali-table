(ns rtable.viewer.highcharts
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [promesa.core :as p]
   [reagent.dom]
   [rtable.viewer.highcharts-render :refer [render-highchart render-highstock]]
   [rtable.transform.highcharts] ; side effects
  ))

(defn highcharts-impl
  [{:keys [style class data-js]
    :or {style {}
         class ""}}]
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
                           (-> (render-highstock (reagent.dom/dom-node this) data-js)
                               (p/then (fn [res]
                                         (info "highstock render complete.")))
                               (p/catch (fn [err]
                                          (error "highstock render error: " err)))))
    :component-did-update (fn [this old-argv]
                            (let [new-argv (rest (reagent/argv this))
                                  [arg1] new-argv
                                  {:keys [data-js]} arg1]
                                ;(println "component did update: " this "argv: " new-argv)
                              (-> (render-highstock (reagent.dom/dom-node this) data-js)
                                  (p/then (fn [res]
                                            (info "highstock render complete.")))
                                  (p/catch (fn [err]
                                             (error "highstock render error: " err))))))}))

(defn highstock
  [{:keys [data] :as opts}]
  [highcharts-impl (assoc opts :data-js (clj->js data))])

(defn highstock-ds [opts]
  [highcharts-impl opts])

