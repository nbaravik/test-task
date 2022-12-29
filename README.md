# Test Task for Arua

Simple REST API (JSON HTTP API) to manage users stored in MongoDB database.

Requried environment:

- Java 11

- Spring boot 3.0.0

- Database: Mongo5.0 in Docker

- Protocol: HTTP, port 80

Three types of endpoints:

  - GET /users/id - get user by ID
  - PUT /users - create user
  - PATCH /users/id - update user status

## Getting Started


```
git clone https://github.com/nbaravik/test-task.git
docker-compose up -d
mvn clean install
mvn exec:java -Dexec.mainClass="com.github.nbaravik.springuser.UserApplication"
```
application.property:
```
server.port=80

spring.data.mongodb.auto-index-creation=true
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=arua
```
docker-compose.yml:
```
version: "3.8"

services:
mongodb:
image: mongo:5
container_name: mongo-container
ports:
- "27017:27017"
```

## How to verify

### 1. Create user
```
Postman: POST localhost:80/users

Body : {
    "name": "Somebody",
    "email": "somebody@gmail.com",
    "uri": "https://www.istockphoto.com/photos/somepic",
    "status": "OFFLINE"
}
```
Successful result:
```
201 CREATED
{
    "id": "63ac20a230c4d9337cc1e524"
}
```
Unsuccessful result:
```
400 BAD REQUEST
{
    "date": "2022-12-28T19:06:24.4265766",
    "violations": [
        {
            "field": "createUser.user.email",
            "message": "must be a well-formed"
        },
        {
            "field": "createUser.user.status",
            "message": "must be any of enum {ONLINE, OFFLINE}"
        },
        {
            "field": "createUser.user.name",
            "message": "must not be blank"
        }
    ]
}
```
### 2. Get user by ID
```
Postman: GET localhost:80/users/63ac20a230c4d9337cc1e524
```
Successful result:
```
200 OK
{
    "name": "Somebody",
    "email": "somebody@gmail.com",
    "uri": "https://www.istockphoto.com/photos/somepic",
    "status": "OFFLINE"
}
```
Unsuccessful results:
```
404 NOT FOUND
{
"date": "2022-12-28T19:13:52.3738442",
"message": "User with ID=63ac20a230c4d does not exist in the DB."
}

400 BAD REQUEST
{
    "date": "2022-12-28T19:33:14.2770035",
    "violations": [
        {
            "field": "getUserById.id",
            "message": "user name must not be blank"
        }
    ]
}
```
### 3. Modify user status

```
Postman: PATCH localhost:80/users/63ac20a230c4d9337cc1e524?status=ONLINE
```
Successful result:
```
200 OK
{
    "id": "63ac20a230c4d9337cc1e524",
    "status": "ONLINE",
    "previousStatus": "OFFLINE"
}
```
Unsuccessful results:
```
404 NOT FOUND
{
"date": "2022-12-28T19:13:52.3738442",
"message": "User with ID=63ac20a230c4d does not exist in the DB."
}

400 BAD REQUEST
{
    "date": "2022-12-28T19:31:53.3326874",
    "violations": [
        {
            "field": "modifyStatus.newStatus",
            "message": "must be any of enum {ONLINE, OFFLINE}"
        }
    ]
}
```
