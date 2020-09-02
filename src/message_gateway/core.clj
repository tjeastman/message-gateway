(ns message-gateway.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [content-type response]])
  (:gen-class))

(defn handler [request]
  (response {:message "Hello World"}))

(def app
  (wrap-json-response handler))

(defn -main []
  (run-jetty app {:port 3000 :join? false}))
