(ns rtable.data.highcharts.data
  (:require
   [clojure.set]
   [tech.v3.dataset :as tmlds]
   [rtable.data.highcharts.util :refer [chart->series]]))

(defn cols->series
  "extracts one column from ds 
   in format needed by highchart"
  [bar-study-epoch-ds cols]
  (let [rows (tmlds/rows bar-study-epoch-ds)]
    (->> rows
         (map (apply juxt cols))
         (into []))))

(defn set-series-data [highchart-spec-js series-index ds cols]
  (let [series-data (cols->series ds cols)
        series-data-js (clj->js series-data)
        series (aget (.-series highchart-spec-js) series-index)]
    (if series
      (set! (.-data series) series-data-js)
      (println "error: cannot set-series idx: " series-index " col: " cols " - index does not exist."))
    highchart-spec-js))

(defn- convert-series [spec-js ds idx {:keys [type column] :as row}]
  (let [cols (cond

               (= :ohlc type)
               [:epoch :open :high :low :close]

               (keyword? column)
               [:epoch column]

               :else
               (into [] (concat [:epoch] column)))
        v {:type type
           :idx idx
           :cols cols
           :data (cols->series ds cols)}]
    (set-series-data spec-js idx ds cols)
  ;(cond
    ;(= type :flags)
    ;(series-flags bar-study-epoch-ds row)
;    )
    ;(println "adding data row: " v)
    v))

(defn add-series-to-spec-js [spec-js ds chart-spec]
  (let [series (chart->series chart-spec)]
    (doall (map-indexed (partial convert-series spec-js ds) series))
    spec-js))