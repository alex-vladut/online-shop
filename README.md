# Online-Shop Application

## Start the application

Further down I will try to describe the approach took for implementing this application, but if all you need is to start the application and play with it, then it's more than simple. First clone the source code:
```
> git clone https://github.com/alex-vladut/online-shop.git
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

## Domain analysis

In order to better understand the functional requirements, I will first apply the BDD approach to defining some examples based on the requirements described in the section above. Here are some such examples using Gherkin format:

> Given Bob is an admin user <br />
> When Bob attempts to create a product with name “Samsung S10” and the price of $1000.00 <br />
> Then the product is successfully created

> Given Bob is an admin user <br />
> When Bob attempts to retrieve the products available in the Product Catalog <br />
> Then a list of all the products is returned

> Given Bob is an admin user <br />
> And a product exists with name “Samsung S10” and the price $1000.00 <br />
> When Bob attempts to change the price of the product to $800.00 <br />
> Then the price of the product is updated to $800.00

> Given John is a user <br />
> And a product exists with name “Samsung S10” and the price $1000.00 <br />
> When John attempts to place an order for the product and provides an email address of “bob@bobscomp.com” <br />
> Then an order is created with the provided details

> Given John is a user <br />
> When John attempts to place an order but no product is selected <br />
> Then no order is created <br />
> And an error message is presented to the user informing them that a product should be selected

> Given John is a user <br />
> And a product exists with name “Samsung S10” and the price $1000.00 <br />
> When John attempts to place an order for the product and doesn't provide any email address <br />
> Then no order is created <br />
> And an error message is presented to the user informing them that an email address should be provided

In a real world application, it would be very important to implement authentication and authorisation. The above examples state the possible roles users may have in the system, but the implementation will ignore that information as no authentication/authorisation will be added for now.

Based on all the above, I could identify 2 Aggregates: Product and Order, each one being used for implementing different scenarios. Here is a diagram depicting how the domain model could look like:
<img src="/static/images/Domain-Model.png" class="img-responsive" alt="Online-Shop Domain Model" />
