(ns rtable.viewer.echarts
  (:require
   ["echarts" :as ec]))

(defn echarts
  [{:keys [style class data on register-map]
    :or {style {:width "100%" :height "100%"}
         class ""}}]
  (fn []
    [:div
     {:style style
      :class class
      :ref (fn [el]
             (when el
               (when-let [[map-name map-options] register-map]
                 (.registerMap ec
                               map-name (clj->js map-options)))
               (let [chart (.init ec el)]
                 (->> data
                      clj->js
                      (.setOption chart))
                 (some->> on
                          (run! (fn [[event-type f]]
                                  (.on chart
                                       (name event-type)
                                       f)))))))}]))