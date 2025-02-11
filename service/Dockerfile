# Use a lightweight OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built JAR from the local machine to the container
COPY target/service-0.0.1-SNAPSHOT.jar /app/application.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "/app/application.jar"]
