FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY ./pom.xml ./pom.xml

RUN mvn install --fail-never
COPY ./src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder /build/target/api-gateway-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT java -jar /app/app.jar