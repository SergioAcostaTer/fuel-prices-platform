FROM gradle:8.7.0-jdk21 AS build
WORKDIR /workspace
COPY . .
WORKDIR /workspace/apps/market-sinker
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/apps/market-sinker/build/libs/*.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app/app.jar"]
