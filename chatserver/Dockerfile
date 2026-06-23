# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /chatmate
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21
COPY --from=build /chatmate/target/*.jar ./chatmate.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "chatmate.jar"]