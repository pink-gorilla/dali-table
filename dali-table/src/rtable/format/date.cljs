(ns rtable.format.date
  (:require
   [tick.helper :refer [dt-format]]))

;[goog.i18n.DateTimeFormat :as dtf]

(defn dt-yyyymmdd [dt]
  ;(println "dt-yyyymmdd: " dt)
  (if (nil? dt)
    ""
    (if (string? dt)
      dt
      (dt-format "yyyy-MM-dd" dt)
      ;(str dt)
      )))

(defn dt-yyyymmdd-hhmm [dt]
  ;(println "dt-yyyymmdd: " dt)
  (if (nil? dt)
    ""
    (if (string? dt)
      dt
      (dt-format "yyyy-MM-dd HH:mm" dt)
      ;(str dt)
      )))