(ns rtable.glidegrid
   (:require
     ["@glideapps/glide-data-grid" :as gg]
     ;["./glideg2" :as gg] 
     ))

  
(def columns
  (clj->js [{:title "First Name" :width 100}
            {:title "Last Name" :width 100}]))

(defn get-data [col row]
  (clj->js 
   {;:kind GridCellKind.Text
    :data "x"
    :displayData "x"
    :allowOverlay false}))
  
(defn ggrid []
  [:> gg/DataEditor {:getCellContent get-data
                     :columns columns 
                     :rows 10}])