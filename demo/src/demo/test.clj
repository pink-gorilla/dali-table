(ns demo.test
  (:require 
   [modular.config :refer [load-config! config-atom]]))

(defn test [{:keys [config]}]
  (load-config! config)
  (println "loaded config: " @config-atom)
  )