(ns message-gateway.core
  (:require [clojurewerkz.machine-head.client :as mh]
            [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone]
            [message-gateway.collectors :refer [collectors collector-init]]
            [message-gateway.handlers :refer [handle-double handle-int]]
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
    (mh/subscribe conn {"/cpu/0/percent" 0} (partial handle-double registry :system/cpu/0/percent))
    (mh/subscribe conn {"/cpu/1/percent" 0} (partial handle-double registry :system/cpu/1/percent))
    (mh/subscribe conn {"/cpu/2/percent" 0} (partial handle-double registry :system/cpu/2/percent))
    (mh/subscribe conn {"/cpu/3/percent" 0} (partial handle-double registry :system/cpu/3/percent))

    (mh/subscribe conn {"/cpu/0/freq" 0} (partial handle-double registry :system/cpu/0/freq))
    (mh/subscribe conn {"/cpu/1/freq" 0} (partial handle-double registry :system/cpu/1/freq))
    (mh/subscribe conn {"/cpu/2/freq" 0} (partial handle-double registry :system/cpu/2/freq))
    (mh/subscribe conn {"/cpu/3/freq" 0} (partial handle-double registry :system/cpu/3/freq))
    (info "connected to MQTT server")))
