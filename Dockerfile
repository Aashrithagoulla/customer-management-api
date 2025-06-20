FROM eclipse-temurin:17-jdk-alpine as build
LABEL maintainer="john.smith@example.com"
LABEL version="1.0"
LABEL description="Customer API for managing customer information"

# Set working directory
WORKDIR /workspace/app

# Copy maven executable, settings, and project files
# Using separate COPY commands for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies first (cached layer if pom.xml doesn't change)
RUN chmod +x ./mvnw && \
    ./mvnw dependency:go-offline

# Now copy source code
COPY src src

# Build the application (skipping tests as they've run in CI)
RUN ./mvnw install -DskipTests

# Extract the built JAR (layered approach)
RUN mkdir -p target/dependency && \
    (cd target/dependency; jar -xf ../*.jar)

# Production stage
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

# Copy dependencies from build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set environment variables for application configuration (can be overridden)
ENV SERVER_PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:customerdb
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=password

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.example.customerapi.CustomerApiApplication"]
