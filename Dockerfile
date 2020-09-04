FROM clojure as builder
COPY . /app
WORKDIR /app
RUN lein uberjar

FROM openjdk:16-slim
COPY --from=builder /app/target/uberjar/message-gateway-0.1.0-SNAPSHOT-standalone.jar /
CMD ["java", "-jar", "/message-gateway-0.1.0-SNAPSHOT-standalone.jar"]
