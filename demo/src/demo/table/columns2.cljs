(ns demo.table.columns2
  (:require
   [reagent.core :as r]))

(defn age-color [age]
  (println "age: " age)
  (cond
    (< age 18) "text-red-700"
    (< age 65) "text-green-700"
    (>= age 65) "text-blue-700"
    :else "text-blue-700"))

(defn icon [data]
  [:i.fas.fa-check (str data)])

;the column model
(def columns2 [{:path :id}
               {:path :first-name}
               {:path :last-name
                :format icon}
               {:path :age
                :attrs (fn [age] {:class (age-color age)
                                  :style {:text-align "right"
                                          :display "block"}})}
               {:path :sex
                :format (fn [sex]
                          (if (= sex :woman)
                            [:i.fa.fa-user-nurse " Woman"]
                            [:i.fa.fa-user " Man"]))}])