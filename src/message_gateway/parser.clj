(ns message-gateway.parser)

(def parse-fn
  {:double #(Double/parseDouble %)
   :long #(Long/parseLong %)
   :int #(Integer/parseInt %)
   :bool #(Boolean/parseBoolean %)})
