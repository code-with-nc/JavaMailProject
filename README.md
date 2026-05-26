# JavaMailProject

A Java-based mail and user-session management project built using Java Servlets, JDBC, MySQL, and Jakarta Mail API.

---

## Features

- User login handling
- Session management
- Cookie-based user tracking
- Dashboard servlet
- MySQL database connectivity using JDBC
- Java mail client functionality
- Jakarta Mail API integration

---

## Technologies Used

- Java
- Java Servlets
- JDBC
- MySQL
- Jakarta Mail API
- Jakarta Activation API

---

## Project Structure

```text
JavaMailProject/
├── CookieDashboard.java
├── CookieServlet.java
├── DBConnection.java
├── DashboardServlet.java
├── LoginServlet.java
├── SessionServlet.java
├── mailclient/
│   ├── DBConnection.java
│   └── MailClientWithDrawer.java
├── lib/
│   ├── jakarta.activation-2.0.1.jar
│   ├── jakarta.mail-2.0.2.jar
│   └── mysql-connector-java-8.0.21.jar
└── README.md
```

---

## Main Modules

### Authentication Module
Handles user login and database validation using `LoginServlet.java`.

### Session Management Module
Manages user sessions using `SessionServlet.java`.

### Cookie Management Module
Implements cookie-based tracking using `CookieServlet.java` and `CookieDashboard.java`.

### Dashboard Module
Provides dashboard access after successful authentication using `DashboardServlet.java`.

### Mail Client Module
Implements mail-client functionality using Jakarta Mail in `MailClientWithDrawer.java`.

---

## Requirements

- JDK 8 or higher
- Apache Tomcat Server
- MySQL Server
- Java Servlet API
- Jakarta Mail API
- MySQL Connector/J

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/code-with-nc/JavaMailProject.git
cd JavaMailProject
```

### 2. Configure Database Credentials

Update the database connection details inside `DBConnection.java`.

### 3. Add Required Libraries

Add all JAR files from the `lib/` directory to your project build path.

### 4. Deploy on Apache Tomcat

Deploy the servlet files to Apache Tomcat server.

### 5. Run the Application

Start the Tomcat server and open the application in your browser.

---

## Database Configuration

Example JDBC configuration:

```java
String url = "jdbc:mysql://localhost:3306/your_database_name";
String username = "your_mysql_username";
String password = "your_mysql_password";
```

---

## Future Improvements

- Convert project to Maven structure
- Add frontend UI pages
- Add screenshots and documentation
- Implement password hashing
- Add environment-based configuration
- Improve project structure using `src/main/java`
- Add servlet mapping documentation

---

## Author

**Narayani**

GitHub: https://github.com/code-with-nc
