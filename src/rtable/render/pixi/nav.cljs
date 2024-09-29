(ns rtable.render.pixi.nav
  (:require
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   ["@pixi/ui" :refer [Slider]]
   [rtable.render.pixi.state :refer [adjust-visible]]
   [rtable.render.pixi.scale :refer [determine-range-bars determine-range-col]]
   [rtable.render.pixi.bars :refer [draw-bars]]
   [rtable.render.pixi.line :refer [draw-line]]
   ))

(defn pixi-render [state]
  (let [{:keys [ds-visible]} @state
        price-range (determine-range-bars ds-visible)
        price-range2 (determine-range-col ds-visible :close)
        ]
    (println "price-range: " price-range)
    (draw-bars state 400 price-range)
    (draw-line state 400 price-range2 :close)
    (println "pixi-render done.")  
    )
  nil)

(defn nav
  ([state op]
   (nav state op -1))
  ([state op new-end-idx]
   (let [{:keys [end-idx row-count row-count-visible container]} @state
         set-end-idx (fn [end-idx]
                       (swap! state assoc :end-idx end-idx))]

     (case op
       :idx
       (set-end-idx new-end-idx)
       :begin
       (set-end-idx row-count-visible)
       :end
       (set-end-idx row-count)
       :prior
       (set-end-idx (max row-count-visible (- end-idx row-count-visible)))
       :next
       (set-end-idx (min row-count (+ end-idx row-count-visible))))
     (adjust-visible state)
     (.removeChildren ^Container container)
     (pixi-render state))))


(defn create-slider [state]
  (let [bg (Graphics.)
        fill (Graphics.)
        slider (Graphics.)
        {:keys [end-idx row-count-visible row-count]} @state]
    (-> bg
        ;(.beginFill 0xAAAAAA)
        ;(.drawRoundedRect bg 0 0 400 20 1)
        ;(.beginFill 0xFFFFFF)
        ;(.drawRoundedRect 5 5 100 20 1)
        (.rect 10 10 400 20)
        (.fill (clj->js {:color 0xaa4f08})))

    (-> fill
        ;(.beginFill  0xAAAAAA)
        ;(.drawRoundedRect 0 0 400 20 1)
        ;(.beginFill 0xFFFFFF)
        ;(.drawRoundedRect 5 5 100 20 1)
        (.rect 10 10 400 20)
        (.fill (clj->js {:color 0xaa4f08})))
    (-> slider
        ;(.beginFill  0xAAAAAA)
        ;(.drawCircle 0 0 20)
        ;(.beginFill 0xFFFFFF)
        ;(.drawCircle 0, 0, 20)
        (.circle 0, 10, 15)
        (.fill (clj->js {:color 0xde3249 :alpha 1})))

    (let [slider2 (Slider. (clj->js {:bg bg
                                     :fill fill
                                     :slider slider
                                     :value end-idx
                                     :min row-count-visible
                                     :max row-count}))
          container (Container.)]

      ;singleSlider.onUpdate.connect((value) => onChange(`${value}`));
      (.connect (.-onUpdate slider2)
                (fn [value]
                  (println "slider navigated to: " value)
                  (nav state :idx value)))
       ;(.addChild container bg)
       ;(.addChild container fill)
       ;(.addChild container slider)
      ;container
      slider2)))


