# === Stage 1: Build the application ===
FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /app

# Copy the pom and source
COPY pom.xml ./
COPY src ./src

# Build the JAR (skip tests for speed; you may remove skip if you want tests)
RUN mvn clean package -DskipTests

# === Stage 2: Runtime image ===
FROM eclipse-temurin:17-jre
LABEL maintainer="saoudi ilyes Classe"
WORKDIR /app
# Create the logs directory
RUN mkdir -p /app/logs && chmod -R 777 /app/logs
# Copy the built JAR from the build stage
COPY --from=build /app/target/achat-1.0.jar ./app.jar

# Create non‑root user for security
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
