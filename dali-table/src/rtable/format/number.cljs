(ns rtable.format.number
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [goog.i18n.NumberFormat.Format])
  (:import
   (goog.i18n NumberFormat)
   (goog.i18n.NumberFormat Format)))

(defn str-format
  "Formats a string using goog.string.format.
   e.g: (format \"Cost: %.2f\" 10.0234)"
  [fmt & args]
  (apply gstring/format fmt args))

(defn format-number [sformat nr]
  (if (nil? nr)
    "-"
    (if (string? nr)
      nr
      (str-format sformat nr))))

(defn fmt-nodigits [nr]
  (format-number "%.0f" nr))

(defn round-digit-1  [nr]
  (format-number "%.1f" nr))

(defn round-digit-2  [nr]
  (format-number "%.2f" nr))

(defn nr-format-auto [nr]
  (cond
    (nil? nr) "-"
    (string? nr) nr
    :else  (let [nr-abs (.abs js/Math nr)]
             (format-number (cond
                              (> nr-abs 10000) "%.0f"
                              (> nr-abs 1000) "%.1f"
                              (> nr-abs 100) "%.2f"
                              (> nr-abs 10) "%.3f"
                              (> nr-abs 1) "%.4f"
                              :else "%.5f")
                            nr))))

(defn prct [nr]
  (if (and nr (number? nr))
    (.toLocaleString nr js/undefined (clj->js {:style "percent"
                                               :minimumFractionDigits 2}))
    "-"))