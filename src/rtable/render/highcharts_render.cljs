(ns rtable.render.highcharts-render
  "Highchart renderer is a pure javascript renderer, the conversion
   of the spec as clj-data to javascript is done in the render-js component"
  (:require
    [taoensso.timbre :refer-macros [info warn error]]
   ;["highcharts" :as highcharts] ; highstock includes highcharts
   ["highcharts/highstock" :as highcharts] ; this brings highstock and highcharts
   ["highcharts/highcharts-more" :as more]
   ["highcharts/modules/annotations" :as annotations] ; annotation module
   ["highcharts/modules/boost" :as boost]
   ["highcharts/modules/debugger" :as debugger]
   ["highcharts/modules/draggable-points" :as draggable-points]
   ["highcharts/modules/drag-panes" :as drag-panes]))

;; highcharts modules:

; boost-canvas data
; full-screen no-data-to-display         
; annotations-advanced

; accessibility 
; arrow-symbols  stock stock-tools
; export-data exporting 
; dependency-wheel heikinashi parallel-coordinates      
; sunburst dotplot histogram-bellcurve pareto tilemap
; hollowcandlestick pathfinder timeline
; item-series pattern-fill treegrid drilldown lollipop                   
; price-indicator treemap dumbbell map pyramid3d variable-pie
; broken-axis marker-clusters sankey variwide bullet streamgraph
; networkgraph series-label vector coloraxis solid-gauge              
; venn current-date-indicator funnel3d offline-exporting         
; sonification windbarb cylinder funnel oldie static-scale              
; wordcloud oldie-polyfills datagrouping gantt xrange
; grid-axis organization heatmap overlapping-datalabels     

(defn add-annotations []
  ;(println "adding highcharts annotation feature..")
  (annotations highcharts))

(defn add-boost []
  ;(println "adding highcharts boost feature..")
  (boost highcharts))

(defn add-debugger []
  ;(println "adding highcharts debugger feature..")
  (debugger highcharts))

(defn add-more []
  ;(println "adding highcharts more feature..")
  (more highcharts))

(defn add-draggable-points []
  ;(println "adding highcharts draggable-points feature..")
  (draggable-points highcharts))

(defn add-drag-panes []
  ;(println "adding highcharts drag-panes feature..")
  (drag-panes highcharts))

(defonce loaded? (atom false))

(defn ensure-extensions-loaded []
  (when-not @loaded?
    (info "loading highchart extensions..")
    (reset! loaded? true)
    (add-debugger)
    (add-annotations)
    (add-more)
    (add-draggable-points)
    (add-drag-panes) ; see https://www.highcharts.com/docs/stock/axis-resizer
    ; Note: The Boost module should be included last. 
    ; This is because it overrides both standard Highcharts functionality, and functionality 
    ; in certain modules (namely treemap, heatmap, bubble, and scatter).
    (add-boost)
    
    ))

(defn render-highchart [dom-node data]
  (ensure-extensions-loaded)
  (highcharts/Chart. dom-node data); //.catch(console.warn);
  )

(defn export [h]
  (println "setting window.hs ..")
  (set! (.-hs js/window) h))

(defn render-highstock [dom-node data]
  (ensure-extensions-loaded)
  (let [hs (highcharts/stockChart. dom-node data); //.catch(console.warn);
        ]
    ;(export hs)
    hs))
