FROM maven:3.9.11-amazoncorretto-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY openapi-gen/pom.xml openapi-gen/
COPY organizations_api/pom.xml organizations_api/
COPY organizations_ejb/pom.xml organizations_ejb/
COPY organizations_service/pom.xml organizations_service/
COPY organizations_ear/pom.xml organizations_ear/
COPY orgdirectories_service/pom.xml orgdirectories_service/
COPY orgdirectories_config_server/pom.xml orgdirectories_config_server/
COPY orgdirectories_eureka/pom.xml orgdirectories_eureka/
COPY orgdirectories_gateway/pom.xml orgdirectories_gateway/
COPY orgdirectories_gen/pom.xml orgdirectories_gen/pom.xml
COPY orgdirectories_rest/pom.xml orgdirectories_rest/pom.xml
COPY temporal-models/pom.xml temporal-models/pom.xml
RUN mvn dependency:go-offline -pl orgdirectories_config_server -am -B --no-transfer-progress

COPY orgdirectories_config_server/src orgdirectories_config_server/src
RUN mvn package -pl orgdirectories_config_server -am -DskipTests --no-transfer-progress

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/orgdirectories_config_server/target/orgdirectories_config_server.jar ./orgdirectories_config_server.jar

ENV DATABASE_HOST=db
ENV DATABASE_PORT=5432
ENTRYPOINT java -jar ./orgdirectories_config_server.jar