# Empleo Spring — Sistema de Gestión de Empleos

Aplicación web desarrollada con **Spring Boot**, **Spring MVC**, **Spring Data JPA** 
y **Thymeleaf** para la gestión de **Usuarios** y **Empleos**. Implementa arquitectura 
por capas, autenticación, CRUD completo, reportes parametrizados, recuperación de contraseña 
por correo y despliegue en la nube.

**Autor:** Steven Mestra
**Asignatura:** Desarrollo Web — Unidad 2
**Ejercicio asignado:** N.º 15 — Empleo.

---

##  Propósito

Desarrollar y desplegar una aplicación web con Spring Boot, Spring MVC, Spring Data JPA y Thymeleaf, evidenciando la separación de responsabilidades entre controladores, servicios, repositorios, entidades y vistas; así como el manejo de formularios HTML, persistencia, autenticación, operaciones CRUD, consultas/reportes parametrizados y recuperación de contraseña por correo.

---

## Enfoque tecnológico

| Componente | Tecnología |
| :--- | :--- |
| Framework principal | Spring Boot 3.3.5 |
| Modelo web | Spring MVC (vistas renderizadas en el servidor) |
| Motor de plantillas | Thymeleaf |
| Persistencia | Spring Data JPA / Hibernate 6 |
| Base de datos | PostgreSQL (Render) |
| Seguridad | Spring Security 6 + BCrypt |
| Correo | Spring Boot Starter Mail (Gmail SMTP) |
| Java | 21 |
| Build | Maven (con Maven Wrapper) |
| Despliegue | Render (Docker) |

---

## Flujo completo de una petición:

1. El navegador solicita una URL (ej. `GET /usuarios`).
2. Spring MVC la enruta al `UsuarioController`.
3. El Controller llama al `UsuarioService`.
4. El Service consulta el `UsuarioRepository`.
5. El Repository ejecuta la consulta SQL en PostgreSQL.
6. El resultado vuelve al Service → Controller.
7. El Controller agrega los datos al `Model` y retorna el nombre de la plantilla Thymeleaf.
8. Thymeleaf renderiza el HTML con los datos del `Model`.
9. El navegador muestra la página.

---

## Estructura del proyecto

```
Empleo_Spring/
├── .mvn/                                # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/com/unicartagena/edu/co/Empleo_Spring/
│   │   │   ├── EmpleoSpringApplication.java     # Clase principal
│   │   │   ├── Config/
│   │   │   │   ├── PasswordConfig.java          # Bean de BCryptPasswordEncoder
│   │   │   │   └── SecurityConfig.java          # Configuración de Spring Security
│   │   │   ├── Controller/
│   │   │   │   ├── AuthController.java          # Login, recuperación, healthz
│   │   │   │   ├── UsuarioController.java       # CRUD y reportes de Usuario
│   │   │   │   └── EmpleoController.java        # CRUD y reportes de Empleo
│   │   │   ├── Entity/
│   │   │   │   ├── Usuario.java                 # Entidad Usuario
│   │   │   │   └── Empleo.java                  # Entidad Empleo
│   │   │   ├── Repository/
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── EmpleoRepository.java
│   │   │   └── Service/
│   │   │       ├── EmailService.java
│   │   │       ├── EmpleoService.java
│   │   │       ├── UsuarioService.java
│   │   │       └── Impl/
│   │   │           ├── EmailServiceImpl.java
│   │   │           ├── EmpleoServiceImpl.java
│   │   │           └── UsuarioServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties            # Configuración
│   │       └── templates/
│   │           ├── index.html
│   │           ├── auth/
│   │           │   ├── login.html
│   │           │   └── recuperar.html
│   │           ├── usuarios/
│   │           │   ├── lista.html
│   │           │   ├── formulario.html
│   │           │   ├── ver.html
│   │           │   ├── reporte_rol.html
│   │           │   └── reporte_nombre.html
│   │           └── empleos/
│   │               ├── lista.html
│   │               ├── formulario.html
│   │               ├── ver.html
│   │               ├── reporte_categoria.html
│   │               └── reporte_empresa.html
│   └── test/
├── Dockerfile                           # Imagen Docker multi-etapa
├── pom.xml                              # Dependencias Maven
├── mvnw                                 # Maven Wrapper (Unix)
├── mvnw.cmd                             # Maven Wrapper (Windows)
└── README.md                            # Este archivo
```

---

## Requisitos previos

- **Java 21** (JDK)
- **Maven 3.9+** (o usar el wrapper incluido `./mvnw`)
- **PostgreSQL 14+** (o cuenta en Render)
- **Git**
- **Navegador web moderno** (Chrome, Firefox, Edge)

Verifica las versiones:

```bash
java -version       # Debe ser 21
./mvnw -version     # Maven 3.9+
git --version
```

---

## Base de datos

### Motor
PostgreSQL hospedado en **Render** (plan gratuito).

### Script de creación de la base de datos

Ejecutar en la consola de PostgreSQL (psql) o en el panel de Render:

```sql
-- ==========================================
-- Tabla: usuarios
-- ==========================================
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    clave VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    rol VARCHAR(50) NOT NULL
);

-- ==========================================
-- Tabla: empleo
-- ==========================================
CREATE TABLE IF NOT EXISTS empleo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100),
    area_trabajo VARCHAR(100),
    empresa VARCHAR(100),
    nivel VARCHAR(50),
    sueldo VARCHAR(50),
    funciones VARCHAR(255),
    cargo_jefe VARCHAR(100)
);
```

