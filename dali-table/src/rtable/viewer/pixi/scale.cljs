(ns rtable.viewer.pixi.scale
  (:require
   [tech.v3.dataset :as tmlds]
   [ham-fisted.api :as hamf]))

(defn true-max
  [v]
  (->> (hamf/apply-nan-strategy nil v)
       (hamf/mmax-key identity)))

(defn true-min
  [v]
  (->> (hamf/apply-nan-strategy nil v)
       (hamf/mmin-key identity)))

(defn determine-range-bars [ds]
  (let [max-price (true-max (:high ds))
        min-price (true-min (:low ds))
        ;max-price (* max-price 1.02)
        ;min-price (* min-price 0.98)
        ]
    [min-price max-price]))

(defn determine-range-col [ds col]
  (let [cv (get ds col)
        max-price (true-max cv)
        min-price (true-min cv)
        ;max-price (* max-price 1.02)
        ;min-price (* min-price 0.98)
        ]
    [min-price max-price]))

(defn scale-col [ds height price-range col]
  (let [[min-price max-price] price-range
        range-price (- max-price min-price)
        dheight (double height)
        scale (fn [p]
                (long (* (- 1.0 (/ (- p min-price) range-price)) dheight)))]
     ; max-price -> 1   
     ; min-price -> 0 -> range -> height
     ; (1 - ((p - min-price) / range) ) 
    (tmlds/column-map ds col scale [col])))

(defn scale-bars [ds height price-range]
  (-> ds
      (scale-col height price-range :open)
      (scale-col height price-range :high)
      (scale-col height price-range :low)
      (scale-col height price-range :close)))
