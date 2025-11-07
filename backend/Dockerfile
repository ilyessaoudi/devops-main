# === Stage 1: Build the application ===
FROM eclipse-temurin:17 AS build
WORKDIR /app
COPY backend/pom.xml ./
COPY backend/src ./src
RUN mvn clean package -DskipTests

# === Stage 2: Runtime image ===
FROM eclipse-temurin:17-jre
LABEL maintainer="saoudi ilyes Classe"
WORKDIR /app
COPY --from=build /app/target/achat-1.0.jar ./app.jar
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
