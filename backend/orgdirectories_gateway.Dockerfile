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
RUN mvn dependency:go-offline -pl orgdirectories_gateway -am -B --no-transfer-progress

COPY orgdirectories_gateway/src orgdirectories_gateway/src
RUN mvn package -pl orgdirectories_gateway -am -DskipTests --no-transfer-progress

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/orgdirectories_gateway/target/orgdirectories_gateway.jar ./orgdirectories_gateway.jar

ENTRYPOINT ["java", "-jar", "./orgdirectories_gateway.jar"]
