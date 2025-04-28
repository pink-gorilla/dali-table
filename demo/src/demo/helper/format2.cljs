(ns demo.helper.format2
  (:require
   [pinkgorilla.goog.string :as gs]))

(defn format-number [f n]
  (gs/format f n))

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




