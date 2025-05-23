(ns demo.helper.ui
  (:require
   [demo.link :refer [create-link]]
   [re-frame.core :as rf]
   [reagent.core :as r]))

(defn link-fn [fun text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:on-click fun} text])

(defn link-dispatch [rf-evt text]
  (link-fn #(rf/dispatch rf-evt) text))

(defn link-href [href text]
  [:a.bg-blue-300.cursor-pointer.hover:bg-red-700.m-1
   {:href href} text])

(defn block2 [name & children]
  (->  [:div.bg-blue-400.inline-block.w-full ; md:w-full
        {:class "lg:p-2"}
        [:p.text-4xl.pb-2 name]]
       (into children)))

(defn sample-selector [samples]
  (let [sample-a (r/atom nil)
        header (fn [s]
                 [:a {:class "hover:bg-blue-400 border rounded cursor-pointer"
                      :on-click #(reset! sample-a s)}
                  [:p.pr-2 (str s)]])]
    (fn []
      [:div
       {:style {:display "grid"
                :height "100%"
                :width "100%"
                :grid-template-rows "3rem 1fr"}}
        ; header
       (into [:div.flex.flex-row.bg-blue-300.w-full.h-full
              [:a {:href (create-link 'demo.page.core/page)}
               [:div {:class "hover:bg-blue-400 border rounded cursor-pointer"}
                [:p.pr-2 "main"]]]]
             (map header (keys samples)))
        ; sample   
       (let [sample-ui (get samples @sample-a)]
         (if sample-ui
           sample-ui
           [:p "no sample selected"]))])))