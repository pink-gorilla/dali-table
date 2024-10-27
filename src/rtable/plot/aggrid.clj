(ns rtable.plot.aggrid
  (:require
   [tick.core :as t]
   [tablecloth.api :as tc]
   [dali.spec :refer [create-dali-spec]]
   [dali.transform.transit :refer [save-transit]]))

;; DATE FORMAT

(defn create-format-date [zone]
  (let [fmt (t/formatter "YYYY-MM-dd HH:mm")]
    (fn [dt]
      (let [dt (-> dt
                   (t/zoned-date-time)
                   (t/in zone))]
        (t/format fmt dt)))))

(comment
  ((create-format-date "UTC") (t/instant))
  ((create-format-date "America/New_York") (t/instant))
;  
  )

(defn date->string  [timezone ds]
  (let [fmt (create-format-date timezone)]
    (tc/add-column ds :date (map fmt (:date ds)))))

(defn format-date [ds spec]
  (let [tz (or (:timezone spec) "UTC")]
    (date->string tz ds)))

;; SELECT COLUMNS

(defn ensure-keyword [v]
  (if (string? v)
    (keyword v)
    v))

(defn agtable-cols [spec]
  (->> (:columns spec)
       (map :field)
       (map ensure-keyword)))

(defn select-columns [ds spec]
  (let [columns (agtable-cols spec)]
    (tc/select-columns ds columns)))

(defn aggrid-ds
  "returns a dali specification. 
    spec must follow r-table spec format.
    The ui shows a table with specified columns,
    Specified formats, created from the ds"
  [{:keys [style class columns]
    :or {style {:width "100%" :height "100%"}
         class ""}
    :as opts}
   ds]
  (assert columns ":columns key required for aggrid-ds dali spec.")
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.aggrid/aggrid
    :transform-fn 'rtable.transform.aggrid/load-and-transform-aggrid
    :data {:style style
           :class class
           :columns columns
           :load (-> ds
                     ;(format-date opts)
                     (select-columns opts)
                     #_(tc/rename-columns {:open "open"
                                           :high "high"})
                     (save-transit))}}))