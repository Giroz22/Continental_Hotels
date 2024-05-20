# Continental_Hotels

## 1. Generalidades:

### Categoría

Turismo

### Integrantes

- Lis Sharik Agudelo Tobon
- Esteban Bedoya
- Alejandro Giraldo
- Santiago Andres Lara
- Tatiana Maldonado Pérez
- Cristian Camilo Manco Correa
- Juan Camilo Atehortua Herrera

### Proyecto

El desarrollo del backend es realizado en spring Boot como tecnología para el lado del servido, además consume datos usando APIrest para la persistencia de los datos del respectivo proyecto.

---

## 2. Nombre del proyecto:

Continental Hotels

---

## 3. Propuesta del trabajo

### ¿Por qué?

El proyecto busca cubrir una necesidad fundamental en la gestión de Continental Hotels: centralizar y optimizar el control de reservas y alojamientos en todas sus sedes. La implementación de un sistema de información específico para la cadena hotelera mejorará la eficiencia operativa, brindará una experiencia más satisfactoria a los huéspedes y facilitará la toma de decisiones estratégicas basadas en datos.

### ¿Para qué?

El propósito principal del sistema es proporcionar una plataforma integral que permita registrar, administrar y analizar todas las actividades relacionadas con las reservas de habitaciones y el alojamiento de huéspedes. Esto incluye la gestión de datos básicos de los huéspedes, información detallada de las habitaciones, control de reservas, generación de informes estadísticos y notificaciones automáticas para huéspedes y administradores.

### ¿Para quién?

El proyecto está dirigido a Continental Hotels y sus equipos administrativos y de atención al cliente. Es vital para los administradores de la cadena hotelera, quienes necesitan una herramienta robusta para gestionar eficientemente las operaciones diarias y tomar decisiones informadas. Además, beneficiará a los empleados encargados de la gestión de reservas y a los propios huéspedes, quienes disfrutarán de un proceso de reserva más fluido y una comunicación más efectiva durante su estadía. Además, el enfoque en la usabilidad y la eficiencia operativa garantizará una experiencia óptima tanto para los usuarios internos como para los huéspedes, fortaleciendo la reputación y competitividad de la cadena hotelera en el mercado turístico.

---

## 4. Funcionalidades Propuestas:

1. Registro de nuevos hoteles y habitaciones.
2. Consulta de todas las reservas.
3. Eliminación de hoteles y habitaciones.
4. Gestión de tipos de habitaciones más reservadas.
5. Creación, cancelación, edición y consulta de reservas y habitaciones.
6. Gestión de reservas por parte de los empleados.
7. Realización y cancelación de reservas por parte de los huéspedes.
8. Consulta de reservas realizadas por los huéspedes.
9. Autenticación y autorización de usuarios.
10. Recuperación o modificación de claves de acceso.
11. Notificaciones por correo electrónico para confirmación y modificación de reservas.
12. Generación de informes estadísticos sobre reservas.
13. Generación de reportes de reservas en diferentes formatos.
14. Generación de reportes personalizados para huéspedes.

---
## Arquitectura
```
continental/
└── src/
    └── main/
        ├── java/
        │   ├── api/
        │   │   ├── controller/
        │   │   ├── dto/
        │   │   └── error_handler/
        │   ├── config/
        │   ├── domain/
        │   │   ├── entities/
        │   │   └── repositories/
        │   ├── infraestructura/
        │   │   ├── abstract_service/
        │   │   └── service/
        │   └── utils/
        |   |   ├── enums/
        │   │   └── exceptions/
        └── resource/


```

## TRELLO
https://trello.com/b/EIHC6xAF/equipo-4

---

## Diagrama de la base de datos

![DiagramaDB](https://github.com/Giroz22/Continental_Hotels/assets/147103562/5f3e73e8-6846-49c3-8bf9-6c2ba1b05393)

## Diagrama de arquitectura

![DiagramaArquitectura](https://github.com/Giroz22/Continental_Hotels/assets/147103562/59deb77b-a06e-4df1-b06f-a9ec8676dd4c)


## Ejecucion del programa

1. Si es desde un sistema operativo windows abrir el programa xampp
2. Dar start a mysql
3. Abrir mysql en workbench en el puerto local
4. Ejecutar el comando `CREATE DATABASE continental;` para crear la base de datos.
5. Abrir postman mediante el siguiente enlace:  https://www.postman.com/continentalhotels/workspace/team-workspace/collection/34443806-c4d1d105-a0ed-47e2-a967-38782e860246?action=share&creator=34443806
6. Seleccionar la consulta que desea realizar e ingresar datos de ser necesarios para probar la 
funcionalidad del programa.