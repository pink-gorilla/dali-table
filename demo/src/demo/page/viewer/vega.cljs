(ns demo.page.viewer.vega
  (:require
   [rtable.viewer.vega :refer [vega vegalite]]))

(defn page  [_]
  [:div {:style {:width "100vw" :height "100vh"
                 :display "grid"
                 :grid-template-columns "1fr 1fr"
                 :grid-template-rows "1fr 10fr"
                 :grid-auto-flow "column"}}

   [:h1 {:style {:text-align "center"
                 :font-size "24px"
                 :font-weight "bold"}} "vega lite"]
   [vegalite {:embed/opts {; see:https://vega.github.io/vega-embed/interfaces/EmbedOptions.html
                           :width 350 ; int
                           :height 350
                           :theme  "quartz"}
              :data {:values [{:x 3 :y 4}
                              {:x 7 :y 1}
                              {:x 5 :y 7}
                              {:x 2 :y 6}]}
              :mark :point
              :encoding {:x {:field :x :type :quantitative}
                         :y {:field :y :type :quantitative}}}]

   [:h1 {:style {:text-align "center"
                 :font-size "24px"
                 :font-weight "bold"}} "vega"]
   [vega  {:embed/opts {;:mode "vega"
                        :theme "quartz"}
           :$schema "https://vega.github.io/schema/vega/v5.json"
           :width 400
           :height 247.2187886279357
           :padding {:top 10, :left 55, :bottom 40, :right 10}
           :data [{:name "50bbe3dd-798b-434d-b1b2-6095fee74a3d", :values [{:x 0, :y 7}
                                                                          {:x 1, :y 8}
                                                                          {:x 2, :y 7} {:x 3, :y 4} {:x 4, :y 6}]}]
           :marks [{:type "symbol"
                    :from {:data "50bbe3dd-798b-434d-b1b2-6095fee74a3d"}
                    :encode {:enter {:x {:scale "x", :field "x"}
                                     :y {:scale "y", :field "y"}
                                     :fill {:value "steelblue"}
                                     :fillOpacity {:value 1}}
                             :update {:shape "circle", :size {:value 70}, :stroke {:value "transparent"}}
                             :hover {:size {:value 210}, :stroke {:value "white"}}}}]
           :scales [{:name "x", :type "linear", :range "width", :zero false, :domain {:data "50bbe3dd-798b-434d-b1b2-6095fee74a3d", :field "x"}}
                    {:name "y", :type "linear", :range "height", :nice true, :zero false, :domain {:data "50bbe3dd-798b-434d-b1b2-6095fee74a3d", :field "y"}}]
           :axes [{:orient "bottom", :scale "x"}
                  {:orient "left", :scale "y"}]}]])
