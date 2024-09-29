(ns rtable.render.pixi.button
   (:require
    [taoensso.timbre :refer-macros [info warn error]]
    ["pixi.js" :as pixi :refer [Application Container Graphics Text]]
    ))

(defn create-button [stage x y label cb]
  (let [button (Graphics.)
        text (Text. (clj->js {:text label
                              ;:style {:fill "white" :fontSize 16}
                              }))]
    ; button
    (-> button
       (.rect x y 30 30)
       (.fill (clj->js {:color 0xaa4f08})))
    (set! (.-interactive button) true )
    (set! (.-buttonMode true) true)
    ;button.on ('pointerdown', callback);
    ;button.on ('pointerover', () => button.tint = 0xAAAAAA);
    ;button.on ('pointerout', () => button.tint = 0xFFFFFF);
    ; text
    (set! (.-x text) (+ x 10))
    (set! (.-y text) (+ y 5))

    (.addChild stage  button)
    (.addChild stage  text)))


(defn create-buttons [stage]
  (let [y 350
        x-base 400]
    (create-button stage (+ x-base 0) y "<|" nil)
    (create-button stage (+ x-base 40) y "<" nil)
    (create-button stage (+ x-base 80) y ">" nil)
    (create-button stage (+ x-base 120) y ">|" nil)))