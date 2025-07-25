# 🛍️ E-commerce REST API (Spring Boot + MySQL)

A secure and scalable backend API for an e-commerce platform, built using Spring Boot, MySQL, JWT Authentication, and Spring Security.

---

## 🚀 Hướng dẫn cấu hình & chạy dự án

### 1. Yêu cầu hệ thống

- **Java 17** trở lên
- **Maven**
- **MySQL** (hoặc MariaDB)

### 2. Cài đặt & cấu hình database

- Tạo database tên `ecommerce` trong MySQL:
  ```sql
  CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  ```
- Tạo user/password hoặc dùng mặc định `root`/`root` (có thể chỉnh trong `src/main/resources/application.properties`):
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC
  spring.datasource.username=root
  spring.datasource.password=root
  ```
- Dữ liệu mẫu roles sẽ tự động được insert từ file `data.sql`.

### 3. Cấu hình JWT & server

- Có thể thay đổi secret và thời gian sống token trong `application.properties`:
  ```properties
  app.jwtSecret=SecretKeyForJwtSigningDoNotShare
  app.jwtExpirationInMs=86400000
  server.port=8081
  ```

### 4. Cài đặt dependencies & build project

```bash
mvn clean install
```

### 5. Chạy server

```bash
mvn spring-boot:run
```

Server mặc định chạy tại: [http://localhost:8081](http://localhost:8081)

---

## 📘 API & Swagger UI

- Truy cập tài liệu và thử API tại: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) hoặc `/swagger-ui/index.html`
- Nhấn **Authorize** (hình ổ khóa), nhập: `Bearer <token>` để xác thực JWT cho các endpoint cần bảo vệ.

---

## 🔐 Lấy JWT Token

1. **Đăng ký:**
   ```http
   POST /api/auth/signup
   Content-Type: application/json
   {
     "name": "Admin",
     "email": "admin@example.com",
     "password": "123456",
     "role": "ADMIN" // hoặc "CUSTOMER"
   }
   ```
2. **Đăng nhập:**
   ```http
   POST /api/auth/login
   Content-Type: application/json
   {
     "email": "admin@example.com",
     "password": "123456"
   }
   ```
   => Nhận về `{ "token": "..." }`

---

## 🛒 Ví dụ gọi API với Bearer Token

```bash
curl -X POST \
  'http://localhost:8081/api/products' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer <token>' \
  -d '{
  "name": "Bàn làm việc gỗ tự nhiên",
  "description": "Bàn làm việc được làm từ gỗ sồi, kích thước 120x60cm, phù hợp cho văn phòng và học tập.",
  "price": 1500000,
  "stock": 25
}'
```

---

## 🛠 Tech Stack

- Java 17, Spring Boot, Spring Security
- Spring Data JPA, MySQL
- JWT, Maven
- Swagger OpenAPI (springdoc-openapi)

---

## 📚 Một số API chính

### Auth

- `POST /api/auth/signup` — Đăng ký
- `POST /api/auth/login` — Đăng nhập

### Products

- `GET /api/products` — Xem sản phẩm
- `POST /api/products` — Thêm sản phẩm (**Admin**)
- `PUT /api/products/{id}` — Sửa sản phẩm (**Admin**)
- `DELETE /api/products/{id}` — Xóa sản phẩm (**Admin**)

### Cart (Customer only)

- `POST /api/cart/add` — Thêm vào giỏ
- `DELETE /api/cart/remove/{productId}` — Xóa khỏi giỏ
- `GET /api/cart` — Xem giỏ
- `DELETE /api/cart/clear` — Xóa toàn bộ giỏ

### Orders

- `POST /api/orders/create` — Đặt hàng (**Customer**)
- `GET /api/orders/my` — Xem đơn của tôi (**Customer**)
- `GET /api/orders` — Xem tất cả đơn (**Admin**)

---

## 📩 Liên hệ

- Email: support@ecommerce.com
