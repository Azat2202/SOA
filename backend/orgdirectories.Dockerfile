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
COPY orgdirectories_gen/src/main/resources/contract.xsd orgdirectories_gen/src/main/resources/contract.xsd
COPY temporal-models/pom.xml temporal-models/pom.xml
COPY temporal-models/src/main/proto/models.proto temporal-models/src/main/proto/models.proto
RUN mvn dependency:go-offline -pl orgdirectories_service -am -B --no-transfer-progress

COPY openapi-gen/src openapi-gen/src
COPY temporal-models/src  temporal-models/src
COPY orgdirectories_service/src orgdirectories_service/src
COPY swagger.yaml swagger.yaml
RUN mvn package -pl orgdirectories_service -am -DskipTests --no-transfer-progress

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/orgdirectories_service/target/orgdirectories_service.jar ./orgdirectories_service.jar

ENV DATABASE_HOST=db
ENV DATABASE_PORT=5432
ENTRYPOINT java -jar ./orgdirectories_service.jar