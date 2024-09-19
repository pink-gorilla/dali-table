(ns demo.page.tml
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as ds]
   [cquant.tmlds :refer [GET]]
   [demo.text :refer [text]]))

(defn ds-txt [ds]
  [text
   (with-out-str
     (println ds))])

(def ds
  (ds/->dataset
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
   [:p.text-big.text-blue-900.text-bold " demo .."]

   [:div.bg-green-500.m-5.p-5
    [:h1 "ds generated in the browser"]
    [ds-txt ds]]

   [:div.bg-green-500.m-5.p-5
    [:h1 "static ds loaded from server"]
    [ds-url "/r/dailypivots.transit-json"]
    [ds-url "/r/stocks.transit-json"]
    [ds-url "/r/signal-no-date.transit-json"]

    ]])

(defn page [_route]
  [show-page])

