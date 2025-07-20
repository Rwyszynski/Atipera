# GitHub Repo Fetcher

A **Spring Boot project** that fetches **public repositories of the GitHub user `Rwyszynski` along with their branches**, skipping forks, and returns the result in clean JSON format.

## Features

✅ Fetches all public repositories of `Rwyszynski`
✅ Fetches branch lists for each repository
✅ Filters out forked repositories
✅ Exception handling (`UserNotFoundException`, HTTP errors)
✅ Parallel fetching of branches for speed optimization

---

## Tech Stack

* Java 
* Spring Boot
* Spring Web (RestTemplate)
* Jackson
* Lombok
* Log4j2

---

## Endpoints

### GET `/repo/Rwyszynski`

Fetches **all public repositories of the user `Rwyszynski` with branches**, skipping forks.

**Example:**

```http
GET http://localhost:8080/repo/Rwyszynski
```

**Sample Response:**

```json
{
  "repoList": [
    {
      "name": "Hello-World",
      "owner": {
        "login": "Rwyszynski",
        "id": 583231
      },
      "branches": [
        {
          "name": "master",
          "sha": "e9e8f5c..."
        },
        {
          "name": "dev",
          "sha": "b9e8f8c..."
        }
      ]
    }
  ]
}
```

---

## Sequence of Execution

1️⃣ User calls `GET /repo/Rwyszynski`
2️⃣ `GitHubController` calls `GithubService.getAllRepos("Rwyszynski")`
3️⃣ `GithubService`:

* Calls `fetchAllRepos("Rwyszynski")`, which:

  * Uses `GithubProxy.getAllRepos("Rwyszynski")` to call GitHub API `/users/Rwyszynski/repos`.
  * Maps JSON response to `GitHubResult` list and filters out forks.
* For each repository, in parallel:

  * Calls `fetchAllBranches("Rwyszynski", repoName)`:

    * Uses `GithubProxy.getAllBranches("Rwyszynski", repoName)` to call GitHub API `/repos/Rwyszynski/{repoName}/branches`.
    * Maps JSON to `Branch` list.
  * Constructs `FullObject` with repo name, owner, and branches.
* Collects all `FullObject` into `GitHubAllRepoList`.
  4️⃣ Returns `GitHubAllRepoList` as JSON to the user.

---

## How to Run Locally

1️⃣ Clone the repository:

```bash
git clone https://github.com/Rwyszynski/github-repo-fetcher.git
cd github-repo-fetcher
```

2️⃣ Run the application:

```bash
./mvnw spring-boot:run
```

3️⃣ Call the endpoint:

```bash
curl http://localhost:8080/repo/Rwyszynski
```

---

## Project Structure

```
src/main/java/com/zadanie2
    ├── controller
    ├── dto
    ├── entity
    ├── exception
    ├── proxy
    └── service
```

---

## Available Endpoints

### Controller: `GitHubController`

| HTTP Method | Endpoint       | Description                                                                                      |
|-------------|----------------|------------------------------------------------------------------------------------------------|
| GET         | `/repo/{name}` | Retrieves all public repositories (excluding forks) for the GitHub user `{name}`, along with their branches. Returns a structured JSON with repository and branch information. |

---
