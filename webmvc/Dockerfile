FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/overtime-webmvc-*.jar overtime-webmvc.jar
CMD ["java", "-jar", "overtime-webmvc.jar", "--spring.profiles.active=docker"]