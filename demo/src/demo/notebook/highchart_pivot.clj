(ns demo.notebook.highchart-pivot
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [demo.random-bars :refer [random-bar-ds]]
   [demo.env :refer [env]]))

(def col-vec
  (map (fn [i]
         (case i
           30 [95.0 100.0 110.0]
           80 [85.5 99.9 101.1]
           nil)) (range 100)))

(def ds
  (-> (random-bar-ds 100)
      (tc/add-column :pivot col-vec)))

ds

(tap>
 (plot/highstock-ds
  env {:style {:width "600px"
               :height "300px"}
       :charts [{:close {:type :line
                         :color "orange-3"}
                 :bar {:type :ohlc
                       :mode :candle}
                 :pivot {:type :point
                         :vec? true
                         :color "orange-3"
                         ;:color "#FF0000"
                         :marker "square"
                         :radius 5}}
                {:volume :column}]}
  ds))


