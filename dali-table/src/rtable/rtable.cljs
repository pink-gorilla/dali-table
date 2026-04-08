(ns rtable.rtable
  (:require
   [rtable.cell :refer [cell-fn]]))

;; helper fn

(defn assoc-if [m key d]
  (if d
    (assoc m key d)
    m))

(defn max-width-style [max-width]
  {:max-width max-width :overflow "hidden" :text-overflow "ellipsis" :white-space "nowrap"})

;; header

(defn col-header [{:keys [header path] :as col}]
  (if header
    header
    (str path)))

(defn col-header-opts [{:keys [max-width] :as col}]
  (let [max-width (when max-width (max-width-style max-width))]
    (-> {}
        (assoc-if :style max-width))))

(defn col-header-th [col]
  [:th (col-header-opts col)
   (col-header col)])

(defn row-header [cols]
  (into [:tr]
        (map col-header-th cols)))

;; cell

(defn cell-opts [{:keys [class style max-width] :as col}]
  (let [max-width (when max-width (max-width-style max-width))]
    (-> {}
        (assoc-if :class class)
        (assoc-if :style max-width)
        (assoc-if :style style))))

(defn cell-td [table-render-cell {:keys [render-cell]
                                  :or {render-cell table-render-cell}
                                  :as col} row]
  [:td (cell-opts col)
   (render-cell col row)])

;; row

(defn table-row [render-cell cols row]
  (into [:tr]
        (map #(cell-td render-cell % row) cols)))

(defn table-rows [render-cell cols rows]
  (map #(table-row render-cell cols %) rows))

;; table

(defn rtable [{:keys [class style render-cell]
               :or {render-cell cell-fn}
               :as config}
              cols data]
  [:div.x-auto {:style style
                :class class}
   [:table.r-table
    [:thead]
    (into [:tbody
           (row-header cols)]
          (table-rows render-cell cols data))]])
