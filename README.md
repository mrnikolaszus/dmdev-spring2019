Tthis project does not carry any significant functional payload; it was created solely for training and testing purposes with the Spring Framework.

Key Topics Covered
This project explores various key topics in Spring Framework, including:

XML-based Configuration: The project showcases XML-based configuration alongside other methods of configuring Spring applications.
BeanFactoryPostProcessor (BFPP): Utilization of BeanFactoryPostProcessor for customizing bean definitions before they are registered with the BeanFactory.
BeanPostProcessor (BPP): Implementation of BeanPostProcessor for performing custom initialization and destruction logic for beans.
Scopes: Demonstration of different bean scopes such as singleton and prototype.
Event Listeners: Implementation of event listeners to handle application events.
Data JPA: Integration with Spring Data JPA for simplified data access.
Thymeleaf: Usage of Thymeleaf as the template engine for server-side HTML rendering.
Data JPA Repositories: Utilization of Data JPA repositories for database interaction.
Database Migrations: Implementation of database migrations for managing database schema changes.
Spring Boot Tests: Writing tests for Spring Boot applications using the testing framework provided by Spring Boot.
REST: Development of RESTful APIs for communication between client and server.
Security Starter: Configuration of security features using Spring Security starter.
AOP: Application of Aspect-Oriented Programming for cross-cutting concerns such as logging and security.

findAll: Retrieves a list of users based on specified filters and pagination parameters.
findById: Retrieves user details by ID and populates the model with user information, roles, and associated companies.
registration: Displays a user registration form.
create: Handles user creation, performing validation and redirecting to the registration page if errors occur.
update: Updates user information based on the provided ID.
delete: Deletes a user by ID.
