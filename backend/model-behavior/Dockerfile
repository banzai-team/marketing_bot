FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY ./model-behavior/pom.xml ./pom.xml
COPY ./model-behavior/application/pom.xml ./application/pom.xml
COPY ./model-behavior/core/pom.xml ./core/pom.xml

RUN mvn install --fail-never

COPY ./commons ./commons
WORKDIR /build/commons
RUN mvn install

WORKDIR /build

COPY ./model-behavior/application/src ./application/src
COPY ./model-behavior/core/src ./core/src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder /build/application/target/application-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT java -jar /app/app.jar