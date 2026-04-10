(ns notebook.dali.rtable.chart.plotly
  (:require
   [rtable.plot :as plot]))

(plot/echarts
 {:style {:width "600px"
          :height "300px"}
  :data {:xAxis {:type "category"
                 :data ["Mon" "Tue" "Wed" "Thu" "Fri" "Sat" "Sun"]}
         :yAxis {:type "value"}
         :series [{:data [150 230 224 218 135 147 260]
                   :type "line"}]}})
(plot/plotly
 {:layout {:title "A surface plot"}
  :data [{:z [[1 2 3] [3 2 1]]
          :type "surface"}]})


(plot/plotly {:layout {:title "A simple scatter plot with lines and markers"}
              :data [{:x [1 2 3 4]
                      :y [10 15 13 17]
                      :mode "markers"
                      :type "scatter"}
                     {:x [2 3 4 5]
                      :y [16 5 11 9]
                      :mode "lines"
                      :type "scatter"}
                     {:x [1 2 3 4]
                      :y [12 9 15 12]
                      :mode "lines+markers"
                      :type "scatter"}]})

;; ## Example with customized options

(plot/plotly {:layout {:margin {:l 20 :r 0 :b 20 :t 0}
                       :autosize false
                       :width 300
                       :height 200}
              :config {:displayModeBar false
                       :displayLogo false}
              :data [{:x ["giraffes" "orangutans" "monkeys"]
                      :y [20 14 23]
                      :type "bar"}]})