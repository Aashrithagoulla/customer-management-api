# Customer API

A comprehensive Spring Boot RESTful API for Customer Management designed with best practices and modern technologies.

## Project Overview

This repository demonstrates a production-ready Customer Management API built with Spring Boot. The system provides a complete solution for managing customer data with robust validation, proper error handling, and comprehensive test coverage.

## Project Contents

This repository contains a complete Spring Boot application for customer management with full CRUD operations, validation, error handling, and observability features. The project includes:

- RESTful API endpoints
- Service and repository layers
- H2 in-memory database
- Comprehensive tests
- Docker containerization
- Kubernetes deployment manifests
- CI/CD workflow configuration

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

## Client Application

The repository includes a Java command-line client application that demonstrates how to interact with the Customer API. This client provides a simple interface for:

- Creating new customers
- Retrieving customer information
- Updating existing customers
- Deleting customers

The client application is located in the `client` directory and can be run with:

```bash
cd client
./run.sh
```

For a guided demonstration of the client's capabilities, use the demo script:

```bash
cd client
./show_demo.sh
```

## Technology Stack

- **Backend**: Spring Boot 3.5.x, Java 17
- **Database**: H2 (in-memory for demo purposes)
- **API Documentation**: Spring REST Docs
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Build Tool**: Maven
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **CI/CD**: GitHub Actions

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
