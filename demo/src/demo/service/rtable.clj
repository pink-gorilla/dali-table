(ns demo.service.rtable
  (:require
   [rtable.plot.rtable :refer [rtable]]))

(defn random-data []
  (let [i (rand-int 10)]
    (case i
      0 "Peace to the world"
      1 "The hottentotten are here"
      2 "The sky is always blue"
      3 "Biene Maya and Willy"
      4 "Quot erum demunstrandum"
      5 "The early bird catches the worm"
      6 "Six Six Six"
      7 "Seven Angles are here"
      8 "8 means infinity "
      9 "what is the secret of nine?")))


(def data
  (into [{:id 1005
          :name "fischers fritz"
          :superpower false}
         {:id 1007
          :name "the one whose name cannot be said"
          :superpower true
          :quote [:span
                  (random-data)]
          :quote2 [:span
                   (random-data)]}]
        (map (fn [i]
               {:id i
                :name (str "John Doe " i)
                :superpower (cond
                              (< i 5) false
                              (< i 10) true
                              :else nil)
                :quote (random-data)
                :quote2 (random-data)})
             (range 1000))))


(def opts 
  {:class "table-blue table-hover table-auto"
   :style {:width "100%"
           :height "100%"
           :border "3px solid green"}
   :columns  [{:path :id
              :format 'demo.helper.homemade/format-hash} ;; format
             {:path :id
              :header "link"
              :render-cell 'demo.helper.homemade/goto-person} ;; render-cell
             {:path :name
              :max-width "60px"
              :attrs 'demo.helper.homemade/attrs-red} ;; attrs
             {:path :superpower 
              :format 'demo.helper.homemade/format-boolean}
             {:path :quote
              :max-width "150px"}
             {:path :quote2
              :max-width "100px"}]})


(defn rtable-data []
    (rtable opts data))

