(ns demo.page.homemade
  (:require
   [rtable.rtable :refer [rtable]]
   [ipsum :refer [random-paragraph]]
   [demo.helper.menu :refer [wrap-menu]]
   [demo.table.columns2 :refer [columns2]]
   [demo.table.data2 :refer [table-data]]))

(def data
  (into [{:id 1
          :name "fischers fritz"}
         {:id 2
          :name "the one whose name cannot be said"
          :quote [:span
                  (random-paragraph 1)]
          :quote2 [:span
                   (random-paragraph 1)]}]
        (map (fn [i]
               {:id (str "#: " i)
                :name (str "John Doe " i)
                :quote (random-paragraph 1)
                :quote2 (random-paragraph 1)})
             (range 1000))))

(defn page-table [_]
  [:div.grid.grid-cols-2
   
   [rtable {:class "table-blue table-hover table-auto"
            :style {:width "50vw"
                    :height "40vh"
                    :border "3px solid green"}}
    [{:path :id}
     {:path :name
      :max-width "60px"}
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