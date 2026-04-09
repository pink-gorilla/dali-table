(ns notebook.dali.rtable.table.rtable
  (:require
   [rtable.plot :as plot]
   [notebook.dali.rtable.random-bars :refer [random-bar-ds]]))

(def ds (random-bar-ds 200))

ds

(plot/rtable-ds {:class "table-head-fixed padding-sm table-red table-striped table-hover"
                 :style {:width "400px"
                         :height "400px"
                         :border "3px solid green"}
                 :columns [{:path :date :format 'rtable.format.format-date/dt-yyyymmdd-hhmm :max-width "80px"}
                           {:path :close :format 'rtable.format.format-number/fmt-nodigits}
                           {:path :volume}]}
                ds)
