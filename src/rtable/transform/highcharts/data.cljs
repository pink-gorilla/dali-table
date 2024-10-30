(ns rtable.transform.highcharts.data
  (:require
   [clojure.set]
   [tech.v3.dataset :as tmlds]
   [rtable.transform.highcharts.util :refer [chart->series]]))

;; convert normal column

(defn cols->series
  "extracts one column from ds 
   in format needed by highchart"
  [bar-study-epoch-ds cols]
  (let [rows (tmlds/rows bar-study-epoch-ds)]
    (->> rows
         (map (apply juxt cols))
         (into []))))

;; flag series

(defn- flag [v2style col row]
  {:x (:epoch row)
   ; :y (:close row)
   ;:z 1000
   :title (str (col row))
   :shape (get v2style (col row))
   :text (str "col: " col "val: " (col row))})

(def default-flag-styles
  {;:long "square"
   :long "url(/r/flags/arrow-up.svg)"
   true "flags"
   ;:short "circle"
   :short "url(/r/flags/arrow-down.svg)"})

(defn select-signal-contains [ds signal-col v]
  (tmlds/filter ds
                (fn [row]
                  (contains? v (signal-col row)))))

(defn series-flags
  "extracts one column from ds in format needed by highchart
   for signal plot"
  [bar-study-epoch-ds {:keys [v2style column]
                       :or {v2style default-flag-styles}}]
  ;(println "Series flags col:" col)
  (let [signal-set (-> v2style keys set)
        ; (tc/select-rows bar-ds (contains? v2style (:signal bar-ds)))
        only-signal-ds (select-signal-contains bar-study-epoch-ds column signal-set)
        rows (tmlds/rows only-signal-ds)
        _ (println "rows with signal: " (tmlds/row-count only-signal-ds))
        data (->> (map #(flag v2style column %) rows)
                  (into []))]
    (println "series data: " data)
    data))

; set series

(defn set-series-data [highchart-spec-js series-index series-data]
  (let [series-data-js (clj->js series-data)
        series (aget (.-series highchart-spec-js) series-index)]
    ;(println "series idx: " series-index " data: " series-data)
    (if series
      (set! (.-data series) series-data-js)
      (println "error: cannot set-series idx: " series-index " - index does not exist."))
    highchart-spec-js))

(defn- convert-series [spec-js ds idx {:keys [type column] :as row}]
  (let [cols (cond
               (= :ohlc type)
               [:epoch :open :high :low :close]

               (keyword? column)
               [:epoch column]

               :else ; this gets range type
               (into [] (concat [:epoch] column)))
        series-data (if (= :flags type)
                      (series-flags ds row)
                      (cols->series ds cols))
        v {:type type
           :idx idx
           :cols cols
           :data series-data}]
    (set-series-data spec-js idx series-data)
    ;(println "adding data row: " v)
    v))

(defn add-series-to-spec-js [spec-js ds chart-spec]
  (let [series (chart->series chart-spec)]
    (doall (map-indexed (partial convert-series spec-js ds) series))
    spec-js))