# Customer API

Spring Boot RESTful API for Customer Management

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

## API Endpoints

- `GET /api/v1/customers` - Get all customers
- `GET /api/v1/customers/{id}` - Get a customer by ID
- `POST /api/v1/customers` - Create a new customer
- `PUT /api/v1/customers/{id}` - Update a customer
- `DELETE /api/v1/customers/{id}` - Delete a customer

## Running the Application

```bash
./mvnw spring-boot:run
```

## Running the Tests

```bash
./mvnw test
```
