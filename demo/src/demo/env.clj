(ns demo.env
  (:require
   [dali.store.file :refer [create-dali-file-store]]))

(def env
  {:dali-store (create-dali-file-store
                {:fpath ".gorilla/public/dali"
                 :rpath "/r/dali"})})


