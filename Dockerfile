# ==========================================
# ETAPA 1: COMPILAR CON MAVEN
# ==========================================
FROM maven:3.9-eclipse-temurin-21 AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y las dependencias
COPY pom.xml .

# Descargar dependencias (se cachean si no cambian)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto y generar el JAR
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: EJECUTAR CON JRE
# ==========================================
FROM eclipse-temurin:21-jre

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Render usa el 8080 por defecto)
EXPOSE 8080

# Ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]