# 🛒 RevShop - Console-Based E-Commerce Application

RevShop is a fully functional console-based e-commerce system built using **Java 21** and **Oracle 10g XE** with pure **JDBC (no frameworks)**.

The application supports both **Buyer** and **Seller** roles and follows a clean layered architecture.  
The design is modular and future-ready for migration to **Spring Boot**.

---

# 📌 Project Overview

RevShop simulates a real-world e-commerce backend system where:

- Buyers can browse, purchase, review products.
- Sellers can manage inventory and products.
- Orders, payments, reviews, and notifications are handled through proper database transactions.

The system is designed using strict separation of concerns:

```
Controller → Service → DAO → Database
```

---

# 🏗 Architecture

## 📂 Package Structure

```
com.revshop
│
├── controller      → Menu interaction layer
├── service         → Business logic layer
│   └── impl
├── dao             → Database access layer
│   └── impl
├── model           → Entity classes
├── util            → DB connection & utilities
├── exception       → Custom exceptions
└── main            → Application entry point
```

---

# 🗄 Database Design

## Tables Used

- USERS
- SELLERS
- PRODUCTS
- CART
- ORDERS
- ORDER_ITEMS
- REVIEWS
- FAVORITES
- NOTIFICATIONS

## ID Management

- Oracle Sequences
- BEFORE INSERT Triggers

## Referential Integrity

- Proper Foreign Keys
- Prevents orphan records
- Transaction-based order placement
- Integrity constraint validation

---

# 👤 Buyer Features

- Register / Login
- Forgot password (Security Question)
- Browse Products
- Add / Remove Cart Items
- Checkout (Order Preview)
- Payment Simulation
- Order History
- View Notifications
- Add Product Reviews
- View Invoice per Order
- Manage Favorites

---

# 🏪 Seller Features

- Register with Business Details
- Add Product
- Update Product
- Delete Product (Integrity Protected)
- Browse Own Products
- Stock Threshold Alert

---

# ⚙️ Technologies Used

- Java 21
- JDBC (PreparedStatement)
- Oracle 10g XE
- SQL*Plus
- IntelliJ IDEA

---

# 🔧 Setup Instructions

## 1️⃣ Install Oracle 10g XE

Ensure Oracle service is running.

---

## 2️⃣ Run Database Schema

Open SQL*Plus and run:

```
@schema/revshop_schema.sql
```

---

## 3️⃣ Add Oracle JDBC Driver

Download `ojdbc8.jar` and add it to your project classpath.

---

## 4️⃣ Configure Database Connection

Edit:

`com.revshop.util.DatabaseConnection`

```java
private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

---

## 5️⃣ Run Application

Run:

```
com.revshop.main.RevShopApplication
```

---

# 🛡 Business Rules Implemented

- Discount price cannot exceed MRP
- Cannot delete product if order exists (foreign key protection)
- Low stock alert based on threshold
- Cart cleared after successful order
- All SQL executed using PreparedStatement
- Layered architecture maintained
- Transaction handling during checkout

---

# 📊 ERD Overview

```
USERS ───< SELLERS
USERS ───< ORDERS ───< ORDER_ITEMS >─── PRODUCTS
PRODUCTS ───< REVIEWS
PRODUCTS ───< CART
PRODUCTS ───< FAVORITES
USERS ───< NOTIFICATIONS
```

---

# 🔄 Future Enhancements

- Convert DAO → JPA Repository
- Convert Controllers → REST Controllers
- Move DB configuration → application.properties
- Add JWT Authentication
- Add Payment Gateway Integration
- Add Sales Analytics Dashboard
- Convert to Microservices Architecture

---

# 🎯 Design Goals

- Clean OOP Principles
- Modular Structure
- Spring Boot Ready
- Transaction-Safe Operations
- Oracle Compatible Schema
- Production-Like Console Workflow

---

# 👨‍💻 Author

Developed as part of advanced backend architecture learning and enterprise-level Java practice.

---

# 📌 License

This project is for educational and academic purposes.
