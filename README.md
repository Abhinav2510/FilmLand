# :movie_camera: FilmLand
<a href="https://foojay.io/works-with-openjdk"><img align="right" src="https://github.com/foojayio/badges/raw/main/works_with_openjdk/Works-with-OpenJDK.png" width="100"></a>


![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)


## Description:
The Filmland API Backend allows performing following operation. Equipped with :closed_lock_with_key:	 `Stateless REST JWT based Bearer token` authentication
- Get all available categories for subscription in FilmLand
- Subscribe to category
- Share your subscription
- Create User 
- Login with user details

## :shield:	 Code coverage
![image](https://user-images.githubusercontent.com/14979620/173673166-90dd2d18-3219-4868-a275-cad94fe8699a.png)


## :balance_scale:	Assumptions :
- Validations for users
  - username and password should be at least 8 characters long.
- Validations for Subscriptions
  - `email` should be same as logged in users email
  - `customer` can be left empty 
  - Only a person who subscribed intially can share that subscription with other user
  - One user can subscribe to any category only once

## :hammer_and_wrench:	Tech-Stack
![Java](https://img.shields.io/badge/-Java-000?&logo=Java&logoColor=007396)
![Docker](https://img.shields.io/badge/-Docker-000?&logo=Docker)
![Spring](https://img.shields.io/badge/-Spring-000?&logo=Spring)

- Java 8 
- Spring-Boot
- JPA
- In-Memory Database H2
- Maven
- Git bash

## :memo: Steps to run the application
- Checkout the code / Download from git repo()
- checkout : open git bash and run command `git clone https://github.com/Abhinav2510/FilmLand.git`
- Option 1: Maven way of running
  - open command prompt(cmd) or terminal on Mac
  - navigate to the project folder
  - run command `mvn clean install`
  - once its successfully build run command `mvn spring-boot: run`


Now application is up and running on http://localhost:8080

## :grey_question:	How to use this service
- Open the URL in your browser : http://localhost:8080
- User will see a swagger page with all the defined specs of the service.
- There will have 2 Tags you can see.


### 1. user-controller
#### Description:
- Endpoint 1: `POST /users/signup`
  - Allows creation of user
- Endpoint 2: `POST /users/signin`
  - Allows user to login
  - On providing correct credential in request the response provides Bearer token in header which can be used for calling further API

### 2. film-category-controller
#### Description:
- All the below endpoints are secured with stateless JWT authentication.
- All request to below Endpoints should contain custom header `authorization` with value containing `Bearer {JWT}`
- Endpoint 1:`POST /categories/`
    - subscribe to category or share subscription with another user
- Endpoint 2: `GET /catefories/`
   - get all the available categories and subscribed available for subscriptions

### :test_tube: Testing using Swagger UI

####Running application
- Run application using `mvn spring-boot: run` or `java -jar /target/recipe-0.0.1-SNAPSHOT.jar`
- Navigate to http://localhost:8080
- ![image](https://user-images.githubusercontent.com/14979620/173673595-9b92f81f-366d-4d60-a12c-f534f71bfb1c.png)

#### Authenticating for using Recipe API
- under `user-controller` tab you can create user or use already created user to authenticate 
- to create user use `/users/signup` endpoint
- to Use default user for application use below JSON object for `user/signin`
```
  {
  "email":"user@test.com",
  "password":"password1234"
  } 
```
- It will return response header `authorization` with JWT token
- ![Screenshot 2021-09-23 at 00 35 49](https://user-images.githubusercontent.com/14979620/134431617-56e89f8d-5c41-4daf-bf00-cae108677c86.png)
- Copy the value in `authorization` header
- On top left corner click on Authorize button and enter copied value
- ![image](https://user-images.githubusercontent.com/14979620/173674122-a018aa4c-6dfa-43b6-9df6-18f72184d066.png)


Now you should be able to call all the APIs without needing to specify `authorization` header manually 
