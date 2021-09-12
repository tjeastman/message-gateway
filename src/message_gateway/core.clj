(ns message-gateway.core
  (:require
   [clojurewerkz.machine-head.client :as mh]
   [iapetos.core :as prometheus]
   [iapetos.standalone :as prometheus-standalone]
   [message-gateway.collectors :refer [collectors collector-init]]
   [message-gateway.parser :refer [parse-fn]]
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

(defonce httpd
  (prometheus-standalone/metrics-server registry {:port metrics-server-port}))

(defn payload-log [value topic]
  (info (format "obtained value %s for topic %s from server" value topic))
  value)

(defn -main []
  (info "server running")
  (let [connection-string (format "tcp://%s:%d" message-server-hostname message-server-port)
        connection-options {:username message-server-username :password message-server-password}
        connection (mh/connect connection-string connection-options)]
    (doseq [{collector-key :key
             collector-topic :topic
             collector-format :format} collectors]
      (mh/subscribe
       connection
       {collector-topic 0}
       (let [payload-parse (collector-format parse-fn)
             payload-publish #(prometheus/set registry collector-key %)]
         (fn [_ _ payload]
           (-> payload
               (String. "UTF-8")
               (payload-parse)
               (payload-log collector-topic)
               (payload-publish))))))
    (info "connected to MQTT server")))
