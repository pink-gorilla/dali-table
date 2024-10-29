(ns rtable.viewer.highcharts
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [promesa.core :as p]
   [reagent.dom]
   [rtable.viewer.highcharts-render :refer [render-highchart render-highstock]]
   [rtable.transform.highcharts] ; side effects
   [rtable.transform.highcharts.axes :refer [hack-height]]))

(defn highstock
  [{:keys [style class data-js]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (reagent/create-class
   {:display-name "highstock"
    :reagent-render (fn [{:keys [style class data-js]
                          :or {style {}
                               class ""}}] ;; remember to repeat parameters
                      [:div {:style style
                             :class class}])
    :component-did-mount (fn [this] ; oldprops oldstate snapshot
                           (let [node (reagent.dom/dom-node this)
                                 width (.-offsetWidth node)
                                 height (.-offsetHeight node)]
                             (info "highstock mount.")
                             (hack-height data-js height)
                             (-> (render-highstock (reagent.dom/dom-node this) data-js)
                                 (p/then (fn [res]
                                           (info "highstock render complete.")))
                                 (p/catch (fn [err]
                                            (error "highstock render error: " err))))))
    :component-did-update (fn [this old-argv]
                            (let [new-argv (rest (reagent/argv this))
                                  [arg1] new-argv
                                  {:keys [data-js]} arg1
                                  node (reagent.dom/dom-node this)
                                  width (.-offsetWidth node)
                                  height (.-offsetHeight node)]
                              (info "highstock update.")
                              (hack-height data-js height)
                                ;(println "component did update: " this "argv: " new-argv)
                              (-> (render-highstock (reagent.dom/dom-node this) data-js)
                                  (p/then (fn [res]
                                            (info "highstock render complete.")))
                                  (p/catch (fn [err]
                                             (error "highstock render error: " err))))))}))

;(defn highstock
;  [{:keys [data] :as opts}]
;  (info "opts:" opts)
;  [highstock-impl (assoc opts :data-js (clj->js data))])



