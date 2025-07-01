# Customer API Client

This is a command-line client application that consumes the Customer API.

## For Interviewers - Quick Start

```bash
# Step 1: Make sure the API is running first
# In the main project directory:
./mvnw spring-boot:run

# Step 2: In a new terminal, run the client
cd client
./run.sh
```

### Simulated Demo (Recommended for Interviewers)

To see a simulation of what the client looks like in action:

```bash
cd client
./show_demo.sh
```

This will display a pre-recorded simulation showing all the client's capabilities without requiring the API to be running.

### Automated Demo

If you want to run the actual client with automated input (requires API running):

```bash
cd client
./demo.sh
```

This will automatically perform a sequence of operations:
1. List all customers
2. Create a new customer
3. List all customers again (showing the newly created customer)
4. Exit

## Prerequisites

- Java JDK 17 or later
- Maven 3.6+ (not needed if using the included Maven wrapper)

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
