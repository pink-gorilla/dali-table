(ns rtable.snippet.rtable
  (:require
   [rtable.plot :as plot]
   [rtable.snippet.data.random-bars :refer [random-bar-ds]]))

(def ds (random-bar-ds 200))

(plot/rtable-ds {:class "table-head-fixed padding-sm table-red table-striped table-hover"
                 :style {:width "50vw"
                         :height "40vh"
                         :border "3px solid green"}
                 :columns [{:path :date :format 'rtable.format.format-date/dt-yyyymmdd-hhmm :max-width "80px"}
                           {:path :close :format 'rtable.format.format-number/fmt-nodigits}
                           {:path :volume}]}
                ds)
