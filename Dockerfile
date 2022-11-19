# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw clean package spring-boot:repackage


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

EXPOSE 80

COPY --from=builder ./app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]
