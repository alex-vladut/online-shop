FROM openjdk:8-jdk-alpine
ARG BUILD_VERSION

ADD online-shop-${BUILD_VERSION}.jar online-shop.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar /online-shop.jar
