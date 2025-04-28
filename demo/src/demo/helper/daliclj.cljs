(ns demo.helper.daliclj
  (:require
   [ui.flexlayout :refer [component-ui]]
   [dali.viewer :refer [viewer2]]
   [dali.flowy :refer [dali-task-viewer]]))

(defmethod component-ui "viewer" [{:keys [id state]}]
  (fn [options]
    (if options
      [viewer2 options]
      [:div "error: option does not exist"])))

(defmethod component-ui "clj" [{:keys [id state]}]
  (fn [options]
    (let [{:keys [fun]} options]
      (if fun
        [dali-task-viewer fun]
        [:div "error: option does not contain :fun key"]))))

(defmethod component-ui "saying" [{:keys [id state]}]
  (fn [options]
    [:div
     "saying options (at request): " (pr-str options)
     [dali-task-viewer 'demo.service.saying/saying options]]))