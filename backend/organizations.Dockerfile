FROM maven:3.9.11-amazoncorretto-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY orgdirectories_service/pom.xml orgdirectories_service/
COPY organizations_service/pom.xml organizations_service/
COPY openapi-gen/pom.xml openapi-gen/
RUN mvn dependency:go-offline -B

COPY . .
RUN mvn package -pl organizations_service -am -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/organizations_service/target/organizations_service.jar ./organizations_service.jar

ENV DATABASE_HOST=db
ENV DATABASE_PORT=5432
ENTRYPOINT java -jar ./organizations_service.jar
