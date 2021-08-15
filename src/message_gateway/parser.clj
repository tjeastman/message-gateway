(ns message-gateway.parser
  (:require [taoensso.timbre :refer [debug]]))

(defn decode-payload [payload] (String. payload "UTF-8"))

(defn parse-int [topic _ payload]
  (let [value (Integer/parseInt (decode-payload payload))]
    (debug (format "obtained value %d for topic %s from server" value topic))
    value))

(defn parse-double [topic _ payload]
  (let [value (Double/parseDouble (decode-payload payload))]
    (debug (format "obtained value %f for topic %s from server" value topic))
    value))

(defn parse-bool [topic _ payload]
  (let [value (Boolean/parseBoolean (decode-payload payload))]
    (debug (format "obtained value %b for topic %s from server" value topic))
    value))
