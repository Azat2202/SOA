FROM maven:3.9.11-amazoncorretto-17-alpine AS build
WORKDIR /app

# Copy pom files first for dependency caching
COPY pom.xml .
COPY openapi-gen/pom.xml openapi-gen/
COPY organizations_api/pom.xml organizations_api/
COPY organizations_ejb/pom.xml organizations_ejb/
COPY organizations_service/pom.xml organizations_service/
COPY organizations_ear/pom.xml organizations_ear/
COPY orgdirectories_service/pom.xml orgdirectories_service/
COPY orgdirectories_config_server/pom.xml orgdirectories_config_server/
COPY orgdirectories_eureka/pom.xml orgdirectories_eureka/

RUN mvn dependency:go-offline -pl organizations_ear -am -B --no-transfer-progress

COPY . .

RUN mvn clean package -pl organizations_ear -am -DskipTests --no-transfer-progress

RUN mvn dependency:copy -Dartifact=org.postgresql:postgresql:42.7.2 -DoutputDirectory=/app/lib

FROM payara/server-full:6.2024.10-jdk17

USER root
COPY --from=build /app/lib/postgresql-42.7.2.jar /opt/payara/glassfish/lib/
RUN chown payara:payara /opt/payara/glassfish/lib/postgresql-42.7.2.jar
USER payara

COPY --from=build /app/organizations_ear/target/organizations_ear-1.0.ear ${DEPLOY_DIR}/
