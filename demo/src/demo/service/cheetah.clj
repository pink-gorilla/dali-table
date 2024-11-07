(ns demo.service.cheetah
  (:require
   [tick.core :as t]
   [tech.v3.dataset :as ds]
   [tablecloth.api :as tc]
   [rtable.plot.cheetah :refer [cheetah-ds]]
   [demo.env :refer [env]]))

;; WORKING

(defn stock-csv []
  (let [ds (tc/dataset "https://raw.githubusercontent.com/techascent/tech.ml.dataset/master/test/data/stocks.csv" {:key-fn keyword})
        opts {:columns [; bar
                        {:field "symbol" :caption "symbol" :width 90}
                        {:field "date" :caption "date" :width 220}
                        {:field "price" :caption "price" :width 90
                         :style 'demo.helper.format2/red-color}]}]
    (cheetah-ds env opts ds)))



(comment
  (-> (stock-csv)
      ;(tc/info)     
      )


 ; 
  )

;