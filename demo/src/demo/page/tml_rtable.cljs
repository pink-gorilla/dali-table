(ns demo.page.tml-rtable
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [tech.v3.dataset :as tmlds]
   [cquant.tmlds :refer [GET]]
   [rtable.rtable :refer [rtable]]
   ;[rtable.cell :refer [format-boolean]]
   ))
 
 (def opts 
   {:class "table-blue table-hover table-auto"
    :style {:width "50vw"
            :height "40vh"
            :border "3px solid green"}})

  (defn create-row-getter [ds col]
    (let [col-data (-> ds (tmlds/column col) (tmlds/column->data) :data)]
      (println "col " col " data: " col-data)
      (fn [row-idx]
        (println "getting row: " row-idx)
        (or (-> (aget col-data row-idx) pr-str) "?"))))

 

 (defn cols  [ds]
    [{;:expr (create-row-getter ds :close)
      :path :close
      :format (fn [v] (str "#" v))} ;; format
     ;{:path (create-row-getter ds :spike) 
     ; :header "link"
     ; :render-cell (fn [col-info row]
     ;                [:a {:href (str "/person/id/" (:id row))}
     ;                 [:span "goto-person"]])} ;; render-cell
     {:path :doji
      :max-width "60px"
      :attrs (fn [v] {:class "bg-red-300"})} ;; attrs
     {:path :spike-doji ; :format format-boolean
     }
     {:path :doji-v  :max-width "150px"}
     {:path :spike-v  :max-width "100px"}
     {:path :spike-doji-v  :max-width "100px"}
     {:path :long  :max-width "100px"}
     {:path :short  :max-width "100px"}
     ])
 
(defn ds-rtable [ds]
   [rtable opts (cols ds) (tmlds/rows ds)])


(defn ds-rtable-url [url]
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
           [ds-rtable @ds-a])
         [:button {:on-click load-ds} (str "load " url)])])))

(defn show-page []
  [:div
   [:p.text-big.text-blue-900.text-bold " tml-ds in rtable .."]
   [:div.bg-green-500.m-5.p-5
    [ds-rtable-url "/r/signal-no-date.transit-json"]]])

(defn page [_route]
  [show-page])
