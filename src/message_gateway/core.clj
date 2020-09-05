(ns message-gateway.core
  (:require [iapetos.core :as prometheus]
            [iapetos.standalone :as prometheus-standalone])
  (:gen-class))

(defonce registry
  (-> (prometheus/collector-registry)
      (prometheus/register
       (prometheus/counter :app/runs-total)
       (prometheus/histogram :app/duration-seconds)
       (prometheus/gauge :app/active-users-total))))

(defonce httpd
  (prometheus-standalone/metrics-server registry {:port 8080}))

(defn -main []
  (-> registry
      (prometheus/inc :app/runs-total)
      (prometheus/observe :app/duration-seconds 0.7)
      (prometheus/set :app/active-users-total 22))
  (println "running"))
