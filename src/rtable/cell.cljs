(ns rtable.cell)

(defn get-path-or-key [row path]
  (if (vector? path)
    (get-in row path)
    (get row path)))

(defn cell-data
  "Resolve the data within a row for a specific column"
  [row cell]
  (let [{:keys [path expr]} cell]
    (or (and path
             (get-path-or-key row path))
        (and expr
             (expr row)))))

(defn cell-fn
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
     content]
    ;content
    ))