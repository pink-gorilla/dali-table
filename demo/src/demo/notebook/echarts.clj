(ns demo.notebook.echarts
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [demo.random-bars :refer [random-bar-ds]]
   [demo.env :refer [env]]))


(plot/echarts
 {:style {:width "600px"
          :height "300px"}
  :data {:xAxis {:type "category"
                 :data ["Mon" "Tue" "Wed" "Thu" "Fri" "Sat" "Sun"]}
         :yAxis {:type "value"}
         :series [{:data [150 230 224 218 135 147 260]
                   :type "line"}]}})
 