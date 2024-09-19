(ns demo.experiment
  (:require
   [tick.core :as t]
   [tech.v3.dataset :as ds]
   [tablecloth.api :as tc]
   [tech.v3.libs.clj-transit :as tech-transit]
   [tech.v3.io :as io]))

(defn t->file
  [ds fname]
  (with-open [outs (io/output-stream! fname)]
    (tech-transit/dataset->transit ds outs)))

;; WORKING

(defonce stocks (tc/dataset "https://raw.githubusercontent.com/techascent/tech.ml.dataset/master/test/data/stocks.csv" {:key-fn keyword}))
(tc/info stocks)

(t->file stocks "stocks.transit-json")


(defn ds->transit-json-file
  [ds fname]
  (with-open [outs (io/output-stream! fname)]
    (tech-transit/dataset->transit ds outs :json tech-transit/java-time-write-handlers)))

(ds->transit-json-file stocks "stocks2.transit-json")


(defonce dailypivots (tc/dataset "resources/public/daily-pivots.csv" {:key-fn keyword}))
(tc/info dailypivots)
(ds->transit-json-file stocks "dailypivots.transit-json")

(defonce signal (tc/dataset "resources/public/signal.csv" {:key-fn keyword}))
(tc/info signal)

(-> signal
    (tc/select-columns  [;:date
                         :close
                         :spike
                         :doji
                         :spike-doji
                         :doji-v
                         :spike-v
                         :spike-doji-v
                         :long
                         :short])
    ;(ds->transit-json-file "signal-no-date.transit-json")
    (t->file "signal-no-date.transit-json"))

(defn sanitize-date [ds]
  (tc/convert-types ds {:date [:instant #(t/instant %)]}))

(-> signal
    (sanitize-date)
    (tc/clone)
    (tc/write! "signal-instant.csv"))

;; NOT WORKING

(t->file signal "signal.transit-json")
;; => Execution error at com.cognitect.transit.impl.AbstractEmitter/marshal (AbstractEmitter.java:195).
;;    Not supported: class java.time.ZonedDateTime


(t->file (sanitize-date signal) "signal.transit-json")
;; => Execution error at com.cognitect.transit.impl.AbstractEmitter/marshal (AbstractEmitter.java:195).
;;    Not supported: class org.roaringbitmap.RoaringBitmap

(-> signal
    (tc/select-columns  [:date
                         :close
                         :spike
                         :doji
                         :spike-doji
                         :doji-v
                         :spike-v
                         :spike-doji-v
                         :long
                         :short])
    (sanitize-date)
    (ds->transit-json-file "signal4.transit-json")
    (t->file "signal4.transit-json"))

(ds->transit-json-file (sanitize-date signal) "signal.transit-json")


(-> (tc/dataset "signal-instant.csv" {:key-fn keyword})
    (sanitize-date)
    (tc/clone)
    ;(tc/clone)
    ;(tc/info)
    (t->file "signal-instant.transit-json"))
    


(-> (tc/dataset {:date [(t/zoned-date-time)]
                 :a [1]
                 :b [2.0]})
    (sanitize-date)
    (t->file "zoned.transit-json")
    )
 
 


