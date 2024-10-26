(ns rtable.transform.highcharts.axes
  (:require
   [rtable.transform.highcharts.util :refer [axes-count]]))

;; AXES SPEC

(def axes-default
  {:resize {:enabled true}
   :lineWidth 2
   :labels {:align "right" :x -3}})

(defn ohlc-axis [ohlc-height]
  (assoc axes-default
         :top 0
         :height ohlc-height ; "60%"      
         ;:resize {:enabled false}
         :title {:text "OHLC"}))

(defn other-axis [axes-nr ohlc-height other-height axis-idx]
  (assoc axes-default
  ;:title {:text "Volume"}
   ;:top "65%"
         :top (+ ohlc-height (* axis-idx other-height)) ; first additional axes starts at no = 0
         :height other-height ; "35%"
         :resize {:enabled (< axis-idx (dec (dec axes-nr)))}
   ;:offset 0
         ))

(defn y-axis [charts]
  (let [axes-nr (axes-count charts)
        ohlc-height 600
        other-height 100]
    (into []
          (concat
           [(ohlc-axis ohlc-height)]
           (map #(other-axis axes-nr ohlc-height other-height %) (range (dec axes-nr)))))))

(defn set-chart-height [template charts]
  (let [axes-nr (axes-count charts)
        ohlc-height 600
        other-height 100]
    (assoc-in template [:chart :height]
              (+ ohlc-height
                 (* other-height (dec axes-nr))
                 100 ; size of time window selector
                 ))))
