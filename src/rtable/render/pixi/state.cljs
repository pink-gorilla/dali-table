(ns rtable.render.pixi.state
  (:require
   [tech.v3.dataset :as tmlds]))


(defn adjust-visible [state]
  (let [{:keys [width step-px ds end-idx]} @state
        row-count-visible (int (/ width step-px))
        start-idx (max 0 (- end-idx row-count-visible))
        ds-visible (tmlds/select-rows ds (range start-idx end-idx))]

    (swap! state assoc
           :start-idx start-idx
           :ds-visible ds-visible
           :row-count-visible row-count-visible
           )))



(defn create-state [{:keys [width height
                            step-px]} container ds]
  (let [row-count (tmlds/row-count ds)
        state (atom {:width width
                     :height height
                     :step-px step-px
                     :row-count row-count
                     :ds ds
                     :end-idx row-count
                     :container container
                     })]
    (adjust-visible state)
    (println "state: " (dissoc @state :ds))
    state))
  


