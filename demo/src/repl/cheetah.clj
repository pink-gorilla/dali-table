(ns repl.cheetah
  (:require
   [tablecloth.api :as tc]
   [rtable.plot.cheetah :refer [cheetah-ds]]))


(def p (let [ds (tc/dataset "https://raw.githubusercontent.com/techascent/tech.ml.dataset/master/test/data/stocks.csv" {:key-fn keyword})
             opts {:columns [; bar
                             {:field "symbol" :caption "symbol" :width 90}
                             {:field "date" :caption "date" :width 220}
                             {:field "price" :caption "price" :width 90
                               ;:style 'demo.helper.format2/red-color
                              }]}]
         (cheetah-ds opts ds)))

p

(type p)

(require '[dali.store.file :refer [create-dali-file-store]])
(def s (create-dali-file-store {:fpath ".gorilla/public/dali-tap"
                                :rpath "/r/dali-tap"}))


(require '[dali.store :refer [store-data open]])
(def p2 (store-data s p))
(type p2)
p2

(open s {:fmt :transit-json
         :filename "/HmOan..transit.json"})

