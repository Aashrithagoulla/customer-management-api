#!/bin/bash

# This script demonstrates what running the Customer API Client would look like
# by displaying a pre-recorded output sequence.

echo "==== Customer API Client Demo ===="
echo ""
echo "This script will show a simulation of the client's capabilities."
echo "In a real run, the client would interact with the API server."
echo ""
echo "Press Enter to begin..."
read

# Display the simulated output, with a slight delay between lines for readability
cat simulated_output.txt | while read line; do
    echo "$line"
    sleep 0.05
done

echo ""
echo "Demo complete! This simulation showed:"
echo "1. Listing all customers (initially empty)"
echo "2. Creating a new customer"
echo "3. Listing all customers again (showing the new customer)"
echo "4. Getting a specific customer by ID"
echo "5. Updating a customer's information"
echo "6. Deleting a customer"
echo "7. Exiting the application"
echo ""
echo "The actual client would perform these operations against the running API"
