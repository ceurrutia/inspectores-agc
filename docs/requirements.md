# Documento de Requerimientos - Sistema de Gestión de Inspectores

## Objetivo
Desarrollar una API REST que permita gestionar usuarios e inspectores. La API deberá implementar roles de acceso, autenticación con JWT y soporte para operaciones CRUD según el tipo de usuario.

---

## Tipos de Usuario

### 1. SuperAdmin
- Puede hacer **todo**: crear usuarios, asignar roles, modificar, eliminar, ver todos los recursos.
- Accede a rutas protegidas por su rol.

### 2. Admin
- Puede hacer operaciones **CRUD sobre Inspectores**.
- No puede gestionar usuarios.
- Accede a rutas protegidas por su rol.

### 3. Público
- Solo puede hacer `GET /api/inspectores` (ver lista de inspectores).
- No necesita estar autenticado para esta acción.


## Autenticación y Seguridad

- JWT para autenticación.
- Spring Security configura los permisos por ruta.
- Ruta abierta:
    - `GET /api/inspectores`
- Rutas protegidas:
    - `POST /auth/login`
    - `POST /api/users` (solo superadmin)
    - `POST/PUT/DELETE /api/inspectores` (solo admin o superadmin)


## Entidad `Inspector`

| Campo     | Tipo    | Detalles                                               |
|-----------|---------|--------------------------------------------------------|
| id        | Long    | Autogenerado                                           |
| nombre    | String  | Obligatorio                                            |
| apellido  | String  | Obligatorio                                            |
| dni       | String  | Único, obligatorio                                     |
| area      | Enum    | Fiscalizacion y control, Seguridad Alimentaria, etc.  |
| imagen    | String  | URL opcional                                           |

## Entidad `User`

| Campo       | Tipo    | Detalles                            |
|-------------|---------|-------------------------------------|
| id          | Long    | Autogenerado                        |
| username    | String  | Obligatorio                         |
| email       | String  | Obligatorio, único                  |
| dni         | String  | Obligatorio, único                  |
| password    | String  | Encriptado con BCrypt               |
| enabled     | Boolean | Por defecto `true`                  |
| role        | Enum    | SUPERADMIN / ADMIN                  |
| resetToken  | String  | Opcional, para recuperar contraseña |

## DTOs para `Inspector`

Se utilizan DTOs para entrada/salida de datos en `Inspector`, protegiendo la capa de entidad.

### DTOs para `User`

- `UserDTO` contiene:
    - `username`, `email`, `dni`, `password`, `role`
- Se ocultan datos sensibles (como el hash de contraseña o el estado `enabled`) en respuestas públicas.

## Tecnologías

- Java 17
- Spring Boot
- Spring Security
- JWT (jjwt)
- Thymeleaf
- Maven
- MySQL (o cualquier base de datos compatible con JPA)
- Postman (para testing)


## Notas

- El sistema se inicializa con un `SUPERADMIN` precargado.
- Contraseñas se almacenan encriptadas con BCrypt.
- Tokens JWT tienen expiración configurable desde `.env`.
- Solo el `GET /api/inspectores` es público.
- Todos los demás endpoints requieren autenticación y control de rol.
