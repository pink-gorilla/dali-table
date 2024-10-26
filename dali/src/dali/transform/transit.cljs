(ns dali.transform.transit
  (:require
   [taoensso.timbre :refer-macros [info warn error]]
   [promesa.core :as p]
   [cquant.tmlds :refer [GET]]))

(defn load-transit [{:keys [url]}]
  (info "loading transit from url: " url)
  (let [load-promise (GET url)]
    (-> load-promise
        (p/then (fn [ds]
                  (info "url " url " loaded successfully. ")
                  ds))
        (p/catch (fn [err]
                   (error "could not load transit-data from url " url " err: " err))))
    ; give back the original promise
    load-promise))