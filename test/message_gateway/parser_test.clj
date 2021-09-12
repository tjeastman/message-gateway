(ns message-gateway.parser-test
  (:require [clojure.test :refer :all]
            [message-gateway.parser :refer :all]))

(deftest parse-int-test
  (is (= ((:int parse-fn) "333" ) 333)))
