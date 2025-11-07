# === Stage 1: Build the application ===
FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /app

# Copy the pom and source
COPY backend/pom.xml ./
COPY backend/src ./src

# Build the JAR (skip tests for speed; you may remove skip if you want tests)
RUN mvn clean package -DskipTests

# === Stage 2: Runtime image ===
FROM openjdk:17-jdk-slim AS runtime
LABEL maintainer="saoudi ilyes Classe"

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/achat-1.0.jar ./app.jar

# Create non‑root user for security
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

# Expose port your app listens on
EXPOSE 8089

# Entrypoint to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
