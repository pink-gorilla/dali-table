(ns demo.page.viewer.htmltable
  (:require
   [ui.site.ipsum :refer [random-paragraph]]
   [demo.helper.ui :refer [sample-selector]]))

(defn table-static [container-class style]
  [:div.x-auto {:style style
                :class container-class}
   [:table.r-table
    [:thead]
    (into [:tbody
           [:tr
            [:th "col1"]
            [:th {:style {:max-width "80px" :overflow "hidden" :text-overflow "ellipsis" :white-space "nowrap"}} "name"]
            [:th "col3"]]

           [:tr
            [:td "1"]
            [:td {:style {:max-width "80px"  :overflow "hidden" :text-overflow "ellipsis" :white-space "nowrap"}} "fischers fritz"]
            [:td "2"]]
           [:tr
            [:td "3"]
            [:td {:style {:max-width "80px"  :overflow "hidden" :text-overflow "ellipsis" :white-space "nowrap"}} "the one whose name cannot be said"]
            [:td [:span
                  (random-paragraph 1)]]]]
          (map (fn [i]
                 [:tr
                  [:td (str "#: " i)]
                  [:td {:style {:max-width "80px"  :overflow "hidden" :text-overflow "ellipsis" :white-space "nowrap"}} "John Doe " i]
                  [:td (random-paragraph 1)]])
               (range 1000)))]])

(defn table-static-header [demo-name container-class style]
  [:div
   [:h1 demo-name]
   [table-static container-class style]])

(defn page [_]
  [:div.h-screen.w-screen.bg-blue-100
   [sample-selector
    {:small
     [table-static-header "scroll-x-y"
      "table-blue table-hover"
      {:width "50vw"
       :height "40vh"
       :border "3px solid green"}]
     :2
     [table-static-header "header-fixed"
      "table-head-fixed padding-sm table-red table-striped table-hover"
      {:width "50vw"
       :height "40vh"
       :border "3px solid green"}]
     :3
     [table-static-header "header-fixed-blue"
      "table-head-fixed padding-sm table-blue table-striped table-hover"
      {:width "50vw"
       :height "40vh"
       :border "3px solid green"}]}]])

(defn page-full [_]
  [table-static
   "table-head-fixed padding-sm table-blue table-striped table-hover table-full"
   {:border "3px solid red"}])


