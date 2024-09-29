(ns rtable.render.pixi.nav
  (:require
   [rtable.render.pixi.state :refer [adjust-visible]]
   [rtable.render.pixi.bars :refer [draw-bars]]))

(defn pixi-render [state]
  (draw-bars state)
  (println "pixi-render done.")
  nil)

(defn nav [state op]
  (let [{:keys [end-idx row-count row-count-visible container]} @state
        set-end-idx (fn [end-idx]
                      (swap! state assoc :end-idx end-idx))]

    (case op
      :begin
      (set-end-idx row-count-visible)
      :end
      (set-end-idx row-count)
      :prior
      (set-end-idx (max row-count-visible (- end-idx row-count-visible)))
      :next
      (set-end-idx (min row-count (+ end-idx row-count-visible))))
    (adjust-visible state)
    (.removeChildren container)
    (pixi-render state)))

