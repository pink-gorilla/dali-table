(ns rtable.transform.highcharts
  (:require
   [clojure.set]
   [tick.core :as t]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [dali.transform.transit :refer [load-transit]]
   [rtable.transform.highcharts.default :refer [default-template default-chart-with-volume]]
   [rtable.transform.highcharts.series :refer [->series]]
   [rtable.transform.highcharts.axes :refer [y-axis]]
   [rtable.transform.highcharts.data :refer [add-series-to-spec-js]]))

(defn add-epoch [ds]
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

(defn debug-template [template-js]
  (set! (.-HHH js/window) template-js))

(defn transform-highcharts-sync [{:keys [template
                                         charts
                                         ds]
                                  :or {template default-template
                                       charts default-chart-with-volume}
                                  :as opts}]

  (let [ds (add-epoch ds)
        ; highchart will always be at 100% when not setting chart height.
        ;template (set-chart-height template charts)
        template (assoc template
                        :yAxis (y-axis charts)
                        :series (->series charts))
        template-js (clj->js template)]
    (println "template without data: " template)
    (add-series-to-spec-js template-js ds charts)
                        ;(debug-template template-js)
    (-> opts
        (dissoc :ds :charts :template)
        (assoc :data-js template-js))))

(defn transform-highcharts [opts]
  (p/resolved (transform-highcharts-sync opts)))

(defn load-and-transform-highcharts [{:keys [load] :as opts}]
  (let [ds-p (load-transit load)]
    (p/then ds-p (fn [ds]
                   (transform-highcharts-sync (-> opts
                                                  (dissoc :load)
                                                  (assoc :ds ds)))))))
