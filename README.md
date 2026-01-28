# BuilderPortfolioManagementSystem

The Builder Portfolio Management System is a Java-based console application designed to manage construction projects efficiently.
It connects Project Managers, Builders, and Clients and helps track project assignments and progress.

This project follows a layered architecture using Model, DAO, Service, View, and Utility components.

 Features
 
=> User Management
  * Register as:
    - Builder
    - Project Manager
  * Secure Login System
  * Session handling for logged-in users
  * Validations for name, email, and password


=> Project Management
  * Managers can:
    - Create projects
    - Assign projects to builders
    - View their projects
    - Delete projects
  * Builders can:
    - View assigned projects
    - Update project status


=> Project Status Tracking
  * Projects move through stages:
    - UPCOMING
    - IN_PROGRESS
    - COMPLETED


=> Client Handling
Each project stores client details like name, email, and phone.
* Exception Handling
  Custom exceptions used:
  - UserAlreadyExistsException
  - InvalidCredentialsException
  - UserNotFoundException

* Unit Testing
  JUnit 5 test cases cover:
  - User creation and validation
  - Registration & login services
  - Project creation and assignment
  - Status updates
  - Deletion logic
  - Exception scenarios


=> Project Architecture
com.builderportfolio
│
├── model        → Entities (User, Project, Client, Status)
├── dao          → Data Access Objects (In-memory storage)
├── service      → Business logic layer
├── view         → Console UI menus and views
├── util         → Utilities (Session, Input, ServiceFactory)
├── exception    → Custom exception classes
└── test         → JUnit test classes


=> Technologies Used
  * Java
  * JUnit 5
  * IntelliJ IDEA
  * OOP Principles
  * Layered Architecture


=> How to Run
  * Clone the repository
  * Open in IntelliJ IDEA
  * Run the Main class
  * Use the console menu to:
     - Register
     - Login
     - Create projects
     - Assign builders
     - Update project status


=> How to Run Tests
  * Right-click the test folder
  * Select Run Tests
  * All service and model test cases will execute


=> Future Enhancements
  * Database integration (PostgreSQL)
  * Web or GUI interface
  * Role-based dashboards
  * File upload for project images
  * Notifications system
