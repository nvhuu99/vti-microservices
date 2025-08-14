FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/eureka-server-0.0.1-SNAPSHOT.jar"]
