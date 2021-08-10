(ns message-gateway.collectors
  (:require [iapetos.core :as prometheus]
            [outpace.config :refer [defconfig]]))

(def collector-fn
  {:counter prometheus/counter
   :histogram prometheus/histogram
   :gauge prometheus/gauge})

(defn collectors-valid? [collectors]
  (every? #(contains? collector-fn (:type %)) collectors))

(defn collector-init [collector-type collector-key]
  ((collector-type collector-fn) collector-key))

(defconfig
  ^{:validate [collectors-valid? "invalid collectors"]}
  ^:required collectors)
