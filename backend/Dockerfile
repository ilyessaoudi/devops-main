# === Runtime image only ===
FROM eclipse-temurin:17-jre
LABEL maintainer="saoudi ilyes Classe"

WORKDIR /app

# Create the logs directory
RUN mkdir -p /app/logs && chmod -R 777 /app/logs

# Copy the built JAR from Jenkins (from backend/target/)
COPY target/*.jar app.jar

# Create non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
