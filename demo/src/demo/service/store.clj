(ns demo.service.store
  (:require
   [dali.store.file :refer [create-dali-file-store]] 
   ))


(def s (create-dali-file-store {:fpath ".gorilla/public/service"
                                :rpath "/r/service"}))