(ns rtable.viewer.rtable
  (:require
   [rtable.rtable]))

(defn rtable [{:keys [style class
                      columns
                      rows]
               :or {style {}
                    class ""}
               :as opts}]
  ; rtable needs 3 parameters: opts, cols, data
  ; our spec format only uses two parameters; we moved the 
  ; columns definition to a :columns key in opts
  (if (empty? rows)
    [:div.h-full.w-full.p-10 "No Rows in this table. "]
    [rtable.rtable/rtable (dissoc opts :columns :rows) columns rows]))