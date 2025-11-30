build/organizations:
	mvn clean package -pl organizations_service -am -DskipTests
	mvn -pl organizations_service payara-micro:bundle -f pom.xml

run/organizations: build/organizations
	export DATABASE_HOST=localhost
	export DATABASE_PORT=5433
	export DATABASE_DB=study
	export DATABASE_USERNAME=study
	export DATABASE_PASSWORD=study
	java -jar organizations_service/target/organizations_service-1.0-microbundle.jar

