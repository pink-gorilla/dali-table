(ns notebook.dali.rtable.table.rtable
  (:require
   [rtable.plot :as plot]
   [notebook.dali.random-bars :refer [random-bar-ds]]))

(def ds (random-bar-ds 200))

(plot/rtable {:class "table-head-fixed padding-sm table-red table-striped table-hover"
              :style {:width "400px"
                      :height "300px"
                      :border "3px solid green"}
              :columns [{:path :date :format 'ta.viz.lib.format-date/dt-yyyymmdd-hhmm :max-width "80px"}
                        {:path :close :format 'ta.viz.lib.format-number/fmt-nodigits}
                        {:path :volume}]}
             ds)