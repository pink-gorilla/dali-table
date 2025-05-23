(ns rtable.transform.highcharts.series
  (:require
   [dali.util.color :refer [set-color]]
   [rtable.transform.highcharts.default :as templ]
   [rtable.transform.highcharts.util :refer [chart->series]]))

(defmulti series (fn [opts] (:type opts)))

(defn id-name-axis [{:keys [axis column title]
                     :or {title (str column)}}]
  {:id (str column)
   :yAxis axis
   :name title
   :zIndex 1000
   :animation false
   :dataGrouping {:enabled false}})

(defmethod series :flags [opts]
  ;; FLAGS
  ;; A flag series consists of flags marking events or points of interests. 
  ;; Used alone flag series will make no sense. 
  ;; Flags can be placed on either the series of the chart or on the axis.
  (merge (id-name-axis opts)
         templ/flags))

(defmethod series :step [{:keys [color]
                          :or {color "blue"}
                          :as opts}]
  (merge (id-name-axis opts)
         templ/step
         {:color (set-color color)}))

(defmethod series :line [{:keys [color]
                          :or {color "blue"}
                          :as opts}]
  (merge (id-name-axis opts)
         templ/line
         {:color (set-color color)}))

(defmethod series :column [{:keys [color]
                            :or {color "blue"}
                            :as opts}]
  (merge (id-name-axis opts)
         templ/column
         {:color (set-color color)}))

(defmethod series :range [{:keys [color opacity]
                           :or {color "blue"
                                opacity 0.75}
                           :as opts}]
  (merge (id-name-axis opts)
         {:type "arearange"
          :color (set-color color)
          :fillOpacity opacity}))

(defmethod series :ohlc [{:keys [mode barcolor]
                          :or {mode :ohlc
                               barcolor {}}
                          :as opts}]
  (merge (id-name-axis opts)
         {:barcolor barcolor}
         (case mode
           :ohlc {:type "ohlc"}
           :candle {:type "candlestick"}
           :candle-hollow {:type "hollowcandlestick"})))

(defmethod series :point [{:keys [color marker radius]
                           :or {color "blue"
                                marker "circle"
                                radius 2}
                           :as opts}]
  (merge (id-name-axis opts)
         {:type "scatter" ; https://api.highcharts.com/highcharts/series.scatter
                            ;:lineWidth 0
          :marker {:enabled true
                   :symbol marker ; "circle" ; ; "triangle" "square"
                   :radius radius
                   :fillColor (set-color color)
                   ;:color (set-color color) ; no effect it seems
                   }}))

(defn ->series [charts]
  (let [series-seq (chart->series charts)]
    (->> ;(map series series-seq)
     (map-indexed (fn [idx s]
                    (assoc (series s) :idx idx)) series-seq)
     (into []))))

(comment

  (->series [{:open :line}])
  (->series [{:open :step}])
  (->series [{:open :point}])
  (->series [{[:low :high] :range}])
  (->series [{[:low :high] :ohlc}])

  (str [:low :high])

 ; 
  )