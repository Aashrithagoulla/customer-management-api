# Customer API

Spring Boot RESTful API for Customer Management

## Project Contents

This repository contains a complete Spring Boot application for customer management. All source code and configuration files are available in the `CustomerAPI.zip` file.

## Features

- REST controllers with proper HTTP methods
- Service layer for business logic
- Repository layer for database interaction
- Data transfer objects (DTOs) for API contracts
- Error handling with proper HTTP status codes
- Unit and integration tests
- Observability with Spring Actuator
- Containerization with Docker
- Kubernetes deployment configurations

## Key Components

- **API Endpoints**: Full CRUD operations for customer entities
- **Data Model**: Customer entity with validation
- **Error Handling**: Global exception handler with appropriate status codes
- **Client Application**: Java command-line client for API testing
- **Containerization**: Optimized Dockerfile with layered builds
- **Kubernetes**: Deployment, service and configmap manifests

## API Endpoints

- `GET /api/v1/customers` - Get all customers
- `GET /api/v1/customers/{id}` - Get a customer by ID
- `POST /api/v1/customers` - Create a new customer
- `PUT /api/v1/customers/{id}` - Update a customer
- `DELETE /api/v1/customers/{id}` - Delete a customer

## Key Project Files

The ZIP file contains all necessary files, including:

- Spring Boot application code (controllers, services, repositories)
- Model and DTO classes
- Exception handling
- Configuration files
- Unit and integration tests
- Docker and Kubernetes configuration
- Client application

## Running the Application

```bash
./mvnw spring-boot:run
```

## Running the Tests

```bash
./mvnw test
```

## Building Docker Image

```bash
docker build -t customer-api:latest .
```

## Running in Docker

```bash
docker run -p 8080:8080 customer-api:latest
```

## Running the Java Client

```bash
cd client
./run.sh
```

## Note to Reviewers

Please download and extract the `CustomerAPI.zip` file to review the full project structure and code. The project follows best practices for Spring Boot application development and includes comprehensive tests and documentation.
