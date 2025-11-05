FROM openjdk:17-jdk-slim
LABEL maintainer="saoudi ilyes Classe"
WORKDIR /app
COPY target/achat-1.0.jar achat-backend.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "achat-backend.jar"]