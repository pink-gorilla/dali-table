(ns demo.helper.format
  (:require
   [goog.events :as events]
   [goog.i18n.NumberFormat.Format])
  (:import
   (goog.i18n NumberFormat)
   (goog.i18n.NumberFormat Format)))

;; this namespace needs compilation.
;; move it to ui-repl or some other place.

(def nff (NumberFormat. Format/DECIMAL))

(defn format-number
  [num]
  (.format nff (str num)))