(ns demo.helper.daliclj 
  (:require 
    [ui.flexlayout :refer [component-ui]]   
    [dali.cljviewer :refer [clj-viewer]]))

(defmethod component-ui "clj" [{:keys [id state]}]
  (fn [options]
    (let [{:keys [fun args]} options]
      (if fun 
        [clj-viewer {:fun fun :args args}]
        [:div "error: option does not contain :fun key"]))))