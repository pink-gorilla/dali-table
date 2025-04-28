(ns rtable.transform.highcharts.default
  (:require
   [dali.util.color :refer [set-color]]))

(def default-template
  {:xAxis    [{:crosshair {:snap true}
                            ;:resize {:enabled true}
               :title "bongo27"
                            ;:categories (:labels data)  ; categories are used instead of data
               :allowOverlap true
               ;:top -300
               :scrollbar {:enabled false
                           :showFull false}
               :zIndex 4000}]

;:title {:text title}

   :tooltip {:style {:width "200px"}
             :valueDecimals 4
             ;:valueSuffix " %"
             :shared true}

   ; webgl boost enabled by default
   ;:boost false
   :boost {; boost.js plugin makes chart draw using Canvas instead of SVG when there are a lot of points
           :useGPUTranslations true
           :usePreallocated true
           :seriesThreshold 5 ; Chart-level boost when there are more than 5 series in the chart
           :debug {:timeSetup false
                   :timeSeriesProcessing false
                   :timeKDTree false
                   :timeBufferCopy  false
                   :timeRendering false}}

   :chart {; 2024-11-12 awb99: height may not be set, because it does not get overridden.
           ; setting the height does make the x-axis labels disappear. 
           :height 1000 ; gets overwritten by set-chart-height
           :margin [0 0 0 0]
           ; zoom/pan
           :zooming {; https://api.highcharts.com/highcharts/chart.zooming.type
                     :key "alt" ; Should be set different than chart.panKey.
                     :type "x"}
           :panning {:enabled true
                     :type "x" ; "xy"
                     }
           :panKey "ctrl" ; "alt"" "shift"  "meta"
           ; animation
           :animation false
           :time {:useUTC true}
           :styledMode false ; styled mode needs to be false, so the css does not override custom bar color
           }
   :plotOptions {:series {:animation 0
                            ;:label {;:pointStart 2010
                            ;        :connectorAllowed false}
                          }
                 :candlestick {; down
                               :color (set-color "red-5")  ;"red"
                               :lineColor (set-color "red-5") ; "red"
                               ; up
                               :upColor (set-color "blue-5") ;"blue"
                               :upLineColor (set-color "blue-5") ;"blue"
                               }}

   :credits {:enabled false}

   :accessibility {:enabled false}

   ;; highstock specific starting here: *************************

    ; The navigator is a small series below the main series, displaying a view of the entire data set.
   :navigator {:enabled false}
    ;The range selector is a tool for selecting ranges to display within 
    ; the chart. It provides buttons to select preconfigured ranges in 
    ; the chart, like 1 day, 1 week, 1 month etc. It also provides input 
    ; boxes where min and max dates can be manually input.
   :rangeSelector {:enabled false
                    ;:verticalAlign "top" ; timeframe selector on the top
                    ;:selected 1   
                    ;:x 0
                    ;:y 0
                   }
 ;  
   })

(def default-chart-with-volume
  [{:bar {:type :ohlc
          :mode :candle}}
   {:volume {:type :column}}])

;; series

(def flags
  {:type "flags"
   :shape "squarepin" ; "circlepin"
   :fillColor "rgba(255, 255, 255, .4)"
                           ; :width 16 
                           ;:selected true 
   :allowOverlapX true ; https://stackoverflow.com/questions/53437956/highcharts-highstock-flags-series-issue#:~:text=All%20flags%20are%20not%20presented,set%20to%20false%20by%20default.
   :zIndex 9999
   ; default placement: close on :bar series.
   :onSeries ":bar" ; ":close"
   :onKey "close"})

(def step
  {:type "line" ; step plot is a line plot
   :step true})

(def line
  {:type "line"})

(def column
  {:type "column"})



