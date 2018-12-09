# Online-Shop Application

## Start the application

Further down I will try to describe the approach took for implementing this application, but if all you need is to start the application and play with it, then it's more than simple. First clone the source code:
```
> git clone 
```
Then go the the directory where the project resides:
```
> cd online-shop
```
After navigating to the `online-shop` directory, run the following Gradle command in order to start the application:
```
> ./gradlew bootRun
```
The application is now running and you can open the following URL in a browser of your choice in order to play with the API: http://localhost:8080/swagger-ui.html

## General Description

This is a basic RESTful Web Services implementation of an online-shop application using Java and various modules of Spring Framework. In terms of functional requirements, the application should allow administrators to manage a Product Catalog, offering them features such as:
- Create a new product by specifying its price and name;
- Fetch all the products available in the product catalog;
- Update the name and/or price of a given product.
A customer should have the option to place an order by specifying the products to be ordered and an email address. The administrator users should be able to run reports by searching for all the orders placed between given dates. The details about an order should include the products ordered, the price of each product at the time when the order was placed, the total price of the order.

Technical requirements:
- Spring Boot in order to generate a Spring project ready to use
- Spring Web for creating RESTful endpoints
- Spring Data for managing the interaction with the Database
- Use Cassandra for persisting data
- Swagger for documenting the APIs
- Implement tests
- Follow Domain Driven Design and Behaviour Driven Development for better understanding the requirements
