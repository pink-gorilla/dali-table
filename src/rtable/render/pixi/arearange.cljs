(ns rtable.render.pixi.arearange
  (:require
   [tech.v3.dataset :as tmlds]
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   [rtable.render.pixi.scale :refer [scale-bars scale-col]]
   [rtable.color :refer [set-color]]))

(defn add-bar [graphics step-px height col1 col2 color idx row]
  (let [; x
        bar-width step-px
        x-center (* idx step-px)
        x x-center
        ; y
        c1 (min height (max 0 (get row col1)))
        c2 (min height (max 0 (get row col2)))
        y (min c1 c2)
        height (abs (- c1 c2))]
    ; BAR    
    ;(println "adding range-bar x: " x "y: " y  " c1: " c1 " c2: " c2  " width: " bar-width " height: " height "color: " color)
    (.rect graphics x y bar-width height)
    (.fill  graphics (clj->js {:color color
                               :alpha 0.5}));
    ;(.stroke graphics (clj->js {:width 1 :color 0xffffff}))
    ))
(defn draw-range [state container height price-range col1 col2 color]
  (let [{:keys [ds-visible step-px]} @state
        color2 (set-color color)
        ds (-> ds-visible
               (scale-col height price-range col1)
               (scale-col height price-range col2))
        rows (tmlds/rows ds)
        graphics (Graphics.)]
    ;(println "scaled ds:")
    ;(println ds-visible)
    (doall (map-indexed (partial add-bar graphics step-px height col1 col2 color2) rows))
    (.addChild container graphics)
    (println "draw-bars done.")))

