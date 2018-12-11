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
- Spring MVC for creating RESTful APIs
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

## Implementation

I opted for using Cassandra DB for persisting the data, this was mostly due to the fact that I never worked with Cassandra before and I wanted to get an idea of how it works. I also played with a standalone version, but for this project I used CassandraUnit, which is an embedded version. That decision was made in order to make the configuration of the project easier.

I used Spring Data module for managing the access to the database. It provides also an implementation for Cassandra. I also planned to use Spring Data REST in order to automatically generate RESTful endpoints, especially for managing the products as it is more CRUDy oriented (not a lot of business logic involved), but I encountered a few problems due to the integration with Cassandra and I abandoned the idea for now. Will come back to it later and get it working.

The RESTful API endpoints for managing products and orders were implemented manually using Spring MVC Controllers. For documenting the APIs I used Springfox implementation of Swagger, as this philosophy of generating some of the specifications based on Spring annotations appealed to me. For details that couldn't be extracted directly from the usage of Spring Framework, I used Swagger2 specific annotations.

My testing strategy is generally to use a combination of both unit and integration/service tests. I think that unit tests are very important for testing business logic, especially when a good job was done for this business logic to be encapsulated in domain objects that require no interactions with other services. When mocking comes at play, then the unit tests will become very cumbersome and hard to maintain. For such glue code, where multiple components interact with each other, I think an integration test will add more benefits as the tests could include whole flows. BDD offers a nice approach to implement business cases, which includes also interactions with a database. Unfortunatelly I didn't yet get the time to implement such an example for this project, but if you are curious what it would look like please take a look at this GitHub repo https://github.com/alex-vladut/bdd-shop.

This version of the project is still missing a lot of features that would be essential for a real world project. For example, no Transaction Management was implemented. Would be hard to implement RDBMS-like transactions using a NoSQL DB, but at least some optimistic locking or conditions on save/update would be useful in order to make sure that data doesn't end up in an unpredictable state. The error handling could also be improved a lot by adding some general error handlers in order to prevent stack traces from reaching the client and also to translate domain specific exceptions to proper HTTP status codes/error messages. Proper logging as well as implementing some form of authentication/authorisation would be essential too.



**Note:** Swagger validation with Atlassian validator library was added later. What I wanted to do by using this library was to push the syntactic validation to the edges and let the domain focus more on the things that matter - business logic. Using such an approach should help detect various types of exceptions such as: missing required fields, fields not properly formatted (e.g. a String has the right Date format), fields are of the right type etc. After applying this strategy I was able to remove completely every null check from the domain as such checks should have been done at a higher level.
