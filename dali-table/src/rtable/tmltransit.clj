(ns rtable.tmltransit
  (:require
   [transit.type.tick :refer [add-tick-handlers!]]
   [cquant.transit.techml :refer [add-techml-dataset-handlers!]]))

; produce side effects once.

(add-techml-dataset-handlers!)

(add-tick-handlers!)