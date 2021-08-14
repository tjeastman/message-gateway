(ns message-gateway.core
  (:require [clojurewerkz.machine-head.client :as mh]
            [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone]
            [message-gateway.collectors :refer [collectors collector-init]]
            [message-gateway.handlers :refer [handle-double handle-int]]
            [outpace.config :refer [defconfig]]
            [taoensso.timbre :refer [info]])
  (:gen-class))

(defconfig ^:required port 8080)
(defconfig ^:required mh-port 1883)
(defconfig ^:required mh-hostname "localhost")

(defonce registry
  (reduce
   prometheus/register
   (prometheus/collector-registry)
   (map #(let [{collector-type :type collector-key :key} %]
           (collector-init collector-type collector-key)) collectors)))

(defonce httpd
  (prometheus-standalone/metrics-server registry {:port port}))

(defn -main []
  (info "server running")
  (let [conn-str (format "tcp://%s:%d" mh-hostname mh-port)
        conn (mh/connect conn-str)]
    (info "connected to MQTT server")))
