# CycleSync — Bicycle Sharing & Management System

A full-stack Java web application for managing a bicycle-sharing service, built with Jakarta EE, Apache Tomcat 10.1, and MySQL.

---

## Tech Stack

| Layer      | Technology                        |
|------------|-----------------------------------|
| Backend    | Java 21, Jakarta Servlets 5.0     |
| Frontend   | JSP, JSTL 3.0, CSS, Lucide Icons  |
| Database   | MySQL 8 (via XAMPP)               |
| Server     | Apache Tomcat 10.1                |
| IDE        | Eclipse IDE for Enterprise Java   |

---

## Features

- **Member** registration, login, and profile management
- **Bicycle** browsing, real-time availability search, and borrowing
- **Automated** fine calculation for overdue returns
- **Admin** dashboard with fleet analytics, member management, and transaction history
- **BCrypt** password hashing for secure authentication
- **Role-based** access control (Admin / Member)

---

## Getting Started

### Prerequisites

- Java 21 (JDK)
- Apache Tomcat 10.1
- MySQL 8 / XAMPP
- Eclipse IDE for Enterprise Java and Web Developers

### 1. Clone the Repository

```
git clone https://github.com/Bijen-Lama/CycleSync.git
```

### 2. Database Setup

1. Start XAMPP and ensure MySQL is running on port **3306**.
2. Open **phpMyAdmin** and import the SQL schema:
   ```
   database/cyclesync_db.sql
   ```

### 3. Environment Variables (Optional)

By default the application connects to `127.0.0.1:3306` with user `root` and no password.
Override any value by setting environment variables before starting Tomcat:

| Variable      | Default         | Description          |
|---------------|-----------------|----------------------|
| `DB_HOST`     | `127.0.0.1`     | MySQL host           |
| `DB_PORT`     | `3306`          | MySQL port           |
| `DB_NAME`     | `cyclesync_db`  | Database name        |
| `DB_USER`     | `root`          | Database username    |
| `DB_PASSWORD` | *(empty)*       | Database password    |

### 4. Import into Eclipse

1. **File → Import → Existing Projects into Workspace** → select the cloned folder.
2. Right-click the project → **Properties → Project Facets** → ensure *Dynamic Web Module 5.0* and *Java 21* are selected.
3. Add the project to a **Tomcat 10.1** server.
4. **Project → Clean** to rebuild.
5. Start the server and navigate to `http://localhost:8080/CycleSync/`.

---

## Project Structure

```
CycleSync/
├── src/
│   └── main/
│       ├── java/com/cyclesync/
│       │   ├── config/        # Database configuration
│       │   ├── controllers/   # Jakarta Servlet controllers
│       │   ├── dao/           # Data Access Objects
│       │   ├── model/         # Entity/Model classes
│       │   ├── service/       # Business logic services
│       │   └── listeners/     # Application lifecycle listeners
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── pages/     # JSP view pages
│           │   ├── lib/       # Runtime JARs (JSTL 3.0, MySQL Connector)
│           │   └── web.xml    # Deployment descriptor
│           ├── css/           # Stylesheets
│           └── js/            # JavaScript files
├── database/                  # SQL schema scripts
└── docs/                      # Project documentation
```

---

## License

This project is submitted as academic coursework and is not licensed for commercial use.
