(ns demo.page.highcharts
  (:require
   [reagent.core :as r]
   [ui.highcharts :as highchart-old]
   [rtable.highcharts :refer [highstock]]
   [demo.highcharts.spec :refer [highchart-spec]]
   [demo.highcharts.spec-annotations :as annotations]
   [demo.helper.ui :refer [link-href link-dispatch]]))

(defn sample-selector [samples]
  (let [sample-a (r/atom nil)
        header (fn [s]
                 [:a {:on-click #(reset! sample-a s)}
                  [:p.pr-2 (str s)]])]
    (fn []
      [:div
        ; header
       (into [:div.flex.flex-row.bg-blue-300
              {:style {:width "10cm"}}]
             (map header (keys samples)))
        ; sample   
       (let [sample-ui (get samples @sample-a)]
         (if sample-ui
           sample-ui
           [:p "no sample selected"]))])))

(defn highchart-page  [{:keys [route-params query-params handler] :as route}]
  [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
   [:div
    [link-href "/" "main"]
    [:p "highchart with different sizes"]]
   [link-dispatch [:css/set-theme-component :highcharts true] "default"]
   [link-dispatch [:css/set-theme-component :highcharts "dark-unica"] "dark-unica"]
   [link-dispatch [:css/set-theme-component :highcharts "grid-light"] "grid-light"]
   [link-dispatch [:css/set-theme-component :highcharts "sand-signika"] "sand-signika"]

    ;; old version that is using pinkie-box
   [sample-selector
    {:sm [highchart-old/highchart {:data highchart-spec :box :sm}]
     :md [highchart-old/highchart {:data highchart-spec :box :md}]
     :lg [highchart-old/highchart {:data highchart-spec :box :lg}]
     :annotation [highchart-old/highchart {:data annotations/spec :box :lg}]}]

    ;; new version that provides :style and :class
   [sample-selector
    {:lg-new [highstock {:style {:width "1000px"
                                 :height "500px"}
                         :data highchart-spec}]
     :annotation-new [highstock {:style {:width "500px"
                                         :height "500px"}
                                 :class "bg-red-300"
                                 :data annotations/spec}]
     :annotation-new-big  [highstock {:style {:width "1000px"
                                              :height "500px"}
                                      :class "bg-red-500"
                                      :data annotations/spec}]}]])

(defn highchart-full-page  [{:keys [route-params query-params handler] :as route}]
  [:div
   ;[:div.text-green-300 "grid1"]
   [:div {:class "h-screen w-screen"} ; .grid.grid-cols-2
    [highchart-old/highchart {:data highchart-spec :box :fl}]]])
