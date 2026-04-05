(ns demo.helper.format2
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [goog.i18n.NumberFormat.Format]
   [goog.i18n.DateTimeFormat :as dtf])
  (:import
   (goog.i18n NumberFormat)
   (goog.i18n.NumberFormat Format)))

(defn format-number
  "Formats a string using goog.string.format.
   e.g: (format \"Cost: %.2f\" 10.0234)"
  [fmt & args]
  (apply gstring/format fmt args))

(defn red-color [row]
  ;(println "red-color for: " row)
  (clj->js {:color "red"}))

(defn blue-color [row]
  ;(println "blue-color for: " row)
  (clj->js {:color "blue"}))

(defn bool-color [row]
  (let [row-clj (js->clj row)
        v (get row-clj "cross-up-c")
        color (if v "blue" "red")]
    ;(println row-clj)
    ;(println "bool color: " color " val: " v)
    (clj->js {:color color})))

(defn format-hidden [v]
  ;(println "setting " v " to xxx")
  "xxx")

(defn format-bool [b]
  (if b "t" "f"))

(defn format-bool2 [big? b]
  ;(println "format-bool2 big? " big? "val: " b)
  (if big?
    (if b "T" "F")
    (if b "t" "f")))




