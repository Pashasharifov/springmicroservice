# Stage 1 - Build
FROM gradle:8.5-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2 - Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/build/libs/discovery-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]