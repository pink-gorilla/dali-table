(ns rtable.plot.echarts
  (:require
   [tech.v3.dataset :as tds]
   [tablecloth.api :as tc]
   [de.otto.nom.core :as nom]
   [dali.spec :refer [create-dali-spec]]
   [dali.plot.anomaly :as plot]))

(defn echarts [{:keys [style class data]
                :or {style {:width "100%" :height "100%"}}
                :as opts}]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.echarts/echarts
    :data {:style style
           :class class
           :data data}}))
