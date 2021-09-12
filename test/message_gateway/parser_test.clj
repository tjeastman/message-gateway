(ns message-gateway.parser-test
  (:require [clojure.test :refer :all]
            [message-gateway.parser :refer :all]))

(deftest parse-double-test
  (is (= ((:double parse-fn) "333.33" ) 333.33)))

(deftest parse-long-test
  (is (= ((:long parse-fn) "3333333333" ) 3333333333)))

(deftest parse-int-test
  (is (= ((:int parse-fn) "333" ) 333)))

(deftest parse-bool-test
  (is ((:bool parse-fn) "true" )))
