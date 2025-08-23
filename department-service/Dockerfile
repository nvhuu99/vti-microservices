FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/department-service-0.0.1-SNAPSHOT.jar department-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/department-service-0.0.1-SNAPSHOT.jar"]
