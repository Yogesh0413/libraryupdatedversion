# 📚 Library Management System (Updated Version)

A modern, clean, and fully functional **Library Management System** built with **Java**, **Spring Boot**, **Thymeleaf**, **MySQL**, and **Bootstrap**. This project is structured to follow good software engineering practices such as **MVC architecture**, **DTO usage**, **services/repositories**, and **role‑based access (Admin / Student)**.

---

## 🚀 Features

### **👨‍💼 Admin Features**

* Add / Edit / Delete Books
* Manage Students / Users
* Assign / Update User Roles (Admin, Student)
* View All Issued Books
* Dashboard and Reports

### **🎓 Student Features**

* View books
* Borrow and return books
* View issued book history

### **⚙️ System Features**

* Secure Login using Spring Security
* Layered Architecture
* MySQL persistent database
* Validation + Error Handling
* Responsive UI with Bootstrap

---

## 🏗️ Project Structure

```
src/main/java/com/java/librarymanagement
│
├── controllers
│   ├── AdminController
│   ├── BookController
│   ├── DashboardController
│   └── UserController
│
├── entities
│   ├── Book
│   ├── BorrowRecord
│   └── User
│
├── repos
│   ├── BookRepository
│   ├── BorrowRecordRepository
│   └── UserRepository
│
├── services
│   ├── BookService
│   ├── BorrowService
│   └── UserService
│
└── LibrarymanagementApplication
```

---

## 🛠️ Technologies Used

* Java 17+
* Spring Boot
* Spring Data JPA
* Spring Security
* MySQL
* Thymeleaf
* Bootstrap 4/5

---

## 🏁 Getting Started

### **1. Clone the Repo**

```
git clone https://github.com/your-username/libraryupdatedversoin.git
cd libraryupdatedversoin
```

### **2. Configure Database**

Create a database in MySQL:

```
CREATE DATABASE library_db;
```

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### **3. Run the Application**

```
mvn spring-boot:run
```

Open in browser:

```
http://localhost:8080/
```

---


## 🧱 Folder Details

### **Controller Layer**

Handles HTTP requests and passes data to services.

### **Service Layer**

Business logic lives here.

### **Repository Layer**

Interacts with the database using JPA.

### **Templates**

Thymeleaf HTML pages.

---

## 🧪 Testing

* Postman for API testing
* MySQL Workbench for DB checking

---

## 🐛 Troubleshooting

* If the database resets, ensure `ddl-auto=update`
* If tables don’t appear, check your entities and naming conventions
* If login fails, check Spring Security configuration

---

## 🤝 Contributing

Pull requests are welcome! Please open an issue first for major changes.

---


## ⭐ Like This Project?

Give it a **star** on GitHub to support the development!
