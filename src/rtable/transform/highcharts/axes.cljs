(ns rtable.transform.highcharts.axes
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [rtable.transform.highcharts.util :refer [axes-count]]))

;; AXES SPEC

(def axes-default
  {:resize {:enabled true}
   :startOnTick false
   :endOnTick false ; this makes charts bigger, as the space on top/bottom gets reduced
   :margin 0
   :lineWidth 2
   :labels {:align "right" :x -3}})

(defn ohlc-axis [ohlc-height]
  (assoc axes-default
         :top 0
         :height ohlc-height ; "60%"      
         ;:resize {:enabled false}
         ;:title {:text "OHLC"}
         ))

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

(defn small-total [axis-nr small-height]
  (* (dec axis-nr) small-height))

(defn align-heights [container axis-nr small-height]
  (let [small (small-total axis-nr small-height)]
    {:main (- container small)
     :other small-height
     :nr axis-nr}))

(defn nr-other-showable [container main-min-height other-height]
  (let [space-for-small (- container main-min-height)]
    (if (< container main-min-height)
      0 ; container is already below main min height - no smalls.
      (.floor js/Math (/ space-for-small other-height)))))

(defn target-heights [container axis-nr]
  (let [main-min-height 300
        other-large 150
        other-small 100
        scenario-all-large (align-heights container axis-nr other-large)
        scenario-all-small (align-heights container axis-nr other-small)]
    (cond
    ; chart is big enough - show all others in large size
      (> (:main scenario-all-large) main-min-height)
      scenario-all-large
    ; try show all others in small size
      (> (:main scenario-all-small) main-min-height)
      scenario-all-small
      :else
    ; show only those others that fit to the container height
      (let [other-max (nr-other-showable container main-min-height other-small)
            total-possible (inc other-max)
            total (min total-possible axis-nr)]
        (align-heights container total other-small)))))

(defn set-height [yAxis {:keys [main other]} idx]
  (let [axis (aget yAxis idx)
        height (if (= 0 idx)
                 main
                 other)
        top (if (= 0 idx)
              0
              (+ main (* (dec idx) other)))]
    (info "setting axis:" idx " height:" height " top: " top)
    (set! (.-height axis) height)
    (set! (.-top axis) top)))

(defn set-chart-height! [template-js container-height]
  (let [chart (.-chart template-js)]
    (set! (.-height chart) container-height)))

(defn hack-height [template-js container-height]
  (set-chart-height! template-js container-height)
  (let [yAxis (.-yAxis template-js)
        cname (.-name (.-constructor yAxis))
        container-height (- container-height 50) ; 50 is the height of the axis
        ]
    (when (= cname "Array") ; yAxis can also be a map with the only axis
      (let [l (.-length yAxis)
            targets (target-heights container-height l)]
     ; #js [#js {:resize #js {:enabled true}, :lineWidth 2, :labels #js {:align "right", :x -3}, :top 0, 
     ;            :height 600, :title #js {:text "OHLC"}} 
     ; #js {:resize #js {:enabled false}, :lineWidth 2, :labels #js {:align "right", :x -3}, 
     ;      :top 600, :height 100}]    
        (info "hacking highcharts cheight: " container-height "axes# " l " targets: " targets
           ;"y-Axis: " yAxis
              )
        (info "y-Axis: " yAxis)
        ;(set-height yAxis targets 0)    
        (doall (for [idx (range (:nr targets))]
                 (set-height yAxis targets idx)))))))
