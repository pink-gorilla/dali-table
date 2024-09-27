(ns rtable.data.highcharts.default)

(def default-template
  {:xAxis    {:crosshair {:snap true}
              :resize {:enabled true}
              ;:categories (:labels data)  
              }

   ;:title {:text title}

   :tooltip {:style {:width "200px"}
             :valueDecimals 4
             ;:valueSuffix " %"
             :shared true}

   ; webgl boost enabled by default
   :boost false
   #_:boost #_{:useGPUTranslations true
               :seriesThreshold 5 ; Chart-level boost when there are more than 5 series in the chart
               :debug {:timeSetup true
                       :timeSeriesProcessing true
                       :timeKDTree true
                       :timeBufferCopy  true
                       :timeRendering true}}

   :chart {:height 1000 ; gets overwritten by set-chart-height
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
           :time {:useUTC true}}
   :plotOptions {:series {:animation 0
                            ;:label {;:pointStart 2010
                            ;        :connectorAllowed false}
                          }
                 :candlestick {; down
                               :color "red"
                               :lineColor "red"
                               ; up
                               :upColor "blue"
                               :upLineColor "blue"}}

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
  [{:bar {:type :candlestick}}
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



