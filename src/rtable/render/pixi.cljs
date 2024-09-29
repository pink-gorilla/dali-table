(ns rtable.render.pixi
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as reagent]
   [promesa.core :as p]
   [reagent.dom]
   [tech.v3.dataset :as tmlds]
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   [rtable.data.pixi] ; side effects
   [rtable.data :as d]
   [rtable.data.pixi.demo :refer [add-graphics]]
   [rtable.render.pixi.button :refer [create-buttons]]
   [rtable.render.pixi.nav :refer [pixi-render create-slider]]
   [rtable.render.pixi.state :refer [create-state]]
   ))

(defn pixi-app [node width height]
  (let [app (Application.)
        app-p (.init app (clj->js {:width width
                                   :height height
                                   :background "#1099bb"}))]
    (p/then
     app-p
     (fn [r]
       (let [canvas (.-canvas app)
             stage (.-stage app)
             container (Container.)]
         (.appendChild node canvas)
         [stage container])))))


(defn pixi-impl
  [{:keys [style class data]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (let [state-a (atom nil)]
    (reagent/create-class
     {:display-name "pixi-reagent"
      :reagent-render (fn [{:keys [style class data]
                            :or {style {}
                                 class ""}}] ;; remember to repeat parameters
                        [:div {:style style
                               :class class}])
      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                             (let [width 800
                                   height 400
                                   c-p (pixi-app (reagent.dom/dom-node this) width height)]
                               (p/then c-p
                                       (fn [[stage container]]
                                         (let [state (create-state {:width width
                                                                    :height height
                                                                    :step-px 8}
                                                                    container 
                                                                    data)]
                                         (reset! state-a state)
                                         ;(add-range-text stage)
                                         (add-graphics stage)
                                         (create-buttons stage state)
                                         (let [slider (create-slider state)]
                                           (.addChild stage slider))
                                                                                   
                                         (pixi-render state)
                                          (.addChild stage container)
                                         )))

                               nil))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [data]} arg1
                                    ;container @container-a
                                    ]
                                ;(println "component did update: " this "argv: " new-argv)
                                ;(pixi-render container data)
                                nil))})))

(defn pixi
  [opts]
  [pixi-impl opts])

(defn pixi-ds [opts]
  [d/loading-ui opts :pixi pixi])