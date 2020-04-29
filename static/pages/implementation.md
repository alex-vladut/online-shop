## Implementation

I opted for using Cassandra DB for persisting the data, this was mostly due to the fact that I never worked with Cassandra before and I wanted to get an idea of how it works. I also played with a standalone version, but for this project I used CassandraUnit, which is an embedded version. That decision was made in order to make the configuration of the project easier.

I used Spring Data module for managing the access to the database. It provides also an implementation for Cassandra. I also planned to use Spring Data REST in order to automatically generate RESTful endpoints, especially for managing the products as it is more CRUDy oriented (not a lot of business logic involved), but I encountered a few problems due to the integration with Cassandra and I abandoned the idea for now. Will come back to it later and get it working.

The RESTful API endpoints for managing products and orders were implemented manually using Spring MVC Controllers. For documenting the APIs I used Spring-fox implementation of Swagger, as this philosophy of generating some of the specifications based on Spring annotations appealed to me. For details that couldn't be extracted directly from the usage of Spring Framework, I used Swagger2 specific annotations.

My testing strategy is generally to use a combination of both unit and integration/service tests. I think that unit tests are very important for testing business logic, especially when a good job was done for this business logic to be encapsulated in domain objects that require no interactions with other services. When mocking comes at play, then the unit tests will become very cumbersome and hard to maintain. For such glue code, where multiple components interact with each other, I think an integration test will add more benefits as the tests could include whole flows. BDD offers a nice approach to implement business cases, which includes also interactions with a database. Unfortunately I didn't yet get the time to implement such an example for this project, but if you are curious what it would look like please take a look at this GitHub repo https://github.com/alex-vladut/bdd-shop.

This version of the project is still missing a lot of features that would be essential for a real world project. For example, no Transaction Management was implemented. Would be hard to implement RDBMS-like transactions using a NoSQL DB, but at least some optimistic locking or conditions on save/update would be useful in order to make sure that data doesn't end up in an unpredictable state. The error handling could also be improved a lot by adding some general error handlers in order to prevent stack traces from reaching the client and also to translate domain specific exceptions to proper HTTP status codes/error messages. Proper logging as well as implementing some form of authentication/authorisation would be essential too.
