FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the application JAR
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]