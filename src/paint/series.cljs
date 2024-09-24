(ns paint.series
  (:require
   [tech.v3.dataset :as tmlds]
   [ham-fisted.api :as hamf]
   [tech.v3.datatype.statistics]
   [helins.canvas :as hc]
   [goog.dom]
   [reagent.core :as reagent]
   [reagent.dom]))

(defn true-max
  [v]
  (->> (hamf/apply-nan-strategy nil v)
       (hamf/mmax-key identity)))

(defn true-min
  [v]
  (->> (hamf/apply-nan-strategy nil v)
       (hamf/mmin-key identity)))

(defn scale [{:keys [x-stepsize
                     canvas-width
                     canvas-height]
              :or {x-stepsize 20
                   canvas-width 1000
                   canvas-height 400}} ds col]
  (let [c  (get ds col)
        min (true-min c)
        max (true-max c)
        r (- max min)]
    (println "col: " c)
    (println "min: " min "max: " max)
    (map-indexed
     (fn [idx row]
       (let [p (get row col)
             ;x (- canvas-width (* idx x-stepsize))
             x  (* idx x-stepsize)
             y (-> (- p min) (* canvas-height) (/ r) (int))]
         [x y]))
     (tmlds/rows ds))))

(defn line-plot [canvas-opts ctx ds col]
  (println "line-plot..")
  (let [points (scale canvas-opts ds col)
        ;_ (println "points: " points)
        path (-> ctx
                 (hc/color-stroke "blue")
                 (hc/line-width 5)
                 (hc/path))
        plot-one (fn [path [x y]]
                   ;(println "adding line: " x y "path: " path)
                   (hc/line path x y))
        path (reduce plot-one path points)]
    (hc/path-stroke ctx path))
  ctx)

(defn plot-canvas [{:keys [x-stepsize
                           canvas-width
                           canvas-height]
                    :or {x-stepsize 20
                         canvas-width 1000
                         canvas-height 400}
                    :as canvas-opts} node ds]
  (let [canvas-width (* x-stepsize (tmlds/row-count ds))
        _ (println "virtual canvas width: " canvas-width " px")
        c (goog.dom.createElement "canvas")
        _ (goog.dom.setProperties c
                                  #js {"style" (str "height:" canvas-height "px; width:" canvas-width "px;")
                                       "width" (str canvas-width "px")
                                       "height" (str canvas-height "px")})

        _  (goog.dom.appendChild node c)
        ctx (hc/high-dpi (.getContext c
                                      "2d"
                                      #js {"alpha" false}))
        ctx (-> ctx
                (hc/color-fill "yellow")
                (hc/rect-fill 0 0 canvas-width canvas-height))]
    (line-plot canvas-opts ctx ds :close)))

; https://www.geeksforgeeks.org/how-to-get-the-elements-actual-width-and-height-in-javascript/

(defn dimensions [node]
  (let [rect (.getBoundingClientRect node)
        w {:width (.-width rect)
           :height (.-height rect)}]
    w))

(defn plot-canvas-reagent [canvas-opts ds]
  (let [uuid 5]
    (reagent/create-class
     {:display-name "plot-canvas"
      :reagent-render (fn [spec] ;; remember to repeat parameters
                        [:div {:id "supercanvas"
                               :style {:width "100%"
                                       :min-width "100%"
                                       :max-width "100%"
                                       :overflow-x "scroll"
                                       :height "100%"
                                       :min-height "100%"
                                       :max-height "100%"
                                       :overflow-y "hidden"}}])

      :component-did-mount (fn [this] ; oldprops oldstate snapshot
                             ;(println "c-d-m: " this)
                             ;(info (str "jsrender init data: " data))
                             (let [w (dimensions (reagent.dom/dom-node this))
                                   canvas-height (:height w)
                                   canvas-opts (assoc canvas-opts :canvas-height canvas-height)]
                               (println "dimensions2 : " w)
                               (println "new canvas opts:" canvas-opts)
                               (plot-canvas canvas-opts (reagent.dom/dom-node this) ds)))
      :component-did-update (fn [this old-argv]
                              (let [;new-argv (rest (reagent/argv this))
                                    ;[arg1] new-argv
                                    ;{:keys [f data]} arg1
                                    a 1]
                                ;(println "component did update: " this "argv: " new-argv)
                                (println "canvas component did update")
                                (plot-canvas canvas-opts (reagent.dom/dom-node this) ds)))})))

(comment
  (def ds (tmlds/->dataset {:close [3 4 5 3 4 5 6 7 8 12 5 9]}))
  ds
  (scale {:canvas-height 400} ds :close)

  (def bongo
    (.getElementById js/document "bongo"))
  bongo

  (plot-canvas {:canvas-height 400} bongo ds)

 ; 
  )







