(ns rtable.viewer.plotly
  (:require
   [rtable.hooks :as hooks]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]))


(def default-loading-view "Loading...")

(defui plotly-uix [spec]
  (let [plotly (hooks/use-d3-require "plotly.js-dist@2.15.1")
        ref-fn (hooks/use-callback #(when % (.newPlot plotly % (clj->js spec))) [spec plotly])]
    (when spec
      (if plotly
        ($ :div.overflow-x-auto
           ($ :div.plotly {:ref ref-fn}))
        default-loading-view))))


(defn plotly [opts]
  ($ plotly-uix opts))

