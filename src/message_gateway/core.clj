(ns message-gateway.core
  (:require [clojurewerkz.machine-head.client :as mh]
            [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone]
            [message-gateway.collectors :refer [collectors collector-init]]
            [message-gateway.handlers :refer [handle-double]]
            [outpace.config :refer [defconfig]]
            [taoensso.timbre :refer [info]])
  (:gen-class))

(defconfig ^:required metrics-server-port 8080)
(defconfig ^:required message-server-port 1883)
(defconfig ^:required message-server-hostname)
(defconfig message-server-username)
(defconfig message-server-password)

(defonce registry
  (reduce
   prometheus/register
   (prometheus/collector-registry) (map collector-init collectors)))

;; (defonce httpd
;;   (prometheus-standalone/metrics-server registry {:port metrics-server-port}))

(defn -main []
  (info "server running")
  (let [conn-str (format "tcp://%s:%d" message-server-hostname message-server-port)
        conn-opts {:username message-server-username :password message-server-password}
        conn (mh/connect conn-str conn-opts)]
    (doseq [{collector-key :key collector-topic :topic} collectors]
      (mh/subscribe
       conn
       {collector-topic 0}
       (partial handle-double registry collector-key)))
    (info "connected to MQTT server")))
