(ns demo.page.viewer.cheetah
  (:require
   ;[pinkgorilla.goog.string  :refer [format]]
   [rtable.viewer.cheetah :refer [cheetah cheetah-ds]]
   [dali.viewer :refer [viewer2]]
   [demo.helper.ui :refer [sample-selector]]))

(def data [{"make" "Toyota" "model" "Celica" "price" 35000 "cheap" true}
           {"make" "Ford" "model" "Mondeo"  "price" 32000 "cheap" true}
           {"make" "Porsche" "model" "Boxter"  "price" 72000 "cheap" false}])


(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [sample-selector
    {:small
     [cheetah {:style {:width "500px" :height "250px"}
               :columns [{:field "make" :caption "m" :width 50}
                         {:field "model" :caption "model" :width 50}
                         {:field "price" :caption "$$$" :width 50
                          :format 'demo.helper.format2/format-hidden}
                         {:field "cheap" :caption "cheap?" :width 50
                          :columnType "check" :action "check"}
                         ]
               :watch #(println "watch: " %)
               :data data}]

     :small-keywords
     [cheetah {:style {:width "500px" :height "250px"}
               :columns [{:field :make :caption "m" :width 50}
                         {:field :model :caption "model" :width 50}
                         {:field :price :caption "$$$" :width 50}]
               :data [{:make "Toyota" :model "Celica" :price 35000}
                      {:make "Ford" :model "Mondeo"  :price 32000}
                      {:make "Porsche" :model "Boxter"  :price 72000}]}]

     :dataset
     [viewer2
      {:viewer-fn 'rtable.viewer.cheetah/cheetah-ds
       :transform-fn 'rtable.transform.cheetah/load-and-transform-cheetah
       :data {:load {:url  "/r/bars-1m-full.transit-json"}
              :style {:width "100%" :height "100%"}
              :columns [; bar
                        {:field "asset" :caption "a" :width 90}
                        {:field "date" :caption "d" :width 220
                          ;:style 'demo.page.cheetah/bad-fn
                         }
                        {:field "open" :caption "o" :width 90
                         :style 'demo.helper.format2/red-color}
                        {:field "high" :caption "h" :width 90
                         :format 'demo.helper.format2/format-hidden}
                        {:field "low" :caption "l" :width 90
                             ;; this namespace does not work.
                         :format 'demo.helper.format2/format-number
                         :format-args ["Low: %.5f"]}
                        {:field "close" :caption "c" :width 90
                         :style 'demo.helper.format2/blue-color
                             ;:format format
                         :format-args "Cost: %.2f"}
                        {:field "volume" :caption "v" :width 90}
                         ;
                        {:field "atr-band-lower" :caption "BL" :width 50}
                        {:field "atr-band-upper" :caption "BU" :width 50}
                        {:field  "doji" :caption "doji" :width 50}

                        {:field  "below-band" :caption "B?" :width 50
                         :style {:bgColor "#5f5"}}
                        {:field  "cross-down" :caption "XD" :width 50}
                        {:field  "cross-down-c" :caption "XD_" :width 50}
                        {:field  "long-signal" :caption "LS" :width 50}

                        {:field  "above-band" :caption "A?" :width 50 :format 'demo.helper.format2/format-bool}
                        {:field  "cross-up" :caption "XU" :width 50 :format 'demo.helper.format2/format-bool}
                        {:field  "cross-up-c" :caption "XU_" :width 50
                         :style 'demo.helper.format2/bool-color
                         :format 'demo.helper.format2/format-bool2
                         :format-args [false]}
                        {:field  "short-signal" :caption "SS" :width 50
                         :format 'demo.helper.format2/format-bool2
                         :format-args [true]}
                        {:field "entry" :caption "entry" :width 50}]}}]}]])
