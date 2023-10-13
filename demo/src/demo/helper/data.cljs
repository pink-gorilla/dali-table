(ns demo.helper.data
  (:require
    [reagent.core :as r]))


(def table-data (r/atom
                 [{:Animal {:Name    "Lizard"
                            :Colour  "Green"
                            :Skin    "Leathery"
                            :Weight  100
                            :Age     10
                            :Hostile false}}
                  {:Animal {:Name    "Lion"
                            :Colour  "Gold"
                            :Skin    "Furry"
                            :Weight  190000
                            :Age     4
                            :Hostile true}}
                  {:Animal {:Name    "Giraffe"
                            :Colour  "Green"
                            :Skin    "Hairy"
                            :Weight  1200000
                            :Age     8
                            :Hostile false}}
                  {:Animal {:Name    "Cat"
                            :Colour  "Black"
                            :Skin    "Furry"
                            :Weight  5500
                            :Age     6
                            :Hostile false}}
                  {:Animal {:Name    "Capybara"
                            :Colour  "Brown"
                            :Skin    "Hairy"
                            :Weight  45000
                            :Age     12
                            :Hostile false}}
                  {:Animal {:Name    "Bear"
                            :Colour  "Brown"
                            :Skin    "Furry"
                            :Weight  600000
                            :Age     10
                            :Hostile true}}
                  {:Animal {:Name    "Rabbit"
                            :Colour  "White"
                            :Skin    "Furry"
                            :Weight  1000
                            :Age     6
                            :Hostile false}}
                  {:Animal {:Name    "Fish"
                            :Colour  "Gold"
                            :Skin    "Scaly"
                            :Weight  50
                            :Age     5
                            :Hostile false}}
                  {:Animal {:Name    "Hippo"
                            :Colour  "Grey"
                            :Skin    "Leathery"
                            :Weight  1800000
                            :Age     10
                            :Hostile false}}
                  {:Animal {:Name    "Zebra"
                            :Colour  "Black/White"
                            :Skin    "Hairy"
                            :Weight  200000
                            :Age     9
                            :Hostile false}}
                  {:Animal {:Name    "Squirrel"
                            :Colour  "Grey"
                            :Skin    "Furry"
                            :Weight  300
                            :Age     1
                            :Hostile false}}
                  {:Animal {:Name    "Crocodile"
                            :Colour  "Green"
                            :Skin    "Leathery"
                            :Weight  500000
                            :Age     10
                            :Hostile true}}]))
