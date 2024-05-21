# Continental_Hotels

## 1. Generalities:

### Category

Turism

### Members

- Lis Sharik Agudelo Tobon
- Esteban Bedoya
- Alejandro Giraldo
- Santiago Andres Lara
- Tatiana Maldonado Pérez
- Cristian Camilo Manco Correa
- Juan Camilo Atehortua Herrera

### Project

The backend development is done in Spring Boot as the server-side technology. Additionally, it consumes data using REST APIs for the persistence of the respective project's data.

---

## 2. name the project:

Continental Hotels

---

## 3. The work proposal

### ¿Why?

The project aims to address a fundamental need in Continental Hotels' management: centralizing and optimizing the control of reservations and accommodations across all its branches. Implementing a specific information system for the hotel chain will enhance operational efficiency, provide a more satisfying experience for guests, and facilitate strategic decision-making based on data.

### ¿For what?

The main purpose of the system is to provide a comprehensive platform that allows for the registration, management, and analysis of all activities related to room reservations and guest accommodation. This includes managing basic guest data, detailed room information, reservation control, generating statistical reports, and automatic notifications for both guests and administrators.

### ¿For who?

The project is aimed at Continental Hotels and its administrative and customer service teams. It is vital for the hotel chain's administrators, who need a robust tool to efficiently manage daily operations and make informed decisions. Additionally, it will benefit employees responsible for reservation management and the guests themselves, who will enjoy a smoother booking process and more effective communication during their stay. Moreover, the focus on usability and operational efficiency will ensure an optimal experience for both internal users and guests, strengthening the hotel chain's reputation and competitiveness in the tourism market.

---

## 4. Functionality Proposals:

1. Registration of new hotels and rooms.
2. Querying all reservations.
3. Deletion of hotels and rooms.
4. Management of most reserved room types.
5. Creation, cancellation, editing, and querying of reservations and rooms.
6. Reservation management by employees.
7. Making and canceling reservations by guests.
8. Querying reservations made by guests.
9. User authentication and authorization.
10. Recovery or modification of access keys.
11. Email notifications for reservation confirmation and modification.
12. Generation of statistical reports on reservations.
13. Generation of reservation reports in different formats.
14. Generation of personalized reports for guests.

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

## Database diagram

![DiagramaDB](https://github.com/Giroz22/Continental_Hotels/assets/147103562/5f3e73e8-6846-49c3-8bf9-6c2ba1b05393)

## Architecture diagram

![DiagramaArquitectura](https://github.com/Giroz22/Continental_Hotels/assets/147103562/59deb77b-a06e-4df1-b06f-a9ec8676dd4c)


## Requirements to run the program

1. The program uses Java version 17 in the code editor.
2. XAMPP must be installed.
3. MySQL Workbench must be installed.

## Program execution

1. If you are using a Windows operating system, open the XAMPP program.
2. Start MySQL.
3. Open MySQL Workbench on the local port.
4. Execute the command `CREATE DATABASE continental;` to create the database.
5. Run the program from the code editor.
6. Open Postman using the following link:  https://www.postman.com/continentalhotels/workspace/team-workspace/collection/34443806-c4d1d105-a0ed-47e2-a967-38782e860246?action=share&creator=34443806
7. Select the query you wish to perform and enter data if necessary to test the program's functionality.