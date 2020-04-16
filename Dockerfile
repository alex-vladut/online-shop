FROM openjdk:latest

ENV CASSANDRA_ENDPOINT=host.docker.internal

RUN groupadd group && useradd -g group -s /bin/sh user
USER user:group
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Dcassandra.endpoint=${CASSANDRA_ENDPOINT}", "-jar","/app.jar"]