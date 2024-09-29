(ns rtable.render.pixi.line
  (:require
   [tech.v3.dataset :as tmlds]
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   [rtable.color :refer [set-color]]
   [rtable.render.pixi.scale :refer [scale-col]]))

(defn add-line [graphics step-px col idx row]
  (let [x-center (* idx step-px)
        price (get row col)]
    (if (= 0 idx)
      (do 
        ;(println "idx=0 price:" price "x: " x-center)
        (.moveTo graphics x-center price))
      (do 
        ;(println "idx: " idx " price:" price "x: " x-center)
        (.lineTo graphics x-center price))
      )))

(defn draw-line [state height price-range col color]
  (let [color2 (set-color color) ; 0xaa4f08
        {:keys [ds-visible container step-px]} @state
        ds-visible (scale-col ds-visible height price-range col)
        rows (tmlds/rows ds-visible)
        graphics (Graphics.)]
    (doall (map-indexed (partial add-line graphics step-px col) rows))
    (.stroke graphics (clj->js {:width 2 :color color2}))
    (.addChild container graphics)
    (println "draw-bars done.")))