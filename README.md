🛍️ E-commerce REST API (Spring Boot + MySQL)
A secure and scalable backend API for an e-commerce platform, built using Spring Boot, MySQL, JWT Authentication, and Spring Security.

✅ Key Features:
User & Admin authentication with JWT

Role-based access control

Admin-only product management

Customer cart functionality

Customer order placement

Admin access to all orders

🛠 Tech Stack:
Java 17, Spring Boot, Spring Security

Spring Data JPA, MySQL

JWT, Maven

📘 API Endpoints
🔐 Auth
POST /api/auth/signup

POST /api/auth/login

🛒 Products
GET /api/products

POST /api/products (Admin only)

PUT /api/products/{id} (Admin only)

DELETE /api/products/{id} (Admin only)

🛍️ Cart (Customer only)
POST /api/cart/add

DELETE /api/cart/remove/{productId}

GET /api/cart

DELETE /api/cart/clear

📦 Orders
POST /api/orders/place (Customer only)

GET /api/orders/my (Customer only)

GET /api/orders (Admin only)
