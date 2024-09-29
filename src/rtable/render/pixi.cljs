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
   [rtable.data.pixi.scale :refer [scale-bars]]
   [rtable.data.pixi.demo :refer [add-graphics]]
   [rtable.render.pixi.button :refer [create-buttons]]
   ))

(defn pixi-app [node ds]
  (let [app (Application.)
        app-p (.init app (clj->js {:width 800
                                   :height 400
                                   :background "#1099bb"}))]
    (p/then
     app-p
     (fn [r]
       (let [canvas (.-canvas app)
             stage (.-stage app)
             container (Container.)]
         (.appendChild node canvas)
         [stage container])))))


(defn add-bar [graphics idx row]
  (let [{:keys [high low]} row
        bar-width 8
        x (+ (* idx bar-width) (/ bar-width 2))
        height (abs (- high low))
        ]
    ;(.moveTo graphics x high)
    ;(.lineTo graphics x low)
    (.stroke graphics (clj->js {:width 2 :color 0xffffff}))
    (.fill  graphics (clj->js {:color 0xaa4f08}));
    
    ; (.rect  graphics 530 50 140 100)
    (println "adding bar x: " x " y: " low " width: " bar-width " height: " height)
    (.rect graphics
           x low
           bar-width
           height)
    (.fill  graphics (clj->js {:color 0xaa4f08}));
    (.stroke graphics (clj->js {:width 2 :color 0xffffff}))))



(defn draw-bars [container ds]
  (let [ds (scale-bars ds)
        rows (tmlds/rows ds)
        graphics (Graphics.)]
    (println "scaled ds:")
    (println ds)
    (println "container: " container)
    
    (doall (map-indexed (partial add-bar graphics) rows))
    (.addChild container graphics)
    (println "draw-bars done.")))


(defn pixi-render [container ds]
  (draw-bars container ds)
  (println "pixi-render done.")
  nil)


(defn pixi-impl
  [{:keys [style class data]
    :or {style {}
         class ""}}]
    ; https://github.com/reagent-project/reagent/blob/master/doc/CreatingReagentComponents.md
  (let [container-a (atom nil)]
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
                             (let [c-p (pixi-app (reagent.dom/dom-node this) data)]
                               (p/then c-p
                                       (fn [[stage container]]
                                         (reset! container-a container)
                                         ;(add-range-text stage)
                                         (add-graphics stage)
                                         (create-buttons stage)
                                         ;(pixi-render container data)
                                         (pixi-render stage data)
                                         (.addChild stage container)))

                               nil))
      :component-did-update (fn [this old-argv]
                              (let [new-argv (rest (reagent/argv this))
                                    [arg1] new-argv
                                    {:keys [data]} arg1
                                    container @container-a]
                                ;(println "component did update: " this "argv: " new-argv)
                                ;(pixi-render container data)
                                nil))})))

(defn pixi
  [opts]
  [pixi-impl opts])

(defn pixi-ds [opts]
  [d/loading-ui opts :pixi pixi])