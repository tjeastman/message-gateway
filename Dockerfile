FROM clojure
COPY . /app
WORKDIR /app
CMD ["lein", "run"]
