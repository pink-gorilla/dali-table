(ns demo.service.aggrid
  (:require
   [tablecloth.api :as tc]
   [rtable.plot.aggrid :refer [aggrid-ds]]
   [demo.env :refer [env]]))

;; WORKING

(defn stock-csv []
  (let [ds (tc/dataset "https://raw.githubusercontent.com/techascent/tech.ml.dataset/master/test/data/stocks.csv" {:key-fn keyword})
        opts {:columns [; bar
                        {:field "symbol" :header "symbol" :width 90 :resizable true}
                        {:field "date" :header "date" :width 220 :resizable true}
                        {:field "price" :header "price" :width 90 :resizable true
                         :cellStyle {:color "red" :background-color "green"}
                         :type "rightAligned"}]}]
    (aggrid-ds env opts ds)))

(comment
  (-> (stock-csv)
      ;(tc/info)     
      )
; 
  )

;