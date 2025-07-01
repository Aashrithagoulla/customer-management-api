# Client Setup Checklist for Interviewers

This document provides steps to verify the Customer API Client is working correctly.

## RECOMMENDED: View Client Simulation (No Setup Required)

For the quickest way to see the client's capabilities:

```bash
cd client
./show_demo.sh
```

This shows a pre-recorded simulation of all client operations without requiring any setup or the API to be running.

## Pre-Run Checklist (For Live Demo)

1. Ensure the Customer API server is running:
   ```bash
   # In the main project directory
   ./mvnw spring-boot:run
   ```
   
   The API should start and be accessible at http://localhost:8080

2. Ensure you have the following prerequisites:
   - Java JDK 17+ (verify with `java -version`)
   - Maven is included via wrapper (mvnw)

3. Verify directory structure:
   ```
   client/
   ├── CustomerApiClient.java       # Original location (for reference)
   ├── pom.xml                      # Maven project file
   ├── README.md                    # Instructions
   ├── run.sh                       # Script to build and run
   ├── demo.sh                      # Automated demo script with pre-defined inputs
   ├── show_demo.sh                 # Script to show simulated output (recommended)
   ├── simulated_output.txt         # Pre-recorded client output
   └── src/                         # Maven source directory
       └── main/
           └── java/
               └── com/
                   └── example/
                       └── client/
                           └── CustomerApiClient.java   # Compiled location
   ```

## Running the Client

There are three ways to see the client:

### Method 1: Simulated Demo (RECOMMENDED)

```bash
cd client
./show_demo.sh
```

This shows a pre-recorded simulation of the client in action. 
* Does NOT require the API to be running
* Shows ALL client functionality
* No build issues or connectivity problems
* Perfect for a quick review of capabilities

### Method 2: Interactive Mode

```bash
cd client
./run.sh
```

This will build the application and start an interactive shell where you can:
1. List all customers
2. Retrieve a customer by ID
3. Create a new customer
4. Update an existing customer
5. Delete a customer

NOTE: Requires API server to be running first.

### Method 3: Automated Demo

```bash
cd client
./demo.sh
```

This will automatically perform a sequence of operations to demonstrate the client's capabilities.

NOTE: Requires API server to be running first.

## Troubleshooting

If you encounter issues:

1. **Connection refused error:**
   - Make sure the API server is running and accessible at http://localhost:8080
   - Try accessing http://localhost:8080/api/v1/customers in a browser

2. **Build failure:**
   - Check the Maven output for specific errors
   - Ensure the pom.xml is correctly formatted

3. **Permission issues:**
   - If you cannot execute the scripts, run: `chmod +x *.sh`
   
4. **Other issues:**
   - Add `export JAVA_HOME=/path/to/your/jdk` before running if Java home isn't set
   - Try running Java directly: `java -jar target/customer-api-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