> **Nota:** La base de datos montada en render es compartida por este programa y el de jsp de la unidad 1

---

##  Configuración

El proyecto usa **variables de entorno** para las credenciales (buena práctica, evita exponer secretos en el repositorio).

### Variables de entorno requeridas

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `DB_URL` | URL JDBC de conexión a PostgreSQL | `jdbc:postgresql://host:5432/empleodb?sslmode=require` |
| `DB_USER` | Usuario de la base de datos | `estudiante` |
| `DB_PASS` | Contraseña de la base de datos | `********` |
| `PORT` | Puerto del servidor (Render lo asigna) | `8080` |

## Cómo ejecutar la aplicación desde IntelliJ IDEA:

1. Abre el proyecto en IntelliJ.
2. Configura las variables de entorno en `Run` → `Edit Configurations`:
   ```
   DB_URL=jdbc:postgresql://dpg-daikf65g1s2s73fn5nvg-a.oregon-postgres.render.com:5432/empleodb?sslmode=require;
   DB_USER=estudiante;
   DB_PASS=QQOKX1frlWugK6WJtJe5yeCbW82upDfa
   ```
3. Ejecuta `EmpleoSpringApplication` (Shift + F10).

##  Credenciales de acceso

| Usuario | Correo | Contraseña | Rol |
| :--- | :--- | :--- | :--- |
| Administrador | `admin@gmail.com` | `1234` | ADMIN |

---

## Despliegue en Render
Para ver el programa funcionando en render usa el enlace entregado en el pdf.

### Pasos del despliegue

1. El repositorio está conectado a Render vía GitHub.
2. Render detecta el `Dockerfile` y construye la imagen automáticamente.
3. Se configuran las variables de entorno (`DB_URL`, `DB_USER`, `DB_PASS`).
4. El health check consulta `/healthz` cada cierto tiempo.
5. La app queda accesible públicamente por HTTPS.

### Dockerfile multi-etapa

```dockerfile
# Etapa 1: Compilación con Maven
FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre
COPY --from=build /target/Empleo_Spring-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

> **Nota:** El plan gratuito de Render suspende el servicio tras 15 minutos de inactividad. La primera petición tras la suspensión tarda ~30 segundos en responder.

---

## Rutas de la aplicación

### Autenticación

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| GET | `/login` | Formulario de inicio de sesión |
| POST | `/login` | Procesar login |
| GET | `/logout` | Cerrar sesión |
| GET | `/recuperar` | Formulario de recuperación de contraseña |
| POST | `/recuperar/enviar` | Enviar clave temporal por correo |

### Usuarios

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| GET | `/usuarios` | Listar todos los usuarios |
| GET | `/usuarios/nuevo` | Formulario de creación |
| POST | `/usuarios/guardar` | Guardar (crear o actualizar) |
| GET | `/usuarios/editar/{id}` | Formulario de edición |
| GET | `/usuarios/ver/{id}` | Ver detalle |
| GET | `/usuarios/eliminar/{id}` | Eliminar usuario |
| GET | `/usuarios/reporte/rol` | Reporte por rol (parametrizado) |
| GET | `/usuarios/reporte/nombre` | Reporte por nombre (parametrizado) |

### Empleos

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| GET | `/empleos` | Listar todos los empleos |
| GET | `/empleos/nuevo` | Formulario de creación |
| POST | `/empleos/guardar` | Guardar (crear o actualizar) |
| GET | `/empleos/editar/{id}` | Formulario de edición |
| GET | `/empleos/ver/{id}` | Ver detalle |
| GET | `/empleos/eliminar/{id}` | Eliminar empleo |
| GET | `/empleos/reporte/categoria` | Reporte por categoría (parametrizado) |
| GET | `/empleos/reporte/empresa` | Reporte por empresa (parametrizado) |

### Utilidades

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| GET | `/healthz` | Health check (Render) |

---

##  Tecnologías y patrones

| Patrón / Tecnología | Uso |
| :--- | :--- |
| **MVC (Model-View-Controller)** | Separación de la lógica de presentación, negocio y datos |
| **Repository Pattern** | Abstracción del acceso a datos con Spring Data JPA |
| **Dependency Injection** | Spring administra los beans automáticamente |
| **DTO / Entity** | Entidades JPA que mapean las tablas de la BD |
| **BCrypt** | Hash seguro de contraseñas |
| **Thymeleaf** | Renderizado de vistas HTML del lado del servidor |

---

##  Dependencias principales (`pom.xml`)

- `spring-boot-starter-web` — Spring MVC
- `spring-boot-starter-thymeleaf` — Motor de plantillas
- `spring-boot-starter-data-jpa` — Persistencia
- `spring-boot-starter-security` — Autenticación
- `spring-boot-starter-mail` — Envío de correos
- `thymeleaf-extras-springsecurity6` — Integración Thymeleaf + Spring Security
- `postgresql` — Driver JDBC
- `lombok` — Generación de getters/setters

---

## Licencia

Proyecto desarrollado con fines académicos para la asignatura Desarrollo Web.
