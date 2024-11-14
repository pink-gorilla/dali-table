(ns demo.notebook.highchart
  (:require
   [tablecloth.api :as tc]
   [rtable.plot :as plot]
   [demo.random-bars :refer [random-bar-ds]]
   [demo.env :refer [env]]))


(def ds
  (random-bar-ds 100))


(plot/highstock-ds
 env {:style {:width "600px"
              :height "300px"}
      :charts [{:close {:type :line}
                :bar {:type :ohlc
                      :mode :candle}}
               {:volume :column}]}
 ds)



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

(plot/highstock-ds
 env {:style {:width "600px"
              :height "300px"}
      :charts [{:bar {:type :ohlc
                      :mode :candle}
                :ind {:type :step
                      :color "red"}}
               {:volume :column}]}
 ds-step)

