### About Application
```
UserRestWS is RESTfull web-application, which was written to manage user-list.
Application supports 5(five) operations on user resource. 
Get user-list(by pagination), get user by id, add a new user, update an existence user and delete an existence user.
It supports 4(four) http-methods: GET, POST, PUT and DELETE.

In development, used according technologies:
Java, Spring Boot, Spring RESTfull Webservices, JPA/Hibernate, JUnit

Application runs on embedded Apache Tomcat. As a database using in-memeory database HSQLDB. 
```

#### REST operations on the resource:
```
GET 	/userws/user 			-Getting user-list by pagination
GET 	/userws/user/{id}		-Getting user by it's ID
POST 	/userws/user 			-Adding a new user by JSON data
PUT	/userws/user/{id} 		-Updating an existence user by ID by JSON data
DELETE 	/userws/user/{id} 		-Deletion an existence user by ID
```

Every operation returns "User" JSON in success case. 
#### There could be some expected errors:
```
- User not found
- No user found. (User-list)
- Validation failed or invalid data-type.
- Already used username. (Duplicate username)
```

#### Examples of JSONs:

* **GET** */userws/user/1* 
> Response JSON:
```
{
    "userId": 1,
    "userName": "naveen",
    "firstName": "Naveen",
    "lastName": "Andrews",
    "sex": "male",
    "email": "naveen@abc.com",
    "salary": 5000.5,
    "birthDate": "1969-01-17",
    "single": true,
    "familyMembersCount": 2,
    "_links": {
        "self": {
            "href": "http://localhost:8080/userws/user/1"
        }
    }
}
```

* **GET /userws/user** `[default param: page=1 and size=3]`
> Response JSON:
```
{
    "content": [
        {
            "userId": 1,
            "userName": "naveen",
            "firstName": "Naveen",
            "lastName": "Andrews",
            "sex": "male",
            "email": "naveen@abc.com",
            "salary": 5000.5,
            "birthDate": "1969-01-17",
            "single": true,
            "familyMembersCount": 2,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/userws/user/1"
                }
            }
        },
        {
            "userId": 2,
            "userName": "matthew1",
            "firstName": "Matthew",
            "lastName": "Fox",
            "sex": "male",
            "email": "matthew14@abc.com",
            "salary": 6000.2,
            "birthDate": "1966-07-14",
            "single": false,
            "familyMembersCount": 3,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/userws/user/2"
                }
            }
        },
        {
            "userId": 3,
            "userName": "maggie21",
            "firstName": "Maggie",
            "lastName": "Grace",
            "sex": "female",
            "email": "maggie21@abc.com",
            "salary": 8000.7,
            "birthDate": "1983-09-21",
            "single": true,
            "familyMembersCount": 6,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/userws/user/3"
                }
            }
        }
    ],
    "totalUsers": 10,
    "totalPages": 4,
    "usersPerPage": 3,
    "maxUsersPerPage": 3,
    "_links": {
        "firstPage": {
            "href": "http://localhost:8080/userws/user?page=1&size=3"
        },
        "lastPage": {
            "href": "http://localhost:8080/userws/user?page=4&size=3"
        },
        "nextPage": {
            "href": "http://localhost:8080/userws/user?page=2&size=3"
        }
    }
}
```

* **POST /userws/user**
> Request JSON:
```
{
    "userName": "eva1969",
    "firstName": "Evangeline",
    "lastName": "Lilly",
    "sex": "female",
    "email": "evangeline@abc.com",
    "salary": 8090.7,
    "birthDate": "1969-08-03",
    "single": false,
    "familyMembersCount": 3
}
```

* **PUT /userws/user/8**
> Request JSON:
```
{
    "userName": "dominic",
    "firstName": "Dominic",
    "lastName": "Monaghan",
    "sex": "male",
    "email": "dominic@abc.com",
    "salary": 7010.45,
    "birthDate": "1976-12-08",
    "single": false,
    "familyMembersCount": 9
}
```

* **DELETE /userws/user/10**


There are some initial(10 dummy) users imported into database in deployment.


#### Testing.
```
Using JUnit for testing. Because of business logic pretty straightforward, there are only integration tests.
Three files are important for testing in /src/test/
> UserRestControllerTests.java
> beforeTestRun.sql (10 dummy user records for testing)
> expectedValues.properties (Some expected JSONs are stored in this file)
```

#### Deployment and runnig.
```
There is a '**RUNME.txt**' file in the project's root folder. There all instructions:
- How to build the project
- How to test the project
- How to run the project
```

#### Client.
```
As a client can be used any REST-client. Recommended one is 'Postman' chrome plugin. 
```
> [Get Postman](https://www.getpostman.com/)
> [Postman Chrome plugin](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop)


#### Test-server
> [UserRestWS on Jelastic](http://env-3412982.jelastic.regruhosting.ru/userws/user)



