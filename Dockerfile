# Stage 1: Build the application using Maven
FROM maven:3.9.3 AS build
WORKDIR /app
ARG CONTAINER_PORT
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X


FROM openjdk:19
COPY --from=build /app/target/*.jar Book-Store-Management-back.jar
EXPOSE ${CONTAINER_PORT}
ENTRYPOINT ["java", "-jar", "Book-Store-Management-back.jar"]