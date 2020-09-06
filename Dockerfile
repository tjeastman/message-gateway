FROM clojure as builder

WORKDIR /app
COPY project.clj /app/project.clj
RUN lein deps

COPY src /app/src
RUN lein uberjar

FROM openjdk:16-slim

COPY --from=builder /app/target/uberjar/message-gateway-0.1.0-SNAPSHOT-standalone.jar /
CMD ["java", "-jar", "/message-gateway-0.1.0-SNAPSHOT-standalone.jar"]
