(ns demo.table.columns2
  (:require
   [reagent.core :as r]))

(defn age-color [age]
  (cond
    (< age 18) "text-red-700"
    (< age 65) "text-green-700"
    (>= age 65) "text-blue-700"
    :else "text-blue-700"))

(defn icon [data]
  [:i.fas.fa-check (str data)])

;the column model
(def columns2 [{:path :id
                ;:width 50
                }
               {:path :first-name
                ;:width 40
                }
               {:path :last-name
                :format icon
                :header "LastName"
                ;:width 40
                }
               {:path :age
                :attrs (fn [age] {:class (age-color age)
                                  :style {:text-align "right"
                                          :display "block"
                                          :width "2cm"
                                          :max-width "2cm"
                                          :overflow "hidden"
                                          :text-overflow "ellipsis"
                                          :white-space "nowrap"}})
                ;:width 40
                }
               {:path :sex
                :format (fn [sex]
                          (if (= sex :woman)
                            [:i.fa.fa-user-nurse " Woman"]
                            [:i.fa.fa-user " Man"]))
                ;:width 40
                }
               {:path :desc
                :max-width "140px"}

               {:path :x}
               {:path :y}
               {:path :z}
               {:path :a}
               {:path :b}
               {:path :c}
               {:path :1}
               {:path :2}
               {:path :3}])