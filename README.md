![Logo](https://user-images.githubusercontent.com/74038190/212741999-016fddbd-617a-4448-8042-0ecf907aea25.gif)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=denpoo1_Atipera-Intership-Task&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=denpoo1_Atipera-Intership-Task)

# Atipera Intership TASK

## SWAGGER URI: [http://172.205.85.35:8080/swagger-ui/index.html](http://172.205.85.35:8080/swagger-ui/index.html)

# GitHub Repository Listing API

## Overview

This application provides an API to list GitHub repositories of a given user, including details about each repository's branches and their latest commits. The application is built using Java 21 and Spring Boot 3, but you can also use Kotlin + Spring Boot 3 or Quarkus 3 based on your preference.

## Main handler logic
This logic is made due to best practices according to the github api documentation. I am attaching a list of documentation that I used to make this logic:
- https://docs.github.com/en/rest/using-the-rest-api/rate-limits-for-the-rest-api?apiVersion=2022-11-28
- https://docs.github.com/en/rest/using-the-rest-api/troubleshooting-the-rest-api?apiVersion=2022-11-28
- https://docs.github.com/en/rest/using-the-rest-api/best-practices-for-using-the-rest-api?apiVersion=2022-11-28

[![dnBjCyG.md.png](https://iili.io/dnBjCyG.md.png)](https://freeimage.host/i/dnBjCyG)

## Pipeline logic
[![dnBSm8B.md.png](https://iili.io/dnBSm8B.md.png)](https://freeimage.host/i/dnBSm8B)

## Requirements

- Java 21 / Kotlin + Spring Boot 3 / Quarkus 3
- A working knowledge of GitHub's REST API
- Basic understanding of RESTful services and JSON responses

## API Endpoints

### List User Repositories

**Endpoint:** `GET /repositories`

**Description:** Lists all repositories of a given GitHub user that are not forks.

**Query Parameters:**
- `username`: The GitHub username for which repositories are to be listed.

**Request Headers:**
- `Accept: application/json`

**Response:**
- Status code `200 OK` if the user exists and has repositories.
- Status code `404 Not Found` if the user does not exist.

**Response Body Example:**
```json
{
  "repositories": [
    {
      "repository_name": "repo1",
      "owner_login": "owner1",
      "branches": [
        {
          "branch_name": "main",
          "last_commit_sha": "abc123"
        },
        {
          "branch_name": "dev",
          "last_commit_sha": "def456"
        }
      ]
    },
    {
      "repository_name": "repo2",
      "owner_login": "owner1",
      "branches": [
        {
          "branch_name": "main",
          "last_commit_sha": "ghi789"
        }
      ]
    }
  ]
}
```

# Error Handling

The application uses global exception handling to provide meaningful error responses. Here is a summary of how errors are handled:


### Too Many Requests (HTTP 429)
Description: This error occurs when the rate limit for API requests is exceeded.
#### Response Example:
```json
    {
      "status": 429,
      "message": "Too Many Requests"
    }
```


### Not Found (HTTP 404)
Description: This error occurs when the requested resource (e.g., a GitHub repository or user) is not found.
#### Response Example:
```json
    {
      "status": 404,
      "message": "Not Found"
    }
```

### Internal Server Error (HTTP 500)
Description: This error occurs when there is an internal server error while processing the request.
#### Response Example:
```json
    {
      "status": 500,
      "message": "Internal Server Error"
    }
```

### Generic Feign Exception
Description: This handles any other Feign exceptions that may occur.
#### Response Example:

```json
        {
          "status": 500,
          "message": "An error occurred"
        }
```
Custom Exceptions

### Rate Limit Exception (HTTP 403)
Description: This error occurs when the user exceeds the rate limit for a specific resource.
#### Response Example:
```json
    {
      "status": 403,
      "message": "Rate limit exceeded"
    }
```

### Unauthorized Exception (HTTP 401)
Description: This error occurs when authentication credentials are invalid or missing.
#### Response Example:
```json
    {
      "status": 401,
      "message": "Unauthorized"
    }
```

### Not Found Exception (HTTP 404)
Description: This error occurs when the requested resource or user is not found.
#### Response Example:
```json
{
  "status": 404,
  "message": "User not found"
}
```

