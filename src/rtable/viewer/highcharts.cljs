(ns rtable.viewer.highcharts
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [promesa.core :as p]
   [reagent.dom]
   [rtable.viewer.highcharts-render :refer [render-highchart render-highstock]]
   [rtable.transform.highcharts] ; side effects
   [rtable.transform.highcharts.axes :refer [hack-height]]))

(defn render [render-fn data-js dynamic-height node]
  (if node
    (let [width (.-offsetWidth node)
          height (.-offsetHeight node)]
      (info "highstock rendering .. height: " height " width: " width)
      (when dynamic-height
        (hack-height data-js height))
      (-> (render-fn node data-js)
          (p/then (fn [res]
                    (info "highstock render complete.")))
          (p/catch (fn [err]
                     (error "highstock render error: " err)))))
    (error "cannot render highstock - dom node nil.")))

(defn highstock-impl [highcharts-render-fn]
  (let [my-ref (atom nil)]
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
                                 :class class
                                 :ref (fn [el] (reset! my-ref el))}])
        :component-did-mount (fn [this] ; oldprops oldstate snapshot
                               (info "highstock mount.")
                               (render highcharts-render-fn data-js dynamic-height @my-ref))
        :component-did-update (fn [this old-argv]
                                (let [new-argv (rest (reagent/argv this))
                                      [arg1] new-argv
                                      {:keys [data-js dynamic-height]
                                       :or {dynamic-height true}} arg1]
                                  (info "highstock update.")
                                  (render highcharts-render-fn data-js dynamic-height @my-ref)))}))))

(def highstock
  (highstock-impl render-highstock))

(def highchart
  (highstock-impl render-highchart))

;(defn highstock
;  [{:keys [data] :as opts}]
;  (info "opts:" opts)
;  [highstock-impl (assoc opts :data-js (clj->js data))])



