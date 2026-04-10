(ns demo.service.vega
  (:require
   [tablecloth.api :as tc]
   [rtable.plot.vega :as plot]))

(def spec
  {:description "A simple bar chart with embedded data."
   :mark {:type "bar"
          :tooltip {:content "data"}}
   :encoding {:x {:field "a" :type "ordinal"}
              :y {:field "b" :type "quantitative"}}
   :data {:name "table"
          :values [{:a "A" :b 28} {:a "B" :b 55} {:a "C" :b 43} {:a "D" :b 91}
                   {:a "E" :b 81} {:a "F" :b 53} {:a "G" :b 19} {:a "H" :b 87}
                   {:a "I" :b 52} {:a "J" :b 127}]}})

(defn vegalite1 []
  (plot/vegalite spec))

(def ds
  (tc/dataset {:a ["A" "B" "C" "D" "E" "F" "G" "H" "I" "J"]
               :b [28 55 43 91 81 53 19 87 52 127]}))

(comment
  ; in browser open tap viewer
  ; eval in repl this expressions with shortcut to wrap tap around them

  (vegalite1)

  (plot/vegalite  spec)

  ; a layered plot with entirely different datasources
  (plot/vegalite  {:description "A simple bar chart with embedded data."
                   :width 1000
                   :height 1000
                   :layer [{:data {:name "table1"},
                            :mark {:type "bar"
                                   :color "blue"
                                   :tooltip {:content "data"}}
                            :encoding {:x {:field "a" :type "ordinal"}
                                       :y {:field "b" :type "quantitative"}}}
                           {:data {:name "table2"},
                            :mark {:type "point"
                                   :color "red"
                                   :size 100
                                   :tooltip {:content "data"}}
                            :encoding {:x {:field "a" :type "ordinal"}
                                       :y {:field "b" :type "quantitative"}}}]
                   :data {:table1 [{:a "A" :b 28} {:a "B" :b 55} {:a "C" :b 43} {:a "D" :b 91}
                                   {:a "E" :b 81} {:a "F" :b 53} {:a "G" :b 19} {:a "H" :b 87}
                                   {:a "I" :b 52} {:a "J" :b 127}]
                          :table2 [{:a "A" :b 36} {:a "B" :b 68}]}})

  (plot/vegalite {:spec {:description "A simple bar chart with embedded data."
                         :width 1000
                         :height 1000
                         :data {:name "values"
                                :values [{:a "A" :b 28} {:a "B" :b 55} {:a "C" :b 43} {:a "D" :b 91}
                                         {:a "E" :b 81} {:a "F" :b 53} {:a "G" :b 19} {:a "H" :b 87}
                                         {:a "I" :b 52} {:a "J" :b 127}]}
                         :mark {:type "bar"
                                :color "blue"
                                :tooltip {:content "data"}}
                         :encoding {:x {:field "a" :type "ordinal"}
                                    :y {:field "b" :type "quantitative"}}}})

     ; vegalite plot from tml ds (data is sent to browser without using 
     ; transit for the dataset encoding)
  (plot/vegalite  {:embed/opts {; see:https://vega.github.io/vega-embed/interfaces/EmbedOptions.html
                                :width 850 ; int
                                :height 850
                                :theme  "quartz"}
                   :description "A simple bar chart with embedded data."
                   :data {:name "values"
                          :values (plot/convert-data ds [:a :b])}
                   :mark {:type "bar"
                          :color "blue"
                          :tooltip {:content "data"}}
                   :encoding {:x {:field "a" :type "ordinal"}
                              :y {:field "b" :type "quantitative"}}})
 ;
  )
(def spec-ds
  {;:$schema "https://vega.github.io/schema/vega-lite/v4.json"
   :description "A simple bar chart with embedded data."
   :mark {:type "bar"
          ;:tooltip true
          :tooltip {:content "data"}}
   :encoding {:x {:field "a" :type "ordinal"}
              :y {:field "b" :type "quantitative"}}})

(defn vegalite2 []
  (plot/vegalite-ds (assoc spec-ds
                           :cols [:a :b]) ds))

(def vega-spec
  {:$schema "https://vega.github.io/schema/vega/v5.json"
   :width 400
   :height 247.2187886279357
   :padding {:top 10, :left 55, :bottom 40, :right 10}
   :data [{:name "table"
           :values [{:x 0, :y 7}
                    {:x 1, :y 8}
                    {:x 2, :y 7}
                    {:x 3, :y 4}
                    {:x 4, :y 6}]}]
   :marks [{:type "symbol"
            :from {:data "table"}
            :encode {:enter {:x {:scale "x", :field "x"}
                             :y {:scale "y", :field "y"}
                             :fill {:value "steelblue"}
                             :fillOpacity {:value 1}}
                     :update {:shape "circle", :size {:value 70}, :stroke {:value "transparent"}}
                     :hover {:size {:value 210}, :stroke {:value "white"}}}}]
   :scales [{:name "x", :type "linear", :range "width", :zero false, :domain {:data "table", :field "x"}}
            {:name "y", :type "linear", :range "height", :nice true, :zero false, :domain {:data "table", :field "y"}}]
   :axes [{:orient "bottom", :scale "x"} {:orient "left", :scale "y"}]})

(defn vega1 []
  (plot/vega vega-spec))





