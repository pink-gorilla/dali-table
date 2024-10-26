(ns rtable.viewer.pixi.button
  (:require
   ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
   [rtable.viewer.pixi.nav :refer [nav]]))

(defn create-button [stage x y label cb]
  (let [button (Graphics.)
        text (Text. (clj->js {:text label
                              ;:style {:fill "white" :fontSize 16}
                              }))]
    ; button
    (-> button
        (.rect x y 30 30)
        (.fill (clj->js {:color 0xaa4f08})))
    (set! (.-interactive button) true)
    (set! (.-buttonMode true) true)

    (.on button "pointerover" #(set! (.-tint button) 0xAAAAAA))
    (.on button "pointerout" #(set! (.-tint button) 0xFFFFFF))
    (.on button "pointerdown" cb);

    ; text
    (set! (.-x text) (+ x 10))
    (set! (.-y text) (+ y 5))

    (.addChild stage  button)
    (.addChild stage  text)))

(defn create-buttons [stage state]
  (let [y 350
        x-base 400]
    (create-button stage (+ x-base 0) y "<|" #(nav state :begin))
    (create-button stage (+ x-base 40) y "<" #(nav state :prior))
    (create-button stage (+ x-base 80) y ">" #(nav state :next))
    (create-button stage (+ x-base 120) y ">|" #(nav state :end))))