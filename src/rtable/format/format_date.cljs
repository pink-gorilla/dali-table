(ns rtable.format.format-date
  (:require
   [tick.helper :refer [dt-format]]))

(defn dt-yyyymmdd [dt]
  ;(println "dt-yyyymmdd: " dt)
  (if (nil? dt)
    ""
    (if (string? dt)
      dt
      (dt-format "YYYY-MM-dd" dt)
      ;(str dt)
      )))

(defn dt-yyyymmdd-hhmm [dt]
  ;(println "dt-yyyymmdd: " dt)
  (if (nil? dt)
    ""
    (if (string? dt)
      dt
      (dt-format "YYYY-MM-dd HH:mm" dt)
      ;(str dt)
      )))