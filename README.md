# Aira
Aira is a task manager application. Main purpose of Aira is to help distributed development teams to deliver astonishing modern products. Aira is committed to agile workflow and provide all possibilities for development teams to follow agile principles in their work.

## Work In Progress
Application assumes microservice architecture. Configuration Server for cloud storing configs, Eureka as discovery manager, HashiCorp Vault for sensitive configs such as login and password for databases. PostgreSQL would be main database for the app.

<img src="https://github.com/barlo921/barlo921/blob/0037318a23d2265e8030d2161c00971ba3915a01/assets/aira/aira_architecture.jpg" width=650>

## Implementation plan

- [ ] Main projects and tasks managing functionality
- [ ] API Gateway
- [ ] Front-end: Tasks tables, sorting and filtering at view
- [ ] Users part: registration and authorization, securing through API Gateway
- [ ] Front-end: Gant charts
- [ ] Scrum and Kanban boards

## Technologies
- Java 17
- Spring Boot
- Spring Web
- Spring Data (JPA)
- Spring Security
- PostgreSQL
- Vue.js
