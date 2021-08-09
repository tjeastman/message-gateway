(ns message-gateway.core
  (:require [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone]
            [outpace.config :refer [defconfig]])
  (:gen-class))

(defconfig ^:required collectors)

(def collector-fn
  {:counter prometheus/counter
   :histogram prometheus/histogram
   :gauge prometheus/gauge})

(defn collector-init [collector-type collector-key]
  ((collector-type collector-fn) collector-key))

(defonce registry
  (reduce
   prometheus/register
   (prometheus/collector-registry)
   (map #(let [{collector-type :type collector-key :key} %]
           (collector-init collector-type collector-key)) collectors)))

(defonce httpd
  (prometheus-standalone/metrics-server registry {:port 8080}))

(defn -main []
  (println "running"))
