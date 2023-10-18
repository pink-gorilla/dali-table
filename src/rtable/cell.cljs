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
 - el-type     the type of the element, default :span 
 - render-info the specific column from :column-model
 - format      a function that formats the value"
  [render-info row]
  (let [{:keys [el-type format attrs]
         :or   {el-type :span
                attrs (fn [_] {})
                format identity}} render-info
        data    (cell-data row render-info)
        attrs   (attrs data)
        content (format data)]
    [el-type
     attrs
     content]))