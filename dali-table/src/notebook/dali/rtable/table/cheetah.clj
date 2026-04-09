(ns notebook.dali.rtable.table.cheetah
  (:require
   [tablecloth.api :as tc]
   [rtable.plot.cheetah :refer [cheetah-ds]]))

(let [ds (tc/dataset "https://raw.githubusercontent.com/techascent/tech.ml.dataset/master/test/data/stocks.csv" {:key-fn keyword})
      opts {:style {:width "100%"} ; make it as wide as the notebook-viewer
            :columns [; bar
                      {:field "symbol" :caption "symbol" :width 90}
                      {:field "date" :caption "date" :width 220}
                      {:field "price" :caption "price" :width 90
                         ;:style 'demo.helper.format2/red-color
                       }]}]
  (cheetah-ds opts ds))

