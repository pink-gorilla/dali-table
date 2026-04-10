(ns rtable.viewer.vega
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [react]
   [uix.core :as uix :refer [defui $]]
   [uix.dom]
   [rtable.hooks :as hooks]
  ; ["react-vega" :refer [VegaLite Vega]]
  ; ["vega-tooltip" :refer [Handler]]
   ;["react-vega" :refer [useVegaEmbed]]
   ))

#_(defn vegalite [opts]
    [:> VegaLite opts])



;(def tt-handler (let [h (Handler.)] h))

;(def tt-call (let [c (js/console.log "tt call: " c)   c))

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


(def vega-opts
  {:theme  "quartz" ; "ggplot2"
   :tooltip {:theme "dark"}
   :hover     true  ; enable hover event processing
   :width 600
   :height 600
   :padding 5})


#_(defn vega [{:keys [spec] :as opts}]
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

#_(defn vegalite1 [opts]
    [vega (merge {:mode "vega-lite"} opts)])


#_(defui vega-uix [{:keys [spec options] :as opts}]
    (let [ref (react/useRef nil)
          result  (useVegaEmbed #js {:ref ref :spec spec :options options})]
      ($ :div (-> opts
                  (dissoc :spec :options)
                  (assoc :ref ref)))))


(def default-loading-view "Loading...")

(defui vega-lite-uix [value]
  (let [handle-error (hooks/use-error-handler)
        vega-embed (hooks/use-d3-require "vega-embed@6.11.1")
        opts (get value :embed/opts {})
        ref-fn (react/useCallback #(when %
                                     (-> (.embed vega-embed
                                                 %
                                                 (clj->js (dissoc value :embed/opts :embed/callback))
                                                 (clj->js opts))
                                         (.then (fn [result] (if-let [callback (:embed/callback value)]
                                                               (callback result)
                                                               result)))
                                         (.catch handle-error)))
                                  #js[value vega-embed])]
    (when value
      (if vega-embed
        ($ :div {:style (or (:style value) {})
                 :class (or (:class value) "")}
           ($ :div.vega-lite {:ref ref-fn}))
        default-loading-view))))

(defn vegalite [opts]
  ($ vega-lite-uix opts))

(defn vega [opts]
  (let [embed-opts (-> (or (:embed/opts opts) {})
                       (assoc :mode "vega"))]
    ($ vega-lite-uix (assoc opts :embed/opts embed-opts))))
