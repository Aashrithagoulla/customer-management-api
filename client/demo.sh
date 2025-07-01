#!/bin/bash

# This script demonstrates the Client API's functionality with preprogrammed input
# It's useful for quick demos or verification that the client is working properly

echo "==== Customer API Client Demo ===="
echo "This script will build and run the client with automatic inputs"
echo "to demonstrate the functionality."
echo ""

# Build the client
echo "Building client application..."
../mvnw clean package

# Check if build was successful
if [ $? -ne 0 ]; then
    echo "Build failed! Please check the error messages above."
    exit 1
fi

echo ""
echo "Build successful!"
echo "Running client with automatic inputs..."
echo ""

# Create a function to provide automatic input to the client
java -jar target/customer-api-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar << EOF
1

3
John
A
Doe
john.doe@example.com
555-123-4567

1

0
EOF

echo ""
echo "Demo complete! The script showed:"
echo "1. List all customers (initially may be empty)"
echo "2. Create a new customer (John A Doe)"
echo "3. List all customers again (showing the new customer)"
echo "4. Exit the application"

exit 0
