FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/account-service-0.0.1-SNAPSHOT.jar account-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/account-service-0.0.1-SNAPSHOT.jar"]
