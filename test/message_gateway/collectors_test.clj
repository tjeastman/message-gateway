(ns message-gateway.parser-test
  (:require [clojure.test :refer :all]
            [iapetos.core :as prometheus]
            [message-gateway.collectors :refer :all]))

(deftest collector-init-test
  (is (= (collector-init :counter :total-seconds)
         (prometheus/counter :total-seconds))))

(deftest collector-valid-test
  (is (= (collector-valid {:type :counter}))))
