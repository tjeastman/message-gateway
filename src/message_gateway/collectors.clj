(ns message-gateway.collectors
  (:require [iapetos.core :as prometheus]
            [outpace.config :refer [defconfig]]
            [taoensso.timbre :refer [debug]]))

(def collector-fn
  {:counter prometheus/counter
   :histogram prometheus/histogram
   :gauge prometheus/gauge})

(defn collector-valid? [collector]
  (contains? collector-fn (:type collector)))

(defn collectors-valid? [collectors]
  (every? collector-valid? collectors))

(defn collector-init [collector-type collector-key]
  (debug (format "initializing collector %s of type %s" collector-key collector-type))
  ((collector-type collector-fn) collector-key))

(defconfig
  ^{:validate [collectors-valid? "invalid collectors"]}
  ^:required collectors)
