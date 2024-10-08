# K-Learn Backend

## Run Service: 
```
mvn spring-boot:run
```

## API:
### Auth:
- POST ```http://localhost:8080/api/auth/register ```
  - Target: This API allows users to register for an account.
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
  - Target: This API allows users to log in to their account using either their username or email along with their password.
  - Body:
  ```
  {
    "usernameOrEmail": "",
    "password": ""
  }
  ```

### Course:
-  GET ```http://localhost:8080/api/course ```
    - Target: This API retrieves a list of all available courses in the system.

- POST ```http://localhost:8080/api/course/create```
  - Target: Target: This API allows authorized users (such as admins or content managers) to create a new course. It requires the course name, level, description, and price as input.
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
  - Target: This API retrieves a list of all available grammar lessons across all courses.

- GET ```http://localhost:8080/api/grammar/{courseId}```
  - Target: This API retrieves all grammar lessons associated with a specific course based on the provided course ID.

- POST ```http://localhost:8080/api/grammar/create```
  - Target: This API allows authorized users to create a new grammar lesson. The lesson includes the grammar name, explanation, examples, lesson number, and associated course ID.
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
  - Target: This API retrieves a list of all vocabulary topics available across all courses.

- GET ```http://localhost:8080/api/vocabulary_topic/{courseId}```
  - Target: This API retrieves all vocabulary topics associated with a specific course based on the provided course ID.

- POST ```http://localhost:8080/api/vocabulary_topic/create```
  - Target: This API allows authorized users to create a new vocabulary topic, which requires the topic name, description, image, and course ID.
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
  - Target: This API allows authenticated users to retrieve their own profile information. It requires a valid Bearer token for authorization.
  - Authorization: Bearer Token 

## Vocabulary:
- GET ```http://localhost:8080/api/vocabulary/{topic_id}```
  - Target: This API retrieves a list of all vocabulary words under a specific vocabulary topic
- POST ```http://localhost:8080/api/vocabulary/post```
  - Target: This API allows authorized users to add a new vocabulary word to a specific vocabulary topic. The request requires the word, its definition, transcription, image, and associated topic ID.
  - Body:
  ```
  "word": "",
  "definition": "",
  "transcription": "",
  "image": "",
  "topic_id": "" 
  ```
- GET ``` http://localhost:8080/api/vocabulary/count/{topic_id}```
  - Target: This API returns the number of vocabulary words associated with a specific topic

### Vocabulary Progress
- GET ```http://localhost:8080/api/vocabulary_progress/topic/{topic_id}```
  - Target: This API retrieves the vocabulary progress of the currently authenticated user for a specific vocabulary topic. The request must include the Bearer token for authentication and authorization.
  - Authorization: Bearer Token