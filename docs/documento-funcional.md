# Buscador de Inspectores - Agencia Gubernamental de Control

**Requerimientos Funcionales**  
**Fecha:** 18/06/2025  
**Versión:** 1.0  
**Autor:** Cecilia Urrutia

---

## 1. Introducción

### Propósito del Documento
Establecer los requerimientos indispensables para desarrollar un Producto Mínimo Viable (PMV) de una API de búsqueda de inspectores para la Agencia Gubernamental de Control.

### Público Objetivo
Administrados de la Ciudad de Buenos Aires que puedan validar la identidad de un inspector cuando ingrese a su local comercial/obra en construcción.

### Meta
Desarrollar un MVP de API que contemple:

- Un frontend con un buscador de inspectores por apellido, DNI o área.
- Un backend que permita al usuario administrador realizar operaciones de creación, actualización y eliminación de registros.

### Uso
- El **frontend** no requiere login para visualizar correctamente los datos públicos de los inspectores (GET).
- En el **backend**, el gestor del sitio deberá contemplar:

    1. **Registro superAdmin**: Un usuario superadmin precargado.
    2. **Inicio de sesión de administradores**:
        - Introducir email y contraseña para iniciar sesión.
        - Contar con manejo de sesiones basado en **JWT** (JSON Web Tokens) con expiración configurada a 24hs.
    3. **Recuperación de contraseña**:
        - Solicitud mediante un formulario que envía un enlace al correo del usuario.
        - El enlace permite establecer una nueva contraseña que cumple con los requisitos de fortaleza.

---

## 2. Roles de Usuario

### Usuario SUPERADMIN

**Podrá:**
- Crear nuevos administradores.
- Modificar los datos de perfil de SUPERADMIN o ADMIN (DNI o CUIL, email, rol, nombre de usuario), no contraseña.
- Realizar operaciones CRUD sobre roles: SUPERADMIN, ADMIN.
- Eliminar cuentas.
- Realizar operaciones CRUD sobre inspectores.

### Usuario ADMIN

**Podrá:**
- Administrar inspectores: crear, modificar y eliminar.

**No podrá:**
- Eliminar su cuenta.
- Crear nuevos usuarios ADMIN o SUPERADMIN.

---

## 3. Descripción General

### Requisitos de usuarios ADMIN y SUPERADMIN
- Ser empleados de la Agencia Gubernamental de Control
- Completar el registro con:
    - Nombre
    - Apellido
    - DNI o CUIL
    - Email válido con extensión `@buenosaires.gob.ar`
    - Contraseña fuerte:
        - Al menos 8 caracteres
        - Al menos 2 números
        - Al menos un carácter especial
        - Al menos una mayúscula
- Su cuenta será activada por el usuario SUPERADMIN.

### Requisitos Específicos

#### Seguridad
- Validación de contraseñas fuertes en el registro.
- Contraseñas almacenadas con encriptación mediante **bcrypt**.

#### Roles y permisos
- Diferenciación clara entre los permisos de cada tipo de usuario.
- Restricciones según el rol definido.

---

## 4. Especificaciones Técnicas

### Requerimientos Funcionales

- **Registro y Login:**
    - Validación de datos en el formulario de registro.
    - Almacenamiento seguro de las credenciales mediante **bcrypt**.

- **Manejo de sesiones:**
    - Generación y verificación de sesiones seguras con **JWT**.
    - Configuración de expiración de sesión en 24hs.

- **Recuperación de contraseña:**
    - Enlace de recuperación enviado al correo electrónico.
    - Validación de nueva contraseña y actualización segura en la base de datos.

### Requerimientos No Funcionales

- **Escalabilidad:**
    - Diseño modular que permita agregar funcionalidades adicionales en el futuro.

- **Usabilidad:**
    - Interfaz amigable para usuarios con experiencia básica en navegación web.

- **Seguridad:**
    - Proteger los datos de usuario y asegurar conexiones seguras (HTTPS).

### Pila Tecnológica

- **Frontend:** HTML5, JavaScript, React + Vite (Librería Obelisco V2 basada en Bootstrap 5)
- **Backend:** Java Spring Boot, Maven, Spring Security. Manejo de sesiones con JWT y contraseñas con bcrypt.
- **Base de Datos:** Cualquiera compatible con JPA (MySQL, PostgreSQL, SQL)
- **Servidor:** A definir por el área de infraestructura de la Agencia (se recomienda Cloud Server)

---

## 5. Requisitos de la Interfaz

### Estándares de Diseño
- Interfaz limpia y minimalista.
- Colores de su manual de marca y fuentes legibles.
- Compatibilidad con navegadores modernos.

### Diseño de Pantallas

1. **Pantalla de Inicio:**
    - Formulario de inicio de sesión (ADMIN y SUPERADMIN)
    - Formulario de creación de nuevo usuario (solo SUPERADMIN)

2. **Dashboard de SUPERADMIN:**
    - Acceso a pantalla de usuarios y su gestión.
    - Acceso a pantalla de inspectores y su gestión.

3. **Dashboard de ADMIN:**
    - Acceso a pantalla de inspectores y su gestión.
