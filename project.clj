(defproject message-gateway "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/tjeastman/message-gateway"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-jetty-adapter "1.8.1"]
                 [ring/ring-json "0.5.0"]]
  :main ^:skip-aot message-gateway.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
