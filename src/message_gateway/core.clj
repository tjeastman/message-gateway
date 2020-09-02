(ns message-gateway.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :refer [content-type response]])
  (:gen-class))

(defn handler [request]
  (content-type (response "Hello World") "text/html"))

(defn -main []
  (run-jetty handler {:port 3000 :join? false}))
