(ns notebook.dali.rtable.table.cheetah-roundtrips
  (:require
   [clojure.java.io :as io]
   [transit.io :refer [decode]]
   [rtable.plot :as plot]))

(def ds
  (-> "rtable-demo-data/LWhgL6-roundtrips.transit-json"
      io/resource
      slurp
      decode))


(plot/cheetah-ds
 {:style {:width "400px" :height "400px"}
  :columns [; bar
            {:field "asset" :caption "a" :width 90}
            {:field "id" :caption "id" :width 50}
            {:field "side" :caption "side" :width 50}
            {:field "qty" :caption "qty" :width 50}
            ; entry
            {:field "entry-date" :caption "entry-dt" :width 160
             :format 'rtable.format.date/dt-yyyymmdd-hhmm}
            {:field "entry-idx" :caption "entry-idx" :width 50 :style {:bgColor "#5f5"}}
            {:field "entry-price" :caption "entry-p" :width 90 #_:style #_'demo.page.cheetah/red-color
             :format 'rtable.format.number/nr-format-auto}
            ; exit
            {:field "exit-date" :caption "exit-dt" :width 160
             :format 'rtable.format.date/dt-yyyymmdd-hhmm}
            {:field "exit-idx" :caption "exit-idx" :width 50 :style {:bgColor "#5f5"}}
            {:field "exit-price" :caption "exit-p" :width 50
             :format 'rtable.format.number/nr-format-auto}
            {:field "exit-reason" :caption "exit-reason" :width 90}
            ; metrics
            {:field "bars" :caption "bars" :width 50}
            {:field "win?" :caption "win?" :width 50
             ;:style blue-color :format f/format-bool
             }
            {:field "ret-abs" :caption "ret-abs" :width 50}
            {:field "ret-prct" :caption "ret-prct" :width 50
             :format 'rtable.format.number/prct}
            {:field "ret-log" :caption "ret-log" :width 50}
            ;; nav
            {:field "cum-ret-volume" :caption "cum-ret-volume" :width 50}
            {:field "cum-ret-abs" :caption "cum-ret-abs" :width 50}
            {:field "cum-ret-log" :caption "cum-ret-log" :width 50}
            {:field "nav" :caption "nav" :width 50
             :format 'rtable.format.number/nr-format-auto}]} ds)
