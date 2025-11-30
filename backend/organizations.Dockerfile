FROM maven:3.9.11-amazoncorretto-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY orgdirectories_service/pom.xml orgdirectories_service/
COPY organizations_service/pom.xml organizations_service/
COPY openapi-gen/pom.xml openapi-gen/
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn package -pl organizations_service -am -DskipTests

FROM payara/server-full:6.2024.10-jdk17

COPY --from=build /app/organizations_service/target/organizations_service-1.0.war ${DEPLOY_DIR}/organizations_service.war

ENV DATABASE_HOST=db
ENV DATABASE_PORT=5432
ENV DATABASE_DB=study
ENV DATABASE_USERNAME=study
ENV DATABASE_PASSWORD=study
