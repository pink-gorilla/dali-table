(ns rtable.viewer.highcharts
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [promesa.core :as p]
   [reagent.dom]
   [rtable.viewer.highcharts-render :refer [render-highchart render-highstock]]
   [rtable.transform.highcharts] ; side effects
   [rtable.transform.highcharts.axes :refer [hack-height]]))

(defn highstock-impl [highcharts-render-fn]
  (fn [{:keys [style class data-js dynamic-height]
        :or {style {}
             class ""
             dynamic-height true}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
    (reagent/create-class
     {:display-name "highstock"
      :reagent-render (fn [{:keys [style class data-js]
                            :or {style {:width "100%" :height "100%"}
                                 class ""}}] ;; remember to repeat parameters
                        [:div {:style style
                               :class class}])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             (let [node (reagent.dom/dom-node this)
                                 ;width (.-offsetWidth node)
                                   height (.-offsetHeight node)]
                               (info "highstock mount.")
                               (when dynamic-height
                                 (hack-height data-js height))
                               (-> (highcharts-render-fn (reagent.dom/dom-node this) data-js)
                                   (p/then (fn [res]
                                             (info "highstock render complete.")))
                                   (p/catch (fn [err]
                                              (error "highstock render error: " err))))))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [data-js dynamic-height]
                                     :or {dynamic-height true}} arg1
                                    node (reagent.dom/dom-node this)
                                    width (.-offsetWidth node)
                                    height (.-offsetHeight node)]
                                (info "highstock update.")
                                (when dynamic-height
                                  (hack-height data-js height))
                                (-> (highcharts-render-fn (reagent.dom/dom-node this) data-js)
                                    (p/then (fn [res]
                                              (info "highstock render complete.")))
                                    (p/catch (fn [err]
                                               (error "highstock render error: " err))))))})))

(def highstock
  (highstock-impl render-highstock))

(def highchart
  (highstock-impl render-highchart))

;(defn highstock
;  [{:keys [data] :as opts}]
;  (info "opts:" opts)
;  [highstock-impl (assoc opts :data-js (clj->js data))])



