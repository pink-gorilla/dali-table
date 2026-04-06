(ns notebook.dali.vega
   (:require
   [rtable.plot.vega :refer [vega vegalite]])
  )

(vega {:$schema "https://vega.github.io/schema/vega/v5.json"
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
       :axes [{:orient "bottom", :scale "x"} {:orient "left", :scale "y"}]})

(vega {:$schema "https://vega.github.io/schema/vega/v5.json", :width 400, :height 247.2187886279357, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name "6348d171-8e7b-4a02-8b9a-bea64dbcbdce", :values [{:x 0, :y 7} {:x 1, :y 8} {:x 2, :y 7} {:x 3, :y 4} {:x 4, :y 6}]}], :marks [{:type "symbol", :from {:data "6348d171-8e7b-4a02-8b9a-bea64dbcbdce"}, :encode {:enter {:x {:scale "x", :field "x"}, :y {:scale "y", :field "y"}, :fill {:value "steelblue"}, :fillOpacity {:value 1}}, :update {:shape "circle", :size {:value 70}, :stroke {:value "transparent"}}, :hover {:size {:value 210}, :stroke {:value "white"}}}}], :scales [{:name "x", :type "linear", :range "width", :zero false, :domain {:data "6348d171-8e7b-4a02-8b9a-bea64dbcbdce", :field "x"}} {:name "y", :type "linear", :range "height", :nice true, :zero false, :domain {:data "6348d171-8e7b-4a02-8b9a-bea64dbcbdce", :field "y"}}], :axes [{:orient "bottom", :scale "x"} {:orient "left", :scale "y"}]})

(vega {:$schema "https://vega.github.io/schema/vega/v5.json", :width 400, :height 247.2187886279357, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name "9612b327-d447-478a-84a6-27af98f319f0", :values [{:x 0, :y 7} {:x 1, :y 8} {:x 2, :y 7} {:x 3, :y 4} {:x 4, :y 6}]}], :marks [{:type "symbol", :from {:data "9612b327-d447-478a-84a6-27af98f319f0"}, :encode {:enter {:x {:scale "x", :field "x"}, :y {:scale "y", :field "y"}, :fill {:value "steelblue"}, :fillOpacity {:value 1}}, :update {:shape "circle", :size {:value 70}, :stroke {:value "transparent"}}, :hover {:size {:value 210}, :stroke {:value "white"}}}}], :scales [{:name "x", :type "linear", :range "width", :zero false, :domain {:data "9612b327-d447-478a-84a6-27af98f319f0", :field "x"}} {:name "y", :type "linear", :range "height", :nice true, :zero false, :domain {:data "9612b327-d447-478a-84a6-27af98f319f0", :field "y"}}], :axes [{:orient "bottom", :scale "x"} {:orient "left", :scale "y"}]})


;; rect plot
(vega
 {:spec {:$schema "https://vega.github.io/schema/vega/v5.json"
         :padding {:left 5, :right 5, :top 5, :bottom 5}
         :data [{:name "table"}]
         :signals [{:name "tooltip"
                    :value {}
                    :on [{:events "rect:mouseover", :update "datum"}
                         {:events "rect:mouseout", :update "{}"}]}]
         :scales [{:name "xscale"
                   :type "band"
                   :domain {:data "table", :field "category"}
                   :range "width"}
                  {:name "yscale"
                   :domain {:data "table", :field "amount"}
                   :nice true
                   :range "height"}]
         :axes [{:orient "bottom", :scale "xscale"}
                {:orient "left", :scale "yscale"}]
         :marks [{:type "rect"
                  :from {:data "table"}
                  :encode {:enter {:x {:scale "xscale", :field "category", :offset 1}
                                   :width {:scale "xscale", :band 1, :offset -1}
                                   :y {:scale "yscale", :field "amount"}
                                   :y2 {:scale "yscale", :value 0}}
                           :update {:fill {:value "steelblue"}}
                           :hover {:fill {:value "red"}}}}
                 {:type "text"
                  :encode {:enter {:align {:value "center"}
                                   :baseline {:value "bottom"}
                                   :fill {:value "#333"}}
                           :update {:x {:scale "xscale", :signal "tooltip.category", :band 0.5}
                                    :y {:scale "yscale", :signal "tooltip.amount", :offset -2}
                                    :text {:signal "tooltip.amount"}
                                    :fillOpacity [{:test "datum === tooltip", :value 0}, {:value 1}]}}}]}
  :data {:table [{:category "A", :amount 28}
                 {:category "B", :amount 55}
                 {:category "C", :amount 43}
                 {:category "D", :amount 91}
                 {:category "E", :amount 81}
                 {:category "F", :amount 53}
                 {:category "G", :amount 19}
                 {:category "H", :amount 87}]}
  :width 400
  :height 400})


