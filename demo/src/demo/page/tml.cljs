(ns demo.page.tml
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [transit.cljs-ajax :refer [GET]]
   [cquant.tmlds :refer [ds-txt]]))

;; test to load techml-dataset encoded in transit 
;; and print it to text.

(def ds
  (tmlds/->dataset
   {:a (range 100)
    :b (take 100 (cycle [:a :b :c]))
    :c (take 100 (cycle ["one" "two" "three"]))}))

(defn ds-url [url]
  (let [ds-a (r/atom nil)
        load-ds (fn [& _]
                  (-> (GET url)
                      (p/then (fn [ds]
                                (reset! ds-a ds)))
                      (p/catch (fn [err]
                                 (reset! ds-a :load-error)
                                 (println "could not load ds: " url " err: " err)))))]

    (fn [url]
      [:div
       (if @ds-a
         (if (= @ds-a :load-error)
           [:p "load error!"]
           [ds-txt @ds-a])
         [:button {:on-click load-ds} (str "load " url)])])))

(defn show-page []
  [:div
   [:p.text-big.text-blue-900.text-bold " tml dataset transit-json-load and print"]

   [:div.bg-green-500.m-5.p-5
    [:h1 "ds generated in the browser"]
    [ds-txt ds]
    ;; column
    ;[:p "get col :b" (-> ds :b pr-str)]
    [:p "column :b" (-> ds (tmlds/column :b) pr-str)]
    [:p "column-data :b " (-> ds (tmlds/column :b) (tmlds/column->data) pr-str)]

    ; row
    [:p "rowvec-at 1 " (-> ds (tmlds/rowvec-at 1) pr-str)]
    ; cell
    [:p "column-data :b :data " (-> ds
                                    (tmlds/column :b)
                                    (tmlds/column->data)
                                    :data
                                    pr-str)]

    [:p "column :b aget 2: " (-> ds :b (aget 2) pr-str)]
    [:p "column-data :b :data aget 2: " (-> ds (tmlds/column :b) (tmlds/column->data) :data (aget 2) pr-str)]]

   [:div.bg-green-500.m-5.p-5
    [:h1 "static ds loaded from server"]
    [ds-url "/r/dailypivots.transit-json"]
    [ds-url "/r/stocks.transit-json"]
    [ds-url "/r/signal-no-date.transit-json"]]])

(defn page [_route]
  [show-page])

