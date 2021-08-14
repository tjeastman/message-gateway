(ns message-gateway.handlers
  (:require [iapetos.core :as prometheus]
            [taoensso.timbre :refer [debug]]))

(defn handle-double [registry key topic _ payload]
  (let [val (Double/parseDouble (String. payload "UTF-8"))]
    (debug (format "obtained value %.10f for topic %s from server" val topic))
    (prometheus/set registry key val)))

(defn handle-int [registry key topic _ payload]
  (let [val (Integer/parseInt (String. payload "UTF-8"))]
    (debug (format "obtained value %d for topic %s from server" val topic))
    (prometheus/set registry key val)))
