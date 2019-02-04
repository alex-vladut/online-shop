# Online-Shop Application

In this part I will provide a concise description of how the application can be run in a Docker container. Later details will be provided on how to run a Docker image in a Kubernetes cluster.
Prerequisites: Make sure you have first installed Docker on your machine. Follow this link for details: https://www.docker.com/get-started

## Docker

The application can be run in a Docker container and a Gradle plugin was used in order to make the build of a Docker image easier. The plugin is `com.palantir.docker` (see `build.gradle` if you are curious how this looks like). In order to run the application in a Docker container follow these steps:
- (Prerequisites) Make sure you have Docker installed on your machine
- Run the following Gradle command for creating a Docker image locally:
```
> ./gradlew clean docker dockerTag
```
- Now a Docker image should have been created for you. In order to check that, run the following command:
```
> docker image ls
```
And you should get back something like this:
```
REPOSITORY          TAG                 IMAGE ID            CREATED              SIZE
online-shop         0.0.1               f2150f5d1531        About a minute ago   202MB
online-shop         latest              f2150f5d1531        About a minute ago   202MB
openjdk             8-jdk-alpine        21a93502ddd8        4 days ago           103MB
```
- The next step is to run the image you just created in a container. So run the following command in your terminal:
```
> docker run -it --name online-shop -p 8080:8080 online-shop
```
And voila!, the application should run just as it did before. If you go to http://localhost:8080/swagger-ui.html you should be able to interact with the application by invoking different endpoints.