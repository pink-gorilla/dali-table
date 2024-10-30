(ns demo.helper.daliclj 
  (:require 
    [ui.flexlayout :refer [component-ui]]  
    [dali.viewer :refer [viewer2]]
    [dali.cljviewer :refer [clj-viewer]]))

(defmethod component-ui "viewer" [{:keys [id state]}]
  (fn [options]
      (if options
        [viewer2 options]
        [:div "error: option does not exist"])))


(defmethod component-ui "clj" [{:keys [id state]}]
  (fn [options]
    (let [{:keys [fun args]} options]
      (if fun 
        [clj-viewer {:fun fun :args args}]
        [:div "error: option does not contain :fun key"]))))


(defmethod component-ui "saying" [{:keys [id state]}]
  (fn [options]
    [:div 
      "saying options (at request): " (pr-str options)
       [clj-viewer {:fun 'demo.service.saying/saying
                    :args [options]}]      
     
     ]
))