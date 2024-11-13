(ns rtable.plot.highcharts
  (:require
   [tick.core :as t]
   [tech.v3.datatype :as dtype]
   [tablecloth.api :as tc]
   [dali.spec :refer [create-dali-spec]]
   [dali.store.file.transit] ; side effects
   [dali.store :refer [write]]))

; currently tml transit encoding has a bug and wrongly decodes instant.
; so we add :epoch column before writing

(defn instant->epoch-ms [dt]
  (-> dt t/instant t/long (* 1000)))

(defn add-epoch-ms [ds]
  (tc/add-column
   ds
   :epoch
   (->> ds
        :date
        ;(dtype/emap instant->epoch-ms :long)
        ;(dtype/clone)
        (map instant->epoch-ms))))

(defn highstock-ds [{:keys [dali-store]}
                    {:keys [style class charts]
                     :or {style {:width "100%" :height "100%"}
                          class ""}}
                    ds]
  (create-dali-spec
   {:viewer-fn 'rtable.viewer.highcharts/highstock
    :transform-fn 'rtable.transform.highcharts/load-and-transform-highcharts
    :data {:style style
           :class class
           :charts charts
           :load (->>  ds
                       ;(add-epoch-ms)
                       (write dali-store "transit-json"))}}))

(comment

  (def ds (tc/dataset {:a [1]
                       :date [(t/instant)]}))
  ds

  (add-epoch-ms ds)
  ;; => _unnamed [1 3]:
  ;;    
  ;;    | :a |                       :date |        :epoch |
  ;;    |---:|-----------------------------|--------------:|
  ;;    |  1 | 2024-11-13T15:29:28.588495Z | 1731511768000 |

  (require '[dali.store.file :refer [create-dali-file-store]])

  (def env
    {:dali-store
     (create-dali-file-store {:fpath "demo/.data"
                              :rpath "/r/data"})})

  (def plot-spec
    (highstock-ds env {:charts []} ds))

  (require '[dali.store :refer [open]])

  (->> plot-spec
       :data
       :load
       (open (:dali-store env)))
  ;; => _unnamed [1 3]:
  ;;    
  ;;    | :a |                       :date |        :epoch |
  ;;    |---:|-----------------------------|--------------:|
  ;;    |  1 | 1970-01-21T00:58:31.768588Z | 1731511768000 |

  (add-epoch-ms ds)
  ;; => _unnamed [1 3]:
  ;;    
  ;;    | :a |                       :date |        :epoch |
  ;;    |---:|-----------------------------|--------------:|
  ;;    |  1 | 2024-11-13T15:29:28.588495Z | 1731511768000 |

  (->   (add-epoch-ms ds)
        (tc/info))

  (some #{101} '(100 101 102))
  (some #{105} '(100 101 102))

  (-> (t/instant)
      instant->epoch-ms)
  ;; => 1731511709000

; 
  )