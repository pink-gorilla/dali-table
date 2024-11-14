(ns paint.core
  (:require
   [goog.dom]
   [tech.v3.dataset :as tmlds]
   [helins.canvas :as hc]
   [paint.series :refer [line-plot scale plot-canvas]]))

; https://github.com/helins/canvas.cljs

(defonce c (goog.dom.createElement "canvas"))

(goog.dom.setProperties c
                        #js {"style" "height:400px; width:1000px;"
                             "width" "2000px"})
c
(goog.dom.appendChild js/document.body
                      c)
(def ctx
  (hc/high-dpi (.getContext c
                            "2d"
                            #js {"alpha" false})))

ctx

(defn p [ctx]
  (let [path (-> ctx
                 (hc/color-stroke "blue")
                 (hc/line-width 5)
                 (hc/path)
                   ;(hc/move 100 100)
                 (hc/line 30 30)
                 (hc/line 300 300)
                 (hc/line 100 300)
                 (hc/line 300 700)
                   ;(hc/line 10 10 20 20)
                   ;(hc/arc 170 60 50 0 (* 2  Math/PI))
                 )]
    (hc/path-stroke ctx path))
  ctx)

(-> ctx
    (hc/color-fill "red")
    (hc/rect-fill 0 0 2000 400)

    ;(hc/font "bold 100px serif")
    ;(hc/text-fill 50 100 "Hello")
    ;(hc/shadow 10 10 10 "green")
    ;(hc/text-stroke 100 200 "Hello")
    (p)
    (hc/line-width 5)
    (hc/color-stroke "blue")
    (hc/rect-stroke 10 100 50 20)
    (hc/rect-stroke 310 100 50 20)
    (hc/color-stroke "green")
    (hc/rect-stroke 710 100 50 20)
    (hc/rect-stroke 810 100 50 20)
    (hc/rect-stroke 910 100 50 20)
    (hc/rect-stroke 980 100 50 20))

(def ds (tmlds/->dataset {:close  (take 100 (cycle
                                             [1 4 5 3 4 5 6 7 8 12 5 9
                                              30 40 4 50 20 30 10 20 40]))
                          :a (take 100 (repeatedly rand))}))

ds
(scale {:canvas-height 400} ds :close)

ctx

(line-plot {:canvas-height 400} ctx ds :close)

(line-plot {:canvas-height 2200} ctx ds :a)

(def bongo
  (.getElementById js/document "bongo"))

bongo

bongo.id

(.-width bongo)

(plot-canvas {:canvas-height 400} bongo ds)











