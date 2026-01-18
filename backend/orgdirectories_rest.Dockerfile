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
RUN mvn dependency:go-offline -pl orgdirectories_rest -am -B --no-transfer-progress

COPY openapi-gen/src openapi-gen/src
COPY orgdirectories_rest/src orgdirectories_rest/src
COPY swagger.yaml swagger.yaml
RUN mvn package -pl orgdirectories_rest -am -DskipTests --no-transfer-progress

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/orgdirectories_rest/target/orgdirectories_rest.jar ./orgdirectories_rest.jar

ENTRYPOINT java -jar ./orgdirectories_rest.jar