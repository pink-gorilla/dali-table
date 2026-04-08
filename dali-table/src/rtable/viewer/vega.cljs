(ns rtable.viewer.vega
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [reagent.core :as r]
   ["react-vega" :refer [VegaLite Vega]]
   ["vega-tooltip" :refer [Handler]]))

(def vega-opts
  {:theme  "quartz" ; "ggplot2"
   :tooltip {:theme "dark"}
   :hover     true  ; enable hover event processing
   :width 600
   :height 600
   :padding 5})

;(println "fb: " fb)

(def tt-handler
  (let [h (Handler.)]
    ;(println "tt handler: " h)
    h))

(def tt-call
  (let [c (. tt-handler -call)]
    ;(println "tt call: " c)
    c))

; tooltip= {new Handler () .call}

; const signalListeners = { hover: handleHover };
; signalListeners= {signalListeners}

(defn handle-hover [& args]
  (info "hover: " args))

(defn handle-tooltip [t & args]
  (info "tooltip: " (js->clj args)))

(defn handle-parse-error [& args]
  (info "vega spec parse error: " args))

(def signal-listeners
  {;:hover handle-hover
   :tooltip  handle-tooltip
   ;:on-parse-error handle-parse-error
   })

(defn vega [opts]
  (let [spec (:spec opts)
        user-opts (select-keys spec [:width :height :overflow])
        spec (if (map? spec) ; spec could be a map or a string (url)
               (assoc spec :usermeta {:embedOptions (merge vega-opts user-opts)})
               spec)]
    [:> Vega (merge opts
                    {; All signals defined in the spec can be listened to via signalListeners
                     :signalListeners signal-listeners ; (clj->js signal-listeners)
                     :tooltip tt-call
                     :actions {:export true :source true :compiled true :editor false}
                     :onParseError handle-parse-error
                     :spec spec})]))

(defn vegalite1 [opts]
  [vega (merge {:mode "vega-lite"} opts)])

(defn vegalite [opts]
  [:> VegaLite opts])

