(ns rtable.tmlds.highcharts
  (:require
   [clojure.set]
   [tick.core :as t]
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [cquant.tmlds :refer [GET]]))

(defn col->series
  "extracts one column from ds 
   in format needed by highchart"
  [bar-study-epoch-ds col]
  (let [r (tmlds/mapseq-reader bar-study-epoch-ds)]
    (mapv (juxt :epoch col) r)))

(defn ohlc->series
  "extracts ohlc series
   in format needed by highchart
   [[1560864600000,49.01,50.07,48.8,49.61]
    [1560951000000,49.92,49.97,49.33,49.47]]"
  [bar-study-epoch-ds]
  (let [r (tmlds/mapseq-reader bar-study-epoch-ds)]
    (mapv (juxt :epoch :open :high :low :close :volume) r)))

(def ds-a
  (GET "/r/signal-no-date.transit-json"))

(def ds-a
  (GET "/r/stocks.transit-json"))

@ds-a

(meta @ds-a)

(meta (:date @ds-a))

(tmlds/column-map @ds-a "epoch" #(-> % t/instant t/long) [:date])

(col)

