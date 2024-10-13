(ns rtable.render.pixi.demo
  (:require
   ["pixi.js" :as pixi :refer [Container Graphics Text]]))

(defn add-range-text [stage]
  (let [text (Text. (clj->js {:text "range-pos"
                              ;:style {:fill "white" :fontSize 16}
                              }))]
    (set! (.-x text) 50)
    (set! (.-y text) 10)
    (.addChild stage text)))

(defn add-graphics [stage]
  (let [graphics (Graphics.)]
    ; Rectangle 2
    (.rect  graphics 530 50 140 100)
    (.fill  graphics (clj->js {:color 0xaa4f08}));
    (.stroke graphics (clj->js {:width 2 :color 0xffffff}))

        ;Circle
    (.circle graphics 100, 250, 50)
    (.fill graphics (clj->js {:color 0xde3249 :alpha 1}))

    (.circle graphics 350, 340, 50)
    (.fill  graphics (clj->js {:color 0xaa4f08}));
    (.stroke graphics (clj->js {:width 2 :color 0xaa4f08}))

;    (.moveTo graphics 200 200)
;    (.lineTo graphics 200 300)
;    (.lineTo graphics 300 600)

    (.stroke graphics (clj->js {:width 10 :color 0xde3249}))
    (.moveTo graphics 50 100)
    (.lineTo graphics 50 400)
    (.lineTo graphics 300 400)
    (.lineTo graphics 300 800)
    (.lineTo graphics 200 700)
    (.stroke graphics (clj->js {:width 10 :color 0xde3249}))

    (.addChild stage graphics)))
