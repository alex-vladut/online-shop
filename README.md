# Online-Shop Application

## Prerequisites

Make sure you have the following dependencies installed before running the application:
- [Java 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Docker for Desktop](https://docs.docker.com/docker-for-mac/)

## Running the application

Clone the project:
```bash
$ git clone https://github.com/alex-vladut/online-shop.git
$ cd online-shop
```

Start all the dependencies with docker-compose (e.g. Cassandra, Zipkin):
```bash
$ docker-compose -f docker-compose.yml up 
```

Then start the application:
```bash
$ ./gradlew bootRun
```
The application is now running and you can open the following URL in a browser of your choice in order to play with the API: http://localhost:3000/swagger-ui.html

## General Description

This is a basic RESTful Web Services implementation of an online-shop application using Java and various modules of Spring Framework. In terms of functional requirements, the application should allow administrators to manage a Product Catalog, offering them features such as:
- Create a new product by specifying its price and name;
- Fetch all the products available in the product catalog;
- Update the name and/or price of a given product.
A customer should have the option to place an order by specifying the products to be ordered and an email address. The administrator users should be able to run reports by searching for all the orders placed between given dates. The details about an order should include the products ordered, the price of each product at the time when the order was placed, the total price of the order.

Technical requirements:
- Spring Boot in order to generate a Spring project ready to use
- Spring MVC for creating RESTful APIs
- Spring Data for managing the interaction with the Database
- Use Cassandra for persisting data
- Swagger for documenting the APIs
- Implement tests
- Follow Domain Driven Design and Behaviour Driven Development for better understanding the requirements

## Other resources

- [*Domain analysis*](/static/pages/data-model.md)
- [*Implementation*](/static/pages/implementation.md)
- [*Run the app with Docker*](/static/pages/docker-kubernetes.md)
- [*SonarQube*](/static/pages/sonar-qube.md)
- [*Zipkin*](/static/pages/zipkin.md)
- [*Security vulnerabilities check*](/static/pages/dependency-check.md)
