# Etapa 1: Compilación con Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre
COPY --from=build /target/Empleo_Spring-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]