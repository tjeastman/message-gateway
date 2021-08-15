(ns message-gateway.parser-test
  (:require [clojure.test :refer :all]
            [message-gateway.parser :refer :all]))

(deftest parse-int-test
  (is (= (parse-int "topic" nil (.getBytes "333")) 333)))

(deftest parse-double-test
  (is (= (parse-double "topic" nil (.getBytes "3.2")) 3.2)))

(deftest parse-bool-test
  (is (= (parse-bool "topic" nil (.getBytes "true")) true)))
