# Online-Shop Application

In this part I will provide a concise description of how the application can be run in a Docker container. Later details will be provided on how to run a Docker image in a Kubernetes cluster.
Prerequisites: Make sure you have first installed Docker on your machine. Follow this link for details: [https://www.docker.com/get-started](https://www.docker.com/get-started)

## Docker

Build a Docker image using the following command:
```
$ docker build . -t online-shop
```
- Now a Docker image should have been created for you. In order to check that, run the following command:
```
> docker image ls
```
and you should get back something like this:
```
REPOSITORY          TAG                 IMAGE ID            CREATED              SIZE
online-shop         0.0.1               f2150f5d1531        About a minute ago   202MB
online-shop         latest              f2150f5d1531        About a minute ago   202MB
...
```
The next step is to run the image you just created in a container. So run the following command in your terminal:
```
> docker run -it --name online-shop -p 8080:8080 -t online-shop
```
And voila!, the application should run just as it did before. If you go to http://localhost:8080/swagger-ui.html you should be able to interact with the application by invoking different endpoints.