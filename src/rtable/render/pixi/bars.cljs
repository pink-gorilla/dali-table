(ns rtable.render.pixi.bars
   (:require
    [tech.v3.dataset :as tmlds]
    ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
    [rtable.render.pixi.scale :refer [scale-bars]]
   ))
  

(defn add-bar [graphics step-px idx row]
  (let [{:keys [high low close open]} row
        bar-width (- step-px 2)
        x-center (* idx step-px)
        x (- x-center (/ bar-width 2))
        height (abs (- open close))
        upper (min open close)
        lower (max open close)
        ]
    ; BAR    
    ;(println "adding bar x: " x " y: " low " width: " bar-width " height: " height)
    (.rect graphics
           x upper
           bar-width
           height)
    (.fill  graphics (clj->js {:color (if (< close open)  0x66CCFF 0xFF3333)}));
    (.stroke graphics (clj->js {:width 1 :color 0xffffff}))

    ; LINES
    (.stroke graphics (clj->js {:width 1 :color 0x66CCFF}))
    (.moveTo graphics x-center upper)
    (.lineTo graphics x-center high)

    (.stroke graphics (clj->js {:width 1 :color 0xFF3333}))
    (.moveTo graphics x-center lower)
    (.lineTo graphics x-center low)
    
    
    
    
    
    
    ))



(defn draw-bars [state]
  (let [{:keys [ds-visible container step-px]} @state
        ds-visible (scale-bars ds-visible)
        rows (tmlds/rows ds-visible)
        graphics (Graphics.)]
    (println "scaled ds:")
    (println ds-visible)
    (println "container: " container)

    (doall (map-indexed (partial add-bar graphics step-px ) rows))
    (.addChild container graphics)
    (println "draw-bars done.")))