FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

WORKDIR /build
COPY ./commons ./commons
WORKDIR /build/commons
RUN mvn install

WORKDIR /build
COPY ./model-interceptor/pom.xml ./pom.xml

RUN mvn install --fail-never
COPY ./model-interceptor/src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

COPY --from=builder /build/target/model-interceptor-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT java -jar /app/app.jar