# Refugio Patitas - Sistema de Gestión

Este proyecto es una aplicación web desarrollada en Spring Boot para la asignatura de Sistemas Distribuidos, perteneciente al Grado en Ingeniería Informática. Consiste en un sistema de gestión integral para un refugio de animales, permitiendo administrar la información de los Animales, los Voluntarios y los Hábitats o recintos.

## Características principales

El proyecto cuenta con las siguientes funcionalidades, implementadas con su capa de Servicios (`@Service`) para compartir los mismos datos tanto para el lado Web (MVC) como para el lado de la Interfaz (REST API):

- **Gestión de Animales:**
  - Registro de nombre, especie, edad y fecha de ingreso automática.
  - Alternancia rápida del estado de adopción ("Adoptado: Sí / No").

- **Gestión de Voluntarios:**
  - Control de nombre, email, y tipo de labor (Limpieza, Veterinario, Recepción).
  - Estado de cuenta activo/inactivo modificable al instante.

- **Gestión de Hábitats (Recintos):**
  - Registro de nombre (ej. Jaula de Cuarentena), tipo, capacidad, y disponibilidad.
  - **Relaciones implementadas:** Permite designar un **Coordinador** (Eligiendo un Voluntario de la lista) y asignar los **Animales** residentes.

- **Diseño Web y Experiencia (UI):**
  - Interfaz construida con HTML y Thymeleaf.
  - Estilizada íntegramente con **Vanilla CSS**, asegurando un diseño ordenado, centrado y escalable sin depender de herramientas de terceros ni frameworks complejos. Incluye páginas de error dinámicas personalizadas (404).

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.x**
  - `spring-boot-starter-web` (Para API REST y Controladores MVC)
  - `spring-boot-starter-thymeleaf` (Motor de plantillas Web)
- **HTML5 & CSS3**

*(Nota: Los datos tienen persistencia temporal en memoria operativa concurrente `ConcurrentHashMap` utilizando `AtomicLong` para los auto-incrementos, cumpliendo con los requisitos iniciales del prototipo).*

## Instrucciones de Uso

### Ejecución Local

1. Asegúrate de tener **Java** y **Maven** instalados.
2. Navega hasta el directorio raíz del proyecto (`animalshelter`).
3. Ejecuta el servidor de Spring Boot desde consola:
   ```bash
   ./mvnw spring-boot:run
   ```
   *Alternativamente, puedes darle al botón "Run" desde tu IDE directamente sobre la clase `AnimalshelterApplication.java`.*

4. Una vez que la consola indique `Tomcat started on port 8080`, abre tu navegador de preferencia y visita:
   - **Frontend Web:** [http://localhost:8080/animals](http://localhost:8080/animals)

### Uso del API REST
Todas las entidades se encuentran expuestas también de forma programática bajo la ruta `/api/`. Puedes utilizar Postman o cURL para realizar peticiones POST, GET, PUT, PATCH, o DELETE.
- `http://localhost:8080/api/animals/`
- `http://localhost:8080/api/volunteers/`
- `http://localhost:8080/api/habitats/`