;; zoom 
(vega
 {:spec {:description
         "An interactive scatter plot example supporting pan and zoom."
         :autosize "none"
         :config
         {:axis
          {:domain false
           :tickSize 3
           :tickColor "#888"
           :labelFont "Monaco, Courier New"}}
         :axes
         [{:scale "xscale", :orient "top", :offset {:signal "xoffset"}}
          {:scale "yscale", :orient "right", :offset {:signal "yoffset"}}]
         :width 500
         :scales
         [{:name "xscale"
           :zero false
           :domain {:signal "xdom"}
           :range {:signal "xrange"}}
          {:name "yscale"
           :zero false
           :domain {:signal "ydom"}
           :range {:signal "yrange"}}]
         :padding {:top 10, :left 40, :bottom 20, :right 10}
         :marks
         [{:type "symbol"
           :from {:data "points"}
           :clip true
           :encode
           {:enter {:fillOpacity {:value 0.6}, :fill {:value "steelblue"}}
            :update
            {:x {:scale "xscale", :field "u"}
             :y {:scale "yscale", :field "v"}
             :size {:signal "size"}}
            :hover {:fill {:value "firebrick"}}
            :leave {:fill {:value "steelblue"}}
            :select {:size {:signal "size", :mult 5}}
            :release {:size {:signal "size"}}}}]
         :$schema "https://vega.github.io/schema/vega/v5.json"
         :signals
         [{:name "margin", :value 20}
          {:name "hover"
           :on
           [{:events "*:mouseover", :encode "hover"}
            {:events "*:mouseout", :encode "leave"}
            {:events "*:mousedown", :encode "select"}
            {:events "*:mouseup", :encode "release"}]}
          {:name "xoffset", :update "-(height + padding.bottom)"}
          {:name "yoffset", :update "-(width + padding.left)"}
          {:name "xrange", :update "[0, width]"}
          {:name "yrange", :update "[height, 0]"}
          {:name "down"
           :value nil
           :on
           [{:events "touchend", :update "null"}
            {:events "mousedown, touchstart", :update "xy()"}]}
          {:name "xcur"
           :value nil
           :on
           [{:events "mousedown, touchstart, touchend"
             :update "slice(xdom)"}]}
          {:name "ycur"
           :value nil
           :on
           [{:events "mousedown, touchstart, touchend"
             :update "slice(ydom)"}]}
          {:name "delta"
           :value [0 0]
           :on
           [{:events
             [{:source "window"
               :type "mousemove"
               :consume true
               :between
               [{:type "mousedown"} {:source "window", :type "mouseup"}]}
              {:type "touchmove"
               :consume true
               :filter "event.touches.length === 1"}]
             :update "down ? [down[0]-x(), y()-down[1]] : [0,0]"}]}
          {:name "anchor"
           :value [0 0]
           :on
           [{:events "wheel"
             :update "[invert('xscale', x()), invert('yscale', y())]"}
            {:events {:type "touchstart", :filter "event.touches.length===2"}
             :update "[(xdom[0] + xdom[1]) / 2, (ydom[0] + ydom[1]) / 2]"}]}
          {:name "zoom"
           :value 1
           :on
           [{:events "wheel!"
             :force true
             :update "pow(1.001, event.deltaY * pow(16, event.deltaMode))"}
            {:events {:signal "dist2"}, :force true, :update "dist1 / dist2"}]}
          {:name "dist1"
           :value 0
           :on
           [{:events {:type "touchstart", :filter "event.touches.length===2"}
             :update "pinchDistance(event)"}
            {:events {:signal "dist2"}, :update "dist2"}]}
          {:name "dist2"
           :value 0
           :on
           [{:events
             {:type "touchmove"
              :consume true
              :filter "event.touches.length===2"}
             :update "pinchDistance(event)"}]}
          {:name "xdom"
           :update "slice(xext)"
           :on
           [{:events {:signal "delta"}
             :update
             "[xcur[0] + span(xcur) * delta[0] / width, xcur[1] + span(xcur) * delta[0] / width]"}
            {:events {:signal "zoom"}
             :update
             "[anchor[0] + (xdom[0] - anchor[0]) * zoom, anchor[0] + (xdom[1] - anchor[0]) * zoom]"}]}
          {:name "ydom"
           :update "slice(yext)"
           :on
           [{:events {:signal "delta"}
             :update
             "[ycur[0] + span(ycur) * delta[1] / height, ycur[1] + span(ycur) * delta[1] / height]"}
            {:events {:signal "zoom"}
             :update
             "[anchor[1] + (ydom[0] - anchor[1]) * zoom, anchor[1] + (ydom[1] - anchor[1]) * zoom]"}]}
          {:name "size", :update "clamp(20 / span(xdom), 1, 1000)"}]
         :height 300
         :data
         [{:name "points"
           :url "/r/data/normal-2d.json"
           :transform
           [{:type "extent", :field "u", :signal "xext"}
            {:type "extent", :field "v", :signal "yext"}]}]}})