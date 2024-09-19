(ns demo.page.homemade
  (:require
   [rtable.rtable :refer [rtable]]
   [rtable.cell :refer [format-boolean]]
   [ui.site.ipsum :refer [random-paragraph]]
   [demo.helper.menu :refer [wrap-menu]]
   [demo.table.columns2 :refer [columns2]]
   [demo.table.data2 :refer [table-data]]))

(def data
  (into [{:id 1005
          :name "fischers fritz"
          :superpower false}
         {:id 1007
          :name "the one whose name cannot be said"
          :superpower true
          :quote [:span
                  (random-paragraph 1)]
          :quote2 [:span
                   (random-paragraph 1)]}]
        (map (fn [i]
               {:id i
                :name (str "John Doe " i)
                :superpower (cond
                              (< i 5) false
                              (< i 10) true
                              :else nil)
                :quote (random-paragraph 1)
                :quote2 (random-paragraph 1)})
             (range 1000))))

(defn page-table [_]
  [:div.grid.grid-cols-2

   [rtable {:class "table-blue table-hover table-auto"
            :style {:width "50vw"
                    :height "40vh"
                    :border "3px solid green"}}
    [{:path :id
      :format (fn [v] (str "#" v))} ;; format
     {:path :id
      :header "link"
      :render-cell (fn [col-info row]
                     [:a {:href (str "/person/id/" (:id row))}
                      [:span "goto-person"]])} ;; render-cell
     {:path :name
      :max-width "60px"
      :attrs (fn [v] {:class "bg-red-300"})} ;; attrs
     {:path :superpower :format format-boolean}
     {:path :quote
      :max-width "150px"}
     {:path :quote2
      :max-width "100px"}]
    data]

   [rtable {:class "table-head-fixed padding-sm table-red table-striped table-hover"
            :style {:width "50vw"
                    :height "40vh"
                    :border "3px solid green"}}
    [{:path :id}
     {:path :name :max-width "60px"}
     {:path :quote}
     {:path :quote2}]
    data]

   [rtable {:class "table-head-fixed padding-sm table-blue table-striped table-hover"
            :style {:width "50vw"
                    :height "40vh"
                    :border "3px solid green"}}
    [{:path :id}
     {:path :name
      :max-width "60px"}
     {:path :quote
      :max-width "150px"}
     {:path :quote2}]
    data]

   [rtable {:class "table-head-fixed padding-sm table-blue table-striped table-hover"
            :style {:width "50vw"
                    :height "40vh"
                    :border "3px solid green"}}
    columns2
    table-data]])

(def page-table-menu (wrap-menu page-table))