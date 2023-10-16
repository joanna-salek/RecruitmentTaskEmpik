# Recruitment Task for Empik 

## The goal of the task was to build simple REST service that fetches Github user public information, convertes them and present and GET endpoint

### Important! --> If you want to run application with PostgreSQL you need to define some env variables:
* ${DB_HOST}
* ${DB_PORT}
* ${DB_NAME}
* ${DB_USERNAME}
* ${DB_PASSWORD}

### If you wish to use other DB-Engines - appropriate configuration of application.properties file needed

* Application defines REST endpoint for user: GET /users/{login} with response as follows:

```
{
  "id": "...",
  "login": "...",
  "name": "...",
  "type": "...",
  "avatarUrl": "...",
  "createdAt": "..."
  "calculations": "..."
}
```
where appropriate fields are retrieved from Github public users: https://api.github.com/users/{login}
Only "calculations" field is not passed directly but rather it is calculated as follows: 6 / followers * (2 + public_repos)

* Application counts number of API calls for each login in the database

### Needed for further clarifications:
* Question if application should count number of successful API calls or just calls even if they will fail (for now it counts all, even failed API calls)
* Question how application should handle scenario when number of followers is 0 and it will endup in dividing by zero in calculations field (for now it throws an eception:
IllegalArgumentException("Divide by zero exception - number of followers can't be zero for calculation purposes")
