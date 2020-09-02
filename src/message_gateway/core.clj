(ns message-gateway.core
  (:require [ring.adapter.jetty :as ring])
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(defn -main []
  (ring/run-jetty handler {:port 3000 :join? false}))
