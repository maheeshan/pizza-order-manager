FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY ./pom.xml /app

COPY ./src /app/src

RUN mvn clean package -DskipTests -Dspring.profiles.active=build

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/pizza-order-manager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
