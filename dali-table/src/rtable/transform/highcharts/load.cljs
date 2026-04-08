(ns rtable.transform.highcharts.load
  (:require
   [dali.util.color :refer [set-color]]
   [tech.v3.dataset :as tmlds]))

(defn select-signal-contains [ds signal-col v]
  (tmlds/filter ds
                (fn [row]
                  (contains? v (signal-col row)))))

(defn bar-series [series]
  (->> series
       (filter #(= (:type %) "candlestick"))
       first))

(defn create-load-fn [ds series]
  (fn []
    (println "highchart was loaded!")
    (this-as chart
             ;(println "setting xxx to highchart chart")
             (println "styled mode: " (.-styledMode chart))
             (set! (.-xxx js/window) chart)
             (when-let [s (bar-series series)]
               (when-not (empty? (:barcolor s))
                 (println "bar-series idx: " (:idx s) "bar-color: " (:barcolor s))
                 (let [{:keys [column color]} (:barcolor s)
                       filtered-ds (select-signal-contains ds column color)
                       idx-signal-ds  (tmlds/select-columns filtered-ds [:idx column])
                       row-seq (tmlds/rows idx-signal-ds)
                       all-series-js (.-series chart)
                       series-js (aget all-series-js (:idx s))
                       points-js (.-points series-js)]
                   ;(println "filtered count: " (tmlds/row-count idx-signal-ds))
                   ;(println idx-signal-ds)
                   (doall (map (fn [row]
                                 (let [idx (:idx row)
                                       s (get row column)
                                       c (get color s)
                                       chex (set-color c)
                                       _ (println "set color idx: " idx " to " s " color: " chex)
                                       point-js (aget points-js idx)
                                       m {:stroke chex
                                          :fill chex
                                          ; :lineColor chex
                                          }
                                       m-js (clj->js m)
                                       graphic-js (.-graphic point-js)]
                                 ;(set! (.-color point-js) chex)
                                 ; (set! (.-lineColor point-js) chex)
                                 ;(set! (.-stroke point-js) chex)
                                   (.attr graphic-js m-js)
                                   (.log js/console point-js)))
                               row-seq)))))
     ;chart.series[0].points.forEach((point, index) => {
     ;            const previousPoint = chart.series[0].points[index - 1];
    ;            if (
  ;              index > 0 &&
  ;              (point.y - previousPoint.y) > (previousPoint.y * 0.02)
  ;            ) {
  ;              point.graphic.attr({
  ;                stroke: 'green',
  ;              });
  ;            }
  ;          })
             )))

; see:
; https://jsfiddle.net/BlackLabel/bfp4350u/
; https://stackoverflow.com/questions/15531261/accessing-this-in-clojurescript

(defn add-load-fn [template ds series]
  (assoc-in template [:chart :events]
            {:load (create-load-fn ds series)}))




