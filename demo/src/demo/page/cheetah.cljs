(ns demo.page.cheetah
  (:require
   [reagent.core :as r]
   [promesa.core :as p]
   [rtable.cheetah :refer [cheetah]]
   [tech.v3.dataset :as tmlds]
   [cquant.tmlds :refer [GET]]
   ))

(def data [{"make" "Toyota" "model" "Celica" "price" 35000}
           {"make" "Ford" "model" "Mondeo"  "price" 32000}
           {"make" "Porsche" "model" "Boxter"  "price" 72000}])

(defonce clicked? (r/atom false))

(def ds-a (r/atom nil))


(-> (GET ;"/r/signal-no-date.transit-json"
         "/r/bars-1m-full.transit-json"
      )
    (p/then (fn [ds]
              (println "ds loaded: " (tmlds/column-names ds))
              (reset! ds-a ds)))
    (p/catch (fn [err]
               (println "could not load ds "  " err: " err)
               (reset! ds-a :load-error))))


(defn red-color [row]
  ;(println "red-color for: " row)
  (clj->js {:color "red"}))

(defn blue-color [row]
  ;(println "blue-color for: " row)
  (clj->js {:color "blue"}))

(defn bool-color [row]
  (let [row-clj (js->clj row)
        v (get row-clj "cross-up-c")
        color (if v "blue" "red")]
    ;(println row-clj)
    ;(println "bool color: " color " val: " v)
    (clj->js {:color color})))

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   ;; aggrid that has its data
   #_[cheetah {:style {:width "800px" :height "600px"}
             :columns [{:field "make" :caption "m" :width 50}
                       {:field "model" :caption "model" :width 50}
                       {:field "price" :caption "$$$" :width 50}]
             :data data}]
   
   #_[cheetah {:style {:width "800px" :height "600px"}
             :columns [{:field :make :caption "m" :width 50}
                       {:field :model :caption "model" :width 50}
                       {:field :price :caption "$$$" :width 50}]
             :data [{:make "Toyota" :model "Celica" :price 35000}
                    {:make "Ford" :model "Mondeo"  :price 32000}
                    {:make "Porsche" :model "Boxter"  :price 72000}]}]
  

   ; convert ds to array
   #_(if (or (nil? @ds-a) 
           (= :load-err @ds-a))
     [:p "NO DATA LOADED."]
      [cheetah {:style {:width "800px" :height "600px"}
                :columns [{:field "close":caption "m" :width 50}
                         {:field "spike" :caption "model" :width 50}
                         {:field  "doji" :caption "$$$" :width 50}]
                :data (->> (tmlds/rows @ds-a)
                           (into []))}])
   
   ; create accessor function.
   (if (or (nil? @ds-a)
           (= :load-err @ds-a))
     [:p "NO DATA LOADED."]
     [cheetah {:style {:width "100%" :height "100%"}
               :columns [; bar
                         {:field "asset" :caption "a" :width 90}
                         {:field "date" :caption "d" :width 220
                          ;:style 'demo.page.cheetah/bad-fn
                          }
                         {:field "open" :caption "o" :width 90
                          :style 'demo.page.cheetah/red-color
                          }
                         {:field "high" :caption "h" :width 90}
                         {:field "low" :caption "l" :width 90}
                         {:field "close" :caption "c" :width 90
                          :style blue-color
                          }
                         {:field "volume" :caption "v" :width 90}
                         ;
                         {:field "atr-band-lower" :caption "BL" :width 50}
                         {:field "atr-band-upper" :caption "BU" :width 50}
                         {:field  "doji" :caption "doji" :width 50}

                         {:field  "below-band" :caption "B?" :width 50
                          :style {:bgColor "#5f5" }}
                         {:field  "cross-down" :caption "XD" :width 50}
                         {:field  "cross-down-c" :caption "XD_" :width 50}
                         {:field  "long-signal" :caption "LS" :width 50}

                         {:field  "above-band" :caption "A?" :width 50}
                         {:field  "cross-up" :caption "XU" :width 50}
                         {:field  "cross-up-c" :caption "XU_" :width 50
                          :style demo.page.cheetah/bool-color
                          }
                         {:field  "short-signal" :caption "SS" :width 50}

                         {:field "entry" :caption "entry" :width 50}
]
               :ds @ds-a}])

  
   ])
