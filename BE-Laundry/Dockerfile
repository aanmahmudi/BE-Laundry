# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/BE-Laundry-0.0.1-SNAPSHOT.jar /app/BE-Laundry-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run application when the container launches
CMD [ "java", "-jar","BE-Laundry-0.0.1-SNAPSHOT.jar" ]