(ns demo.notebook.highchart-step-missing
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [demo.random-bars :refer [random-bar-ds]]
   [demo.env :refer [env]]))

(def ds
  (random-bar-ds 100))

(def ds-step
  (tc/add-column
   ds
   :ind
   (map-indexed (fn [idx p]
                  (cond
                    (and (> idx 20) (< idx 40))
                    nil
                    :else
                    (- p 2.5))) (:close ds))))

(tap>
 (plot/highstock-ds
  env {:style {:width "600px"
               :height "300px"}
       :charts [{:bar {:type :ohlc
                       :mode :candle}
                 :ind {:type :step
                       :color "red"}}
                {:volume :column}]}
  ds-step))

