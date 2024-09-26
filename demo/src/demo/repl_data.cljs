(ns repl-data
  (:require
   [rtable.data.cheetah]
   [rtable.data.aggrid]
   [rtable.data :refer [load-and-transform2]]))

(load-and-transform2
 {:datatype :cheetah
  :url "/r/bars-1m-full.transit-json"
  :style {:width "100%" :height "100%"}
  :columns [; bar
            {:field "asset" :caption "a" :width 90}
            {:field "date" :caption "d" :width 220
             ;:style 'demo.page.cheetah/bad-fn
             }
            {:field "open" :caption "o" :width 90
             :style 'demo.page.cheetah/red-color}]})

(load-and-transform2
 {:datatype :aggrid
  :url  "/r/bars-1m-full.transit-json"
  :style {;:width "800px" :height "600px"
          :width "100%" :height "100%"}
          ;:theme "material" ;
  :columns [{:field "close" :header "C" :resizable true}
            {:field "spike"
             :cellStyle {:color "red" :background-color "green"}
             :resizable true}
            {:field "doji"
             :type "rightAligned"
             :resizable true}]})

