(ns rtable.transform.rtable
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [dali.util.resolve :refer [resolve-col]]))

(defn transform-rtable [{:keys [columns] :as opts}]
  (info "rtable transform opts: " opts)
  (let [columns-p (resolve-col columns [:format
                                        :attrs
                                        :render-cell])]
    (-> columns-p
        (p/then (fn [columns]
                  (info "rtable resolved cols successfully: " columns)
                  (assoc opts ; assoc makes sure we don't loose style or class, ...
                         :columns columns)))
        (p/catch (fn [err]
                   (error "error resolving cheetah columns: " err))))))



