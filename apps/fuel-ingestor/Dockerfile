# ---- Stage 1: Build ----
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /workspace

# Copy everything
COPY . .

# Build the JAR (skip tests for speed, remove --no-daemon if you prefer)
WORKDIR /workspace/apps/fuel-ingestor
RUN ./gradlew bootJar --no-daemon -x test

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Copy the fat JAR from build stage
COPY --from=build /workspace/apps/fuel-ingestor/build/libs/*.jar app.jar

# Default Spring Boot port
EXPOSE 8081

# Run the app
ENTRYPOINT ["java","-jar","/app/app.jar"]
