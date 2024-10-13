(ns rtable.data.cheetah
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [rtable.data :refer [load-and-transform2 load-dataset]]
   [rtable.resolve :refer [resolve-col]]))

(defmethod load-and-transform2 :cheetah [{:keys [columns url] :as opts}]
  (let [ds-p (load-dataset url)
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



