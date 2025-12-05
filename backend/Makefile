build/organizations:
	mvn clean package -pl organizations_ear -am -DskipTests

docker/build:
	docker build -f organizations.Dockerfile -t organizations-app .

docker/run:
	docker run -it --rm \
		-p 8080:8080 \
		-p 4848:4848 \
		-e DATABASE_HOST=host.docker.internal \
		-e DATABASE_PORT=5433 \
		-e DATABASE_DB=study \
		-e DATABASE_USERNAME=study \
		-e DATABASE_PASSWORD=study \
		organizations-app

run/organizations: build/organizations docker/build docker/run
