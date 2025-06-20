FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the client application files
COPY . .

# Install Maven
RUN apk add --no-cache maven

# Build the client application
RUN mvn clean package

# Set the entry point
ENTRYPOINT ["java", "-jar", "target/customer-api-client-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
