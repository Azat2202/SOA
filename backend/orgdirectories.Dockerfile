FROM maven:3.9.11-amazoncorretto-11-alpine AS build
WORKDIR /app

RUN apk --no-cache add curl

RUN curl -L -o payara-micro.jar \
    https://nexus.payara.fish/repository/payara-community/fish/payara/extras/payara-micro/6.2025.9/payara-micro-6.2025.9.jar

COPY pom.xml .
COPY orgdirectories_service/pom.xml orgdirectories_service/
COPY organizations_service/pom.xml organizations_service/
COPY openapi-gen/pom.xml openapi-gen/
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn package -pl orgdirectories_service -am -DskipTests

FROM amazoncorretto:11-alpine
WORKDIR /app

COPY --from=build /app/payara-micro.jar .
COPY --from=build /app/orgdirectories_service/target/orgdirectories_service-1.0.war ./orgdirectories_service.war

ENTRYPOINT java -jar ./payara-micro.jar --deploy ./orgdirectories_service.war --port 8082
