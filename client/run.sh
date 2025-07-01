#!/bin/bash

# Build the client application
echo "Building client application..."
cd "$(dirname "$0")"
../mvnw clean package

# Check if build was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "Build successful!"
    echo "Running client application..."
    echo ""
    
    # Run the client application with dependencies
    java -jar target/customer-api-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar
else
    echo "Build failed! Please check the error messages above."
fi
