(ns rtable.cell)

(defn get-path-or-key [row path]
  (if (vector? path)
    (get-in row path)
    (get row path)))

(defn cell-data
  "Resolve the data within a row for a specific column"
  [row cell]
  (let [{:keys [path expr]} cell]
    (cond
      path
      (get-path-or-key row path)
      expr
      (expr row)
      :else
      nil)))

(defn format-boolean [b]
  (let [s (case b
            true "✓" ; "true"
            false "✕" ; "false"
            "")]
    s))

(defn default-format-fn [data]
  ; problem in formatting is that boolean values dont show up.
  (cond
    (boolean? data)
    (format-boolean data)

    ;(vector? data)
    ;(str data)
    :else
    data))

(defn cell-fn
  "Return the cell hiccup form for rendering.
 - el-type     the type of the element, default :span 
 - render-info the specific column from :column-model
 - format      a function that formats the value"
  [{:keys [el-type format attrs]
    :or   {el-type :span
           attrs (fn [_] {})
           format default-format-fn}
    :as render-info}
   row]
  (let [data    (cell-data row render-info)
        attrs   (attrs data)
        content (format data)]
    [el-type
     attrs
     content]))