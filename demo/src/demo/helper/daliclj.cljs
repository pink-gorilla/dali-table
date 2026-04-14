(ns demo.helper.daliclj
  (:require
   [reagent.core :as r]
   [uix.core :refer [$ defui memo]]
   [uix.dom]
   [layout.flexlayout.comp :refer [component-ui]]
   [dali.viewer :refer [viewer2]]
   [dali.flowy :refer [dali-task-viewer]]))


(defn viewer-ui [options]
  (if options
    [viewer2 options]
    [:div "error: option does not exist"]))

(defmethod component-ui "viewer" [options]
  ($ :div (r/as-element [viewer-ui options])))

(defn clj-ui [options]
  (let [{:keys [fun]} @(:state options)]
    (if fun
      [dali-task-viewer fun]
      [:div "error: option does not contain :fun key"
       (pr-str options)
       "state: "
       (pr-str @(:state options))
       
       ])))

(defmethod component-ui "clj" [options]
  ($ :div (r/as-element [clj-ui options])))

(defn saying-ui [options]
  [:div
   "saying options (at request): " (pr-str options)
   [dali-task-viewer 'demo.service.saying/saying options]])

(defmethod component-ui "saying" [options]
  ($ :div (r/as-element [saying-ui options])))
  