(defproject message-gateway "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/tjeastman/message-gateway"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[clj-commons/iapetos "0.1.11"]
                 [org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot message-gateway.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
