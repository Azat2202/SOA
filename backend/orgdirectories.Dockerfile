FROM maven:3.9.11-amazoncorretto-11-alpine
WORKDIR /app
RUN apk --no-cache add curl
RUN curl -o payara-micro.jar https://nexus.payara.fish/repository/payara-community/fish/payara/extras/payara-micro/6.2025.9/payara-micro-6.2025.9.jar
COPY . .
RUN mvn package -pl orgdirectories_service
ENTRYPOINT java -jar ./payara-micro.jar --deploy ./orgdirectories_service/target/orgdirectories_service-1.0.war --port 8082
