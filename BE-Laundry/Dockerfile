# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the built jar file into the container
COPY target/BE-Laundry-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the application with the 'docker' Spring profile
CMD ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Jakarta", "-jar", "app.jar"]
