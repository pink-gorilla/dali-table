(ns rtable.data.highcharts
  (:require
   [clojure.set]
   [tick.core :as t]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [rtable.data :refer [load-and-transform2 load-dataset]]
   ;[rtable.resolve :refer [resolve-col]]
   [rtable.data.highcharts.default :refer [default-template default-chart-with-volume]]
   [rtable.data.highcharts.series :refer [->series]]
   [rtable.data.highcharts.axes :refer [y-axis set-chart-height]]
   [rtable.data.highcharts.data :refer [add-series-to-spec-js]]))

(defn add-epoch [ds]
  (tmlds/column-map ds :epoch #(-> % t/instant t/long) [:date]))

(defn debug-template [template-js]
  (set! (.-HHH js/window) template-js))

(defmethod load-and-transform2 :highcharts [{:keys [template
                                                    charts
                                                    url]
                                             :or {template default-template
                                                  charts default-chart-with-volume}
                                             :as opts}]
  (let [ds-p (load-dataset url)]
    (p/then ds-p (fn [ds]
                   (let [ds (add-epoch ds)
                         template (set-chart-height template charts)
                         template (assoc template
                                         :yAxis (y-axis charts)
                                         :series (->series charts))
                         template-js (clj->js template)]
                     ;(println "template without data: " template)
                     (add-series-to-spec-js template-js ds charts)
                     (debug-template template-js)
                     (-> opts
                         (dissoc :url :charts :template :datatype)
                         (assoc :data-js template-js)))))))
