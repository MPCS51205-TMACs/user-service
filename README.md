# User Service
This service manages data in the user context. It also authenticates users.

## Authentication
It receives a user name and password and checks for that combination in the log. If so, it creates a Jwt from the `Jwt Service` and returns.

## User
The user service is primarily REST driven. The REST controllers are located `main/.../controller`

The user service also acts as a publisher to the `user.create`, `user.activation`, `user.delete`, and `user.update` messages

## Running
It's easiest to spin up the application in the docker-compose such that it creates the Postgres and RabbitMQ dependency

## Shortcoming
If I were doing it again, I would break user and auth services into separate services. For this project, it was easiest to combine them since they were tightly coupled: authentication requires user details to construct the JWT. With more time, I would split them out