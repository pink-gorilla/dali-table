(ns rtable.viewer.cheetah
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [reagent.dom]
   [tech.v3.dataset :as tmlds]
   ["cheetah-grid" :as cheetah-grid :refer [ListGrid data]]))

; https://github.com/future-architect/cheetah-grid
; https://future-architect.github.io/cheetah-grid/documents/

(def event-types (js->clj
                  (.-EVENT_TYPE ListGrid)
                  :keywordize-keys true))

;(println "CHEETAH EVENT-TYPES: " event-types)

(defn render-cheetah-data [node columns data watch]
  (let [grid (ListGrid.
              (clj->js {:parentElement node
                        :header columns
                        :records data}))]
    (when watch
      (.listen grid
               (:CHANGED_VALUE event-types)
               (fn [row]
                 (watch (js->clj row :keywordize-keys true)))))
    grid))

(defn cheetah
  [{:keys [style class
           columns
           data
           watch]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (let [grid-a (atom nil)
        my-ref (atom nil)]
    (reagent/create-class
     {:display-name "cheetah-data"
      :reagent-render (fn [{:keys [style class]
                            :or {style {}
                                 class ""}}] ;; remember to repeat parameters
                        [:div {:style style
                               :class class
                               :ref (fn [el] (reset! my-ref el))}])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "cheetah mounted." this)
                             (let [grid (render-cheetah-data @my-ref columns data watch)]
                               (reset! grid-a grid)
                               nil))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [columns data watch]} arg1]
                                ;(println "cheetah update." this)
                                (.dispose @grid-a)
                                (let [grid (render-cheetah-data @my-ref columns data watch)]
                                  (reset! grid-a grid)
                                  nil)))})))

(def CachedDataSource (.-CachedDataSource data))

(defn create-datasource [columns ds]
  (println "creating datasource..")
  (let [count (tmlds/row-count ds)
        format-dict (->> columns
                         (filter (fn [col]
                                   (let [f (:format col)]
                                     (and f (fn? f)))))
                         (map (fn [{:keys [field format format-args]}]
                                [field (if format-args
                                         (apply partial format format-args)
                                         format)]))
                         (into {}))

        format-row (fn [row]
                     (let [formatted-vals (->> format-dict
                                               (map (fn [[field f]]
                                                      (let [field-kw (keyword field)
                                                            v (get row field-kw)]
                                                        [field-kw (f v)])))
                                               (into {}))]
                       (merge row formatted-vals)))
        ;_ (println "columns: " columns)
        ;_ (println "format-dict: " format-dict)
        ds (CachedDataSource. (clj->js {:get (fn [row-idx]
                                               ;(println "getting row-idx: " row-idx)
                                               (let [row (tmlds/row-at ds row-idx)
                                                     row-formatted (format-row row)
                                                     row-js (clj->js row-formatted)]
                                                 ;(println "row: " row)
                                                 ;(println "row-formatted: " row-formatted)
                                                 row-js))

                                        :length count}))]
    ;(println "ds count: " count " ds: " ds)
    ds))

;; row:
;;  {:pweek-low 189.493, :open 190.848, :p1-low 190.351, 
;;   :date #time/instant "2024-08-29T21:50:00Z", :doji false, 
;;   :cross-up-c false, :below-band false, :long-signal false, 
;;   :p0-high 191.537, :cross-down-c false, :close 190.851, 
;;   :volume 34, :high 190.851, :above-band false, :low 190.846, 
;;   :p0-low 190.225, :entry :flat, :daily-atr-upper 191.9889, :short-signal false, 
;;   :cross-up false, :pweek-high 192.026, :daily-atr-mid 190.845, :cross-down false,
;;   :asset GBP/JPY, :p1-high 191.451, :daily-atr-lower 189.7011}

(defn render-cheetah-ds [node columns ds]
  ;(println "render-cheetah ds cols: " columns)
  ;(println "cheetah ds: "  ds)
  (ListGrid.
   (clj->js {:parentElement node
             :header columns
             :dataSource (create-datasource columns ds)})))

(defn cheetah-ds
  [{:keys [style class
           columns
           ds]
    :or {style {:width "100%" :height "100%"}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (let [grid-a (atom nil)
        my-ref (atom nil)]
    (reagent/create-class
     {:display-name "cheetah-ds"
      :reagent-render (fn [{:keys [style class]
                            :or {style {:width "100%" :height "100%"}
                                 class ""}}] ;; remember to repeat parameters
                        [:div {:style style
                               :class class
                               :ref (fn [el] (reset! my-ref el))}])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             (info "cheetah mount.")
                             (let [grid (render-cheetah-ds @my-ref columns ds)]
                               (reset! grid-a grid)
                               nil))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [columns ds]} arg1]
                                (info "cheetah update.")
                                (when @grid-a
                                  (.dispose @grid-a))
                                (let [grid (render-cheetah-ds @my-ref columns ds)]
                                  (reset! grid-a grid)
                                  nil)))})))



