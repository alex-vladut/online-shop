## Zipkin

According to official documentation:
> "Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures. Features include both the collection and lookup of this data."

Read more about Zipkin [here](https://zipkin.io/)

### Start Zipkin

For convenience, `docker-compose.yml` includes all the application's required dependencies and once started Zipkin is accessible at http://localhost:9411/.
Alternatively you can run only Zipkin in a Docker container with the following command:
```bash
$ docker run -d -p 9411:9411 openzipkin/zipkin
```