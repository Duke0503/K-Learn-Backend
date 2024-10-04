# K-Learn Backend

## Run Service: 
```
mvn spring-boot:run
```

## API:
### Auth:
- POST ```http://localhost:8080/api/auth/register ```
  - Body:
  ```
    {
      "firstname": "",
      "lastname": "",
      "email": "",
      "username": "",
      "dob": "",
      "gender": "",
      "password": "",
      "re_password": ""
    }
  ```
- POST ```http://localhost:8080/api/auth/login ```
  - Body:
  ```
  {
    "usernameOrEmail": "",
    "password": ""
  }
  ```

### Course:
-  GET ```http://localhost:8080/api/course ```
    - Authorization: Bearer Token

- POST ```http://localhost:8080/api/course/create```
  - Authorization: Bearer Token
  - Body: 
  ```
  {
    "course_name": "",
    "course_level": "",
    "course_description": "",
    "course_price": ""
  }
  ```

### Grammar:
- GET ```http://localhost:8080/api/grammar```
  - Authorization: Bearer Token

- GET ```http://localhost:8080/api/grammar/{courseId}```
  - Authorization: Bearer Token

- POST ```http://localhost:8080/api/grammar/create```
  - Authorization: Bearer Token
  - Body: 
  ```
  {
    "grammar_name": "",
    "explanation": "",
    "example": "",
    "lesson_number": "",
    "course_id": ""
  }
  ```

### Vocabulay Topic:
- GET ```http://localhost:8080/api/vocabulary_topic```
  - Authorization: Bearer Token

- GET ```http://localhost:8080/api/vocabulary_topic/{courseId}```
  - Authorization: Bearer Token

- POST ```http://localhost:8080/api/vocabulary_topic/create```
  - Authorization: Bearer Token
  - Body: 
  ```
  {
    "topic_name": "",
    "topic_description": "",
    "topic_image": "",
    "course_id": ""
  }
  ```
### User:
- GET ```http://localhost:8080/api/user/profile```
  - Authorization: Bearer Token 