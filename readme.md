# Inspectores API - Documentación del Proyecto

# Introducción
Este sistema gestiona los usuarios del sistema y los inspectores de diversas áreas, permitiendo que usuarios autenticados con roles especiales (ADMIN, SUPERADMIN) puedan realizar operaciones CRUD sobre inspectores.
## Tecnologías Utilizadas
* Java 17
* Spring Boot
* Spring Security + JWT
* JPA + Hibernate
* MySQL
* Thymeleaf (dashboards de superamin, admin, página de registro de usuario)
* Maven
* Dotenv (configuraciones)

## Requisitos del Sistema

* JDK 17+
* MySQL en funcionamiento

## Archivo .env con:

- JWT_SECRET=tu_clave
- JWT_EXPIRATION=3600000
- SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/nombre_bd
- SPRING_DATASOURCE_USERNAME=tu_usuario
- SPRING_DATASOURCE_PASSWORD=tu_clave

## Instalación

* git clone https://github.com/ceurrutia/inspectores-agc.git
* cd Inspectores
* ./mvnw spring-boot:run

## Autenticación

El login de usuario se realiza a través de la interfaz en thymeleaf:
Endpoint: POST /auth/login
http://localhost:8080/auth/login


## Endpoints Principales

## Usuarios

* POST /auth/register - Crear SUPERADMIN 
* POST /auth/register-admin - Crear ADMIN (requiere token SUPERADMIN)

## Roles

* SUPERADMIN: puede crear admins y CRUD de inspectores
* ADMIN: solo CRUD de inspectores
* INSPECTORES: no pueden loguearse, solo se accede a sus datos vía GET

## Inspectores

* GET /api/inspectores - Público, lista todos los inspectores
* GET /api/inspectores/{id} - Público, ver un inspector
* POST /api/inspectores - Crear (ADMIN o SUPERADMIN)
* PUT /api/inspectores/{id} - Modificar (ADMIN o SUPERADMIN)
* DELETE /api/inspectores/{id} - Eliminar (ADMIN o SUPERADMIN)

## Modelo de Inspector

Inspector {
Long id;
String nombre;
String apellido;
String dni;
Area area; // Enum
Funcion funcion; //Enum
String imagen; // URL o base64
}

## Areas Posibles (Enum Area):

* FISCALIZACION_Y_CONTROL
* SEGURIDAD_ALIMENTARIA
* HABILITACIONES_Y_PERMISOS
* FISCALIZACION_Y_CONTROL_OBRAS
* UCA
* 
## Funciones Posibles (Enum Funcion):

* INSPECTOR
* VERIFICADOR

## Frontend

El GET de inspectores está abierto para integrarse con cualquier frontend

## Estructura del Proyecto

- inspectores/
- enums/
- controller/
- service/
- entity/
- dto/
- repository/
- security/
- utils/

## Seguridad

* Todos los endpoints salvo GET /api/inspectores requieren autenticación JWT
* Las rutas para crear ADMINs solo están permitidas para SUPERADMIN

## Futuras mejoras

* Agregar paginación
* Subida de imagen real