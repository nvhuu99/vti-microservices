FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/auth-service-0.0.1-SNAPSHOT.jar"]
