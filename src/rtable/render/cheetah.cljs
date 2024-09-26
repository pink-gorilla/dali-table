(ns rtable.render.cheetah
  (:require
   [reagent.core :as reagent]
   [reagent.dom]
   [tech.v3.dataset :as tmlds]
   ["cheetah-grid" :as cheetah-grid :refer [ListGrid data]]
   [rtable.data.cheetah] ; side effects
   [rtable.data :as d]))

(defn render-cheetah-data [node columns data]
  (ListGrid.
   (clj->js {:parentElement node
             :header columns
             :records data})))

(defn cheetah
  [{:keys [style class
           columns
           data]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (reagent/create-class
   {:display-name "cheetah-data"
    :reagent-render (fn [{:keys [style class]
                          :or {style {}
                               class ""}}] ;; remember to repeat parameters
                      [:div {:style style
                             :class class}])
    :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                           (render-cheetah-data (reagent.dom/dom-node this) columns data))
    :component-did-update (fn [this old-argv]
                            (let [new-argv (rest (reagent/argv this))
                                  [arg1] new-argv
                                  {:keys [data-js]} arg1]
                                ;(println "component did update: " this "argv: " new-argv)
                              (render-cheetah-data (reagent.dom/dom-node this) columns data)))}))

(def CachedDataSource (.-CachedDataSource data))

(defn create-datasource [ds]
  ;(println "creating datasource..")
  (let [count (tmlds/row-count ds)
        ds (CachedDataSource. (clj->js {:get (fn [row-idx]
                                               ;(println "getting row-idx: " row-idx)
                                               (clj->js (tmlds/row-at ds row-idx)))
                                        :length count}))]
    ;(println "ds count: " count " ds: " ds)
    ds))

(defn render-cheetah-ds [node columns ds]
  ;(println "render-cheetah ds cols: " columns)
  ;(println "cheetah ds: "  ds)
  (ListGrid.
   (clj->js {:parentElement node
             :header columns
             :dataSource (create-datasource ds)})))

(defn cheetah-ds-impl
  [{:keys [style class
           columns
           ds]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (reagent/create-class
   {:display-name "cheetah-ds"
    :reagent-render (fn [{:keys [style class]
                          :or {style {}
                               class ""}}] ;; remember to repeat parameters
                      [:div {:style style
                             :class class}])
    :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                           (render-cheetah-ds (reagent.dom/dom-node this) columns ds))
    :component-did-update (fn [this old-argv]
                            (let [new-argv (rest (reagent/argv this))
                                  [arg1] new-argv
                                  {:keys [data-js]} arg1]
                                ;(println "component did update: " this "argv: " new-argv)
                              (render-cheetah-ds (reagent.dom/dom-node this) columns ds)))}))

(defn cheetah-ds [opts]
  [d/loading-ui opts :cheetah cheetah-ds-impl])


