Here’s the reformatted markdown file with improved structure and readability:

---

# Sentinel IAM API

## Overview

The Sentinel IAM API provides endpoints for managing application onboarding and user provisioning in an Identity and Access Management (IAM) system. This repository is designed to be easy to set up and use, enabling developers to onboard applications and users efficiently.

---

## Features

- Upload CSV files to onboard users into applications.
- Retrieve all applications with metadata, including user count.
- Designed with Spring Boot, Gradle, and Docker for easy deployment.

---

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java 21**: Install Java from [AdoptOpenJDK](https://adoptopenjdk.net/).
- **Gradle**: Install Gradle from [Gradle's official site](https://gradle.org/install/).
- **Docker**: Install Docker from [Docker's official site](https://www.docker.com/).
- **Docker Compose**: Install Docker Compose from the [Docker Compose Install Guide](https://docs.docker.com/compose/install/).

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Beyond-Boilerplate/sentinel-iam-api.git
cd sentinel-iam-api
```

### 2. Build the Project

Run the following command to build the application:

```bash
./gradlew clean build
```

This will generate the JAR file under `build/libs/`.

### 3. Start the Application with Docker Compose

To run the database and the application together using Docker Compose, execute:

```bash
docker-compose up --build
```

This command will:
- Start a PostgreSQL database container.
- Start the Sentinel IAM API as a Docker container.

To stop the services, run:

```bash
docker-compose down
```

### 4. Verify the API is Running

Once the app is up, check the health status:

```bash
curl http://localhost:8080/actuator/health
```

You should receive a response similar to:

```json
{
  "status": "UP"
}
```

---

## Using the API with Postman

### 1. Import Postman Collection

A Postman collection is available under `resources/postman-collection.json`. Follow these steps to import it into Postman:

1. Open Postman.
2. Click **Import**.
3. Select the `resources/postman-coll.json` file.
4. Click **Import**.

### 2. Test Endpoints

Once imported, update the `baseUrl` variable in Postman to:

```
http://localhost:8080
```

Then, execute the API requests such as:
- Get all applications.
- Upload a CSV file.

---

## Additional Notes

- Ensure Docker Desktop is running before starting the application.
- If the database doesn't start correctly, remove old containers and volumes:

  ```bash
  docker-compose down -v
  ```

- Logs can be viewed using:

  ```bash
  docker-compose logs -f
  ```

---

## Contributing

Contributions are welcome! Please submit a pull request or open an issue if you encounter any problems.

---

## License

This project is licensed under the **MIT License**.

---

This reformatted version improves readability and organization, making it easier for developers to follow the setup and usage instructions.