(ns demo.page.viewer.paint
  (:require
   [tech.v3.dataset :as tmlds]
   [paint.series :refer [plot-canvas-reagent]]))

(defn make-ds [n]
  (tmlds/->dataset {:close  (take n (cycle
                                     [1 4 5 3 4 5 6 7 8 12 5 9
                                      30 40 4 50 20 30 10 20 40]))
                    :a (take n (repeatedly rand))}))

(def ds-small (make-ds 100))
(def ds-big (make-ds 6080))

; 5000 * 20 = 100000
; Chrome: Maximum height/width: 32,767 pixels

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   ;"paint demo"
   #_[:div {:style {:width "800px" :height "200px"}
            :id "bongo"}]

    ;[plot-canvas-reagent {:canvas-height 400 :canvas-width 1000} ds-small]
    ;[plot-canvas-reagent {:canvas-height 400 :canvas-width 2000} ds-big]
    ;[plot-canvas-reagent {:canvas-height 600 :canvas-width 2000 :x-stepsize 10} ds-big]
   [plot-canvas-reagent {:canvas-height 700 :canvas-width 3000 :x-stepsize 5} ds-big]])