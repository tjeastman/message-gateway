(ns message-gateway.handlers
  (:require
   [iapetos.core :as prometheus]
   [taoensso.timbre :refer [debug]]))

(defn handle-double [registry collector-key topic _ payload]
  (let [value (Double/parseDouble (String. payload "UTF-8"))]
    (debug (format "obtained value %.10f for topic %s from server" value topic))
    (prometheus/set registry collector-key value)))

(defn handle-int [registry collector-key topic _ payload]
  (let [value (Integer/parseInt (String. payload "UTF-8"))]
    (debug (format "obtained value %d for topic %s from server" value topic))
    (prometheus/set registry collector-key value)))

(defn handle-long [registry collector-key topic _ payload]
  (let [value (Long/parseLong (String. payload "UTF-8"))]
    (debug (format "obtained value %d for topic %s from server" value topic))
    (prometheus/set registry collector-key value)))
