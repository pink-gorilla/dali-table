(ns demo.page.main
  (:require
   [reagent-table.core :refer [reagent-table]]
   [demo.helper.ui :refer [link-dispatch link-href link-fn block2]]
   [demo.helper.data :refer [table-data]] ; side-effects
   ))

;the column model
(def columns [{:path [:Animal :Name]
               :header "Name"
               :key :Name}  ; convention - use field name for reagent key
              {:path [:Animal :Colour]
               :header "Colour"
               :key :Colour}
              {:path [:Animal :Skin]
               :header "Skin Type"
               :key :Skin}
              {:path [:Animal :Weight]
               :header "Weight"
               ;:format #(format-number %)
               :attrs (fn [data] {:style {:text-align "right"
                                          :display "block"}})
               :key :Weight}
              {:path [:Animal :Age]
               :header "Age"
               :attrs (fn [data] {:style {:text-align "right"
                                          :display "block"}})
               :key :Age}
              {:path [:Animal :Hostile]
               :header "Hostile?"
               :format #(if % "Stay Away!" "OK to stroke")
               :key :Hostile}])

(defn- row-key-fn
  "Return the reagent row key for the given row"
  [row row-num]
  (get-in row [:Animal :Name]))

(defn- cell-data
  "Resolve the data within a row for a specific column"
  [row cell]
  (let [{:keys [path expr]} cell]
    (or (and path
             (get-in row path))
        (and expr
             (expr row)))))

(defn- cell-fn
  "Return the cell hiccup form for rendering.
 - render-info the specific column from :column-model
 - row the current row
 - row-num the row number
 - col-num the column number in model coordinates"
  [render-info row row-num col-num]
  (let [{:keys [format attrs]
         :or   {format identity
                attrs (fn [_] {})}} render-info
        data    (cell-data row render-info)
        content (format data)
        attrs   (attrs data)]
    [:span
     attrs
     content]))

(defn date?
  "Returns true if the argument is a date, false otherwise."
  [d]
  (instance? js/Date d))

(defn date-as-sortable
  "Returns something that can be used to order dates."
  [d]
  (.getTime d))

(defn compare-vals
  "A comparator that works for the various types found in table structures.
  This is a limited implementation that expects the arguments to be of
  the same type. The :else case is to call compare, which will throw
  if the arguments are not comparable to each other or give undefined
  results otherwise.

  Both arguments can be a vector, in which case they must be of equal
  length and each element is compared in turn."
  [x y]
  (cond
    (and (vector? x)
         (vector? y)
         (= (count x) (count y)))
    (reduce #(let [r (compare (first %2) (second %2))]
               (if (not= r 0)
                 (reduced r)
                 r))
            0
            (map vector x y))

    (or (and (number? x) (number? y))
        (and (string? x) (string? y))
        (and (boolean? x) (boolean? y)))
    (compare x y)

    (and (date? x) (date? y))
    (compare (date-as-sortable x) (date-as-sortable y))

    :else ;; hope for the best... are there any other possiblities?
    (compare x y)))

(defn- sort-fn
  "Generic sort function for tabular data. Sort rows using data resolved from
  the specified columns in the column model."
  [rows column-model sorting]
  (sort (fn [row-x row-y]
          (reduce
           (fn [_ sort]
             (let [column (column-model (first sort))
                   direction (second sort)
                   cell-x (cell-data row-x column)
                   cell-y (cell-data row-y column)
                   compared (if (= direction :asc)
                              (compare-vals cell-x cell-y)
                              (compare-vals cell-y cell-x))]
               (when-not (zero? compared)
                 (reduced compared))))
           0
           sorting))
        rows))

(def table-state (atom {:draggable true}))

(defn table []
 [:div.container {:style {:font-size 16 :margin-top 10} :height "100%"}
  ;[:div.panel.panel-default
   ;[:div.panel-body
  [reagent-table table-data {:table {:class "table table-hover table-striped table-bordered table-transition"
                                     :style {:border-spacing 0
                                             :border-collapse "separate"}}
                                :table-container {:style {:border "1px solid green"}}
                                :th {:style {:border "1px solid white" :background-color "black"}}
                                :table-state  table-state
                                :scroll-height "80vh"
                                :column-model columns
                                :row-key      row-key-fn
                                :render-cell  cell-fn
                                :sort         sort-fn
                                  ;:caption [:caption "Test caption"]
                                :column-selection {:ul
                                                   {:li {:class "btn"}}}}]])

(defn page-main [_]
  [:div.w-full.h-full
   [table]])