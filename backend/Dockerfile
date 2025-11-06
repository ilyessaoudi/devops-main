# === Stage 1: Build the application ===
FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /app
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn clean package -DskipTests

# === Stage 2: Create the runtime image ===
FROM openjdk:17-slim  
LABEL maintainer="saoudi ilyes Classe"
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/achat-1.0.jar ./app.jar

# Use a non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
