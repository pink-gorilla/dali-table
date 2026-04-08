(ns rtable.transform.cheetah
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [dali.transform.load :refer [load-transit]]
   [dali.util.resolve :refer [resolve-col]]))

(defn load-and-transform-cheetah [{:keys [columns load] :as opts}]
  (info "cheetah transform opts: " opts)
  (let [ds-p (load-transit load)
        columns-p (resolve-col columns [:style
                                        :format])]
    (-> columns-p
        (p/then (fn [columns]
                  (info "cheetah resolved cols successfully: " columns)))
        (p/catch (fn [err]
                   (error "error resolving cheetah columns: " err))))
    (-> (p/all [ds-p
                columns-p])
        (p/then (fn [[ds columns]]
                  (info "all/cols:" columns)
                  (assoc opts ; assoc makes sure we don't loose style or class, ...
                         :columns columns
                         :ds ds))))))



