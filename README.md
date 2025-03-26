# Digital Wallet API

Digital Wallet API adalah aplikasi berbasis Spring Boot yang menyediakan fitur manajemen pengguna dan transaksi keuangan.

## 🚀 Teknologi yang Digunakan
- **Spring Boot** (REST API)
- **Spring Data JPA** (Database Management)
- **Swagger UI** (API Documentation)
- **MySQL** (Database)

## 📌 Instalasi dan Menjalankan Aplikasi

### 1️⃣ Clone Repository
```sh
git clone https://github.com/FikrulAkhyar/digital-wallet.git
cd digital-wallet
```

### 2️⃣ Konfigurasi Database (Opsional)
Edit file `application.properties` untuk menggunakan database yang sesuai:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/digital_wallet
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

## 📖 Dokumentasi API
Dokumentasi API tersedia di Swagger UI setelah aplikasi berjalan:

🔗 **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 📌 API Endpoint

### 🔹 **User Management**
| Method | Endpoint      | Deskripsi               |
|--------|-------------|--------------------------|
| GET    | `/users`    | Menampilkan semua user  |
| GET    | `/users/{id}` | Menampilkan detail user |
| POST   | `/users`    | Membuat user baru       |

Contoh Request Body (Create User):
```json
{
  "name": "John Doe"
}
```

### 🔹 **Transaction Management**
| Method | Endpoint                 | Deskripsi                 |
|--------|--------------------------|----------------------------|
| POST   | `/transactions/deposit`  | Melakukan deposit saldo  |
| POST   | `/transactions/withdraw` | Melakukan penarikan saldo |
| GET    | `/transactions/{userId}` | Menampilkan transaksi berdasarkan user |

Contoh Request Body (Deposit / Withdraw):
```json
{
  "userId": "afffc51f-ffa3-463e-83bc-1e422bdae5e8",
  "amount": 30000
}
```

### 🔹 **Inquiry Management**
| Method | Endpoint         | Deskripsi                 |
|--------|-----------------|----------------------------|
| GET    | `/inquiry/{userId}` | Mengecek saldo pengguna |

## 📌 Lisensi
Aplikasi ini dirilis di bawah [MIT License](LICENSE).

