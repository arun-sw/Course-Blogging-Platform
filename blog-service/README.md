# Blog Service

Run the service with `./mvnw.cmd spring-boot:run`. It starts on port `8016` and uses the separate MySQL database `blog_db`. Set `BLOG_DB_PASSWORD` before starting if the MySQL `root` account has a password.

Useful Postman request bodies:

```json
POST /categories
{ "name": "Spring Boot", "description": "Spring Boot articles" }

POST /tags
{ "name": "Backend" }

POST /blogs
{ "userId": 1, "categoryId": 1, "title": "My first post", "content": "Hello blog" }

POST /blogs/1/tags
{ "tagIds": [1] }

PUT /blogs/1
{ "userId": 1, "categoryId": 1, "title": "New title", "content": "New content" }
```

Blog deletion supplies its basic ownership check as a query parameter: `DELETE /blogs/1?userId=1`.
