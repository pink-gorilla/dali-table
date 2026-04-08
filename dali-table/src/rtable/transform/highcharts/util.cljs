(ns rtable.transform.highcharts.util)

(defn chart-pane-spec? [spec]
  true)

(defn chart-count [panes]
  (count panes))

(defn get-chart [spec idx]
  (get spec idx))

(defn- ensure-type-keyword [{:keys [type] :as opts}]
  (if (string? type)
    (assoc opts :type (keyword type))
    opts))

(defn pane->series [idx pane]
  (map (fn [[col type]]
         (cond
           (map? type)
           (merge {:axis idx :column col} (ensure-type-keyword type))

           (string? type)
           {:axis idx :column col :type (keyword type)}

           (keyword type)
           {:axis idx :column col :type type})) pane))

(defn chart->series [chart]
  (->> (map-indexed pane->series chart)
       (reduce concat)))

(defn axes-count [chart]
  (count chart))