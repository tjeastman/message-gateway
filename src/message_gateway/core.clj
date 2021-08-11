(ns message-gateway.core
  (:require [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone]
            [message-gateway.collectors :refer [collectors collector-init]]
            [outpace.config :refer [defconfig]]
            [taoensso.timbre :refer [info]])
  (:gen-class))

(defconfig ^:required port 8080)

(defonce registry
  (reduce
   prometheus/register
   (prometheus/collector-registry)
   (map #(let [{collector-type :type collector-key :key} %]
           (collector-init collector-type collector-key)) collectors)))

(defonce httpd
  (prometheus-standalone/metrics-server registry {:port port}))

(defn -main []
  (info "server running"))
