(ns rtable.render.pixi.bars
   (:require
    [tech.v3.dataset :as tmlds]
    ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
    [rtable.render.pixi.scale :refer [scale-bars]]
   ))
  

(defn add-bar [graphics idx row]
  (let [{:keys [high low]} row
        bar-width 8
        x (+ (* idx bar-width) (/ bar-width 2))
        height (abs (- high low))]
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



(defn draw-bars [state]
  (let [{:keys [ds-visible container]} @state
        ds-visible (scale-bars ds-visible)
        rows (tmlds/rows ds-visible)
        graphics (Graphics.)]
    (println "scaled ds:")
    (println ds-visible)
    (println "container: " container)

    (doall (map-indexed (partial add-bar graphics) rows))
    (.addChild container graphics)
    (println "draw-bars done.")))