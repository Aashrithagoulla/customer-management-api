# Customer API Client

This is a command-line client application that consumes the Customer API.

## Prerequisites

- Java JDK 17 or later
- Maven 3.6+

## Building the Client

```bash
# On Unix/Linux/macOS
cd client
./run.sh

# On Windows
cd client
run.bat
```

## Using the Client

The client application provides a menu-driven interface to interact with the Customer API:

1. List All Customers - Retrieves and displays all customers
2. Get Customer by ID - Retrieves a specific customer by UUID
3. Create Customer - Creates a new customer
4. Update Customer - Updates an existing customer
5. Delete Customer - Deletes a customer by ID
0. Exit - Quits the application

Make sure the Customer API server is running before using this client application.

## Configuration

By default, the client connects to the API at `http://localhost:8080/api/v1/customers`.

To change the API URL, modify the `BASE_URL` constant in `CustomerApiClient.java`.
