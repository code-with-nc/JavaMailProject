# 📧 JavaMailProject

A Java-based mail and user session management project developed using Java Servlets, JDBC, MySQL, and Jakarta Mail API.

The project demonstrates authentication handling, session management, cookie tracking, database integration, and email functionality in Java web applications.

---

# 🚀 Features

- User login authentication
- Session management using Servlets
- Cookie-based user tracking
- Dashboard access handling
- JDBC-based MySQL database connectivity
- Mail sending functionality
- Jakarta Mail API integration
- Servlet-based architecture
- Basic user management workflow

---

# 🛠️ Technologies Used

## Backend Technologies

- Java
- Java Servlets
- JDBC
- MySQL

---

## APIs & Libraries

- Jakarta Mail API
- Jakarta Activation API
- MySQL Connector/J

---

## Web & Server Technologies

- Apache Tomcat Server
- Servlet API
- Session Management
- Cookie Handling

---

# 📂 Project Structure

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

# 📘 Main Modules

---

## 🔐 Authentication Module

Handles user login and database validation using:

```text
LoginServlet.java
```

Features:
- Username/password validation
- Database verification
- Session creation after login

---

## 🧠 Session Management Module

Manages active user sessions using:

```text
SessionServlet.java
```

Features:
- Session creation
- Session tracking
- User authentication persistence

---

## 🍪 Cookie Management Module

Implements cookie-based tracking using:

```text
CookieServlet.java
CookieDashboard.java
```

Features:
- Cookie storage
- Cookie retrieval
- User tracking

---

## 📊 Dashboard Module

Provides dashboard access after successful authentication using:

```text
DashboardServlet.java
```

---

## 📧 Mail Client Module

Implements Java mail functionality using:

```text
MailClientWithDrawer.java
```

Features:
- Email sending
- Mail configuration
- SMTP-based communication
- Jakarta Mail integration

---

# ⚙️ Requirements

Before running the project, install:

- JDK 8 or higher
- Apache Tomcat Server
- MySQL Server
- Java Servlet API
- Jakarta Mail API
- MySQL Connector/J

---

# 💻 How to Run the Project

---

# Step 1️⃣ Clone Repository

## Using HTTPS

```bash
git clone https://github.com/code-with-nc/JavaMailProject.git
cd JavaMailProject
```

---

## Using SSH

```bash
git clone git@github.com:code-with-nc/JavaMailProject.git
cd JavaMailProject
```

---

# Step 2️⃣ Install Java & MySQL

Verify installation:

```bash
java -version
javac -version
mysql --version
```

---

# Step 3️⃣ Configure MySQL Database

Login to MySQL:

```bash
mysql -u root -p
```

Create database:

```sql
CREATE DATABASE javamailproject;
```

Use database:

```sql
USE javamailproject;
```

Create sample user table:

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100),
    password VARCHAR(100)
);
```

Insert sample user:

```sql
INSERT INTO users(username, password)
VALUES ('admin', 'admin123');
```

---

# Step 4️⃣ Configure Database Connection

Update database credentials inside:

```text
DBConnection.java
```

Example:

```java
String url = "jdbc:mysql://localhost:3306/javamailproject";
String username = "root";
String password = "your_mysql_password";
```

---

# Step 5️⃣ Add Required Libraries

Add all JAR files from:

```text
lib/
```

to your project build path.

Required JARs:

```text
jakarta.activation-2.0.1.jar
jakarta.mail-2.0.2.jar
mysql-connector-java-8.0.21.jar
```

---

# Step 6️⃣ Configure Apache Tomcat

Download Apache Tomcat:

🔗 https://tomcat.apache.org/

Copy project files into:

```text
apache-tomcat/webapps/
```

OR import the project into:

- Eclipse IDE
- IntelliJ IDEA
- NetBeans

and configure Tomcat server.

---

# Step 7️⃣ Compile Project

Example using terminal:

```bash
javac *.java
```

---

# Step 8️⃣ Start Apache Tomcat

Linux:

```bash
cd apache-tomcat/bin
./startup.sh
```

Windows:

```cmd
startup.bat
```

---

# Step 9️⃣ Open Project in Browser

Example:

```text
http://localhost:8080/JavaMailProject
```

---

# 📧 Mail Configuration

For mail functionality, configure SMTP credentials inside:

```text
MailClientWithDrawer.java
```

Example:

```java
String host = "smtp.gmail.com";
String from = "your_email@gmail.com";
String password = "your_app_password";
```

---

# 🔐 Security Concepts Covered

- Session Management
- Cookie Handling
- Authentication Flow
- Database Connectivity
- SMTP Mail Handling
- Servlet Lifecycle
- JDBC Operations
- Basic Web Security Concepts

---

# 🎯 Learning Objectives

After completing this project, learners will be able to:

- Build Java Servlet-based web applications
- Implement user authentication systems
- Manage user sessions and cookies
- Connect Java applications with MySQL using JDBC
- Configure and use Jakarta Mail API
- Deploy Java web applications on Apache Tomcat
- Understand servlet request-response workflow
- Handle database-driven login systems
- Understand basic Java web security concepts

---

# 📈 Future Improvements

- Convert project into Maven structure
- Add frontend UI pages
- Add password hashing
- Add role-based authentication
- Add email verification workflow
- Improve project structure using `src/main/java`
- Add REST API support
- Add Docker deployment support
- Add JWT-based authentication

---

# ⚠️ Troubleshooting

---

## MySQL Connection Error

Verify:
- MySQL server is running
- JDBC URL is correct
- username/password are valid

---

## Port 8080 Already in Use

Run Tomcat on another port:

```text
8081
```

Update:

```text
conf/server.xml
```

inside Tomcat.

---

## Servlet Not Loading

Check:
- servlet mapping
- Tomcat deployment
- compiled `.class` files
- project structure

---

## Mail Authentication Error

Use:
- Gmail App Password
- SMTP enabled account

instead of normal Gmail password.

---

# 🔒 Security Notice

This project is intended for:

- educational purposes
- Java web development learning
- servlet practice
- secure coding understanding

Do not deploy with default credentials or insecure configurations in production environments.

---

# 👩‍💻 Author

**Narayani**

GitHub: https://github.com/code-with-nc

---
