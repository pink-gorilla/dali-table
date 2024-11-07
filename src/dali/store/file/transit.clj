(ns dali.store.file.transit
  (:refer-clojure :exclude [read])
  (:require
   [dali.store.file :refer [write-file open-file]]
   [cquant.tmlds :refer [ds->transit-json-file transit-json-file->ds]]))

(defmethod write-file "transit-json" [_ filename ds]
  (ds->transit-json-file ds filename))

(defmethod open-file "transit-json" [_ filename]
  (transit-json-file->ds filename))

