(ns rtable.render.pixi.line
  (:require
   [tech.v3.dataset :as tmlds]
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   [rtable.color :refer [set-color]]
   [rtable.render.pixi.scale :refer [scale-col]]))

;; line

(defn add-line [graphics step-px col idx row]
  (let [x-center (* idx step-px)
        price (get row col)]
    (if (= 0 idx)
      (do
        ;(println "idx=0 price:" price "x: " x-center)
        (.moveTo graphics x-center price))
      (do
        ;(println "idx: " idx " price:" price "x: " x-center)
        (.lineTo graphics x-center price)))))


(defn draw-line [state container height price-range col color]
  (let [color2 (set-color color) ; 0xaa4f08
        {:keys [ds-visible step-px]} @state
        ds-visible (scale-col ds-visible height price-range col)
        rows (tmlds/rows ds-visible)
        graphics (Graphics.)]
    (doall (map-indexed (partial add-line graphics step-px col) rows))
    (.stroke graphics (clj->js {:width 2 :color color2}))
    (.addChild container graphics)
    (println "draw-bars done.")))

;; point

(defn add-point [graphics step-px col color idx row]
  (let [x-center (* idx step-px)
        price (get row col)]
    (.circle graphics x-center price 2))
  (.fill graphics (clj->js {:color color :alpha 1})))

(defn draw-points [state container height price-range col color]
  (let [color2 (set-color color)
        {:keys [ds-visible step-px]} @state
        ds-visible (scale-col ds-visible height price-range col)
        rows (tmlds/rows ds-visible)
        graphics (Graphics.)]
    (doall (map-indexed (partial add-point graphics step-px col color2) rows))
    (.addChild container graphics)
    (println "draw-points done.")))


;; signal

(defn add-signal [graphics step-px col color idx row]
  (let [x-center (* idx step-px)
        ;price (get row col)
        y 350
        v (get row col)
        ]
    
    (if (= v true)
      (.circle graphics x-center y 2)
      (.fill graphics (clj->js {:color color :alpha 0.5})))))

(defn draw-signal [state container height price-range col color]
  (let [color2 (set-color color)
        {:keys [ds-visible step-px]} @state
        ds (-> ds-visible

               ;(tmlds/filter-column col #(= % true))
               ;(scale-col height price-range col)
               )
        _ (println "signal col" col " has signals: " (tmlds/row-count ds))
        rows (tmlds/rows ds)
        graphics (Graphics.)]
    (doall (map-indexed (partial add-signal graphics step-px col color2) rows))
    (.addChild container graphics)
    (println "draw-signal done.")))