(ns rtable.viewer.aggrid
  (:require
   ["ag-grid-react" :as rs :refer [AgGridReact]]
   [frontend.css :refer [get-theme-component]]))

(defn aggrid-impl
  "displays a seq in a table, uses ag-grid"
  [opts]
  [:> AgGridReact opts])

(defn ag-theme-classname [theme]
  (if (= theme true)
    ""
    (str "ag-theme-" theme)))

(defn aggrid [{:keys [_style _theme] :as _opts}]
  (let [global-theme (get-theme-component :aggrid)]
    (fn [{:keys [style theme]
          :or {style {:width "100%" :height "100%"}}
          :as opts}]
      [:div {:className (if theme
                          (ag-theme-classname theme)
                          (ag-theme-classname global-theme))
             :style style}
       [aggrid-impl (dissoc opts :style :theme)]])))






