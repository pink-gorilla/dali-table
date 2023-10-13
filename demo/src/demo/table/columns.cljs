(ns demo.table.columns
  (:require
   [reagent.core :as r]))

;the column model
(def columns [{:path [:Animal :Name]
               :header "Name"
               :key :Name}  ; convention - use field name for reagent key
              {:path [:Animal :Colour]
               :header "Colour"
               :key :Colour}
              {:path [:Animal :Skin]
               :header "Skin Type"
               :key :Skin}
              {:path [:Animal :Weight]
               :header "Weight"
               ;:format #(format-number %)
               :attrs (fn [data] {:style {:text-align "right"
                                          :display "block"}})
               :key :Weight}
              {:path [:Animal :Age]
               :header "Age"
               :attrs (fn [data] {:style {:text-align "right"
                                          :display "block"}})
               :key :Age}
              {:path [:Animal :Hostile]
               :header "Hostile?"
               :format #(if % "Stay Away!" "OK to stroke")
               :key :Hostile}])