# User Management - JSP/Servlet/JDBC/MySQL

Ứng dụng MVC quản lý User bằng Jakarta Servlet, JSP/JSTL, JDBC và MySQL, tương thích Tomcat 10.1+.

## Chức năng
- Hiển thị danh sách User
- Thêm User
- Sửa User
- Xóa User
- Tìm kiếm User theo country
- Sắp xếp User theo name (A → Z)
- CRUD bằng JDBC/MySQL

## 1. CSDL
Chạy `database.sql` trong MySQL. Sau đó mở:
`src/main/java/com/codegym/dao/UserDAO.java`
và sửa `jdbcUsername`, `jdbcPassword` theo máy của bạn.

## 2. Build
```bash
mvn clean package
```
WAR được tạo tại `target/user-management.war`.

## 3. Chạy Tomcat
Copy WAR vào thư mục `webapps` của Tomcat 10.1+ rồi khởi động Tomcat.
Truy cập:
`http://localhost:8080/user-management/users`

## 4. Tìm kiếm theo country
Nhập tên quốc gia vào ô tìm kiếm, ví dụ `Viet Nam`, sau đó bấm **Tìm kiếm**.
Ứng dụng sử dụng SQL `LIKE` nên có thể tìm theo một phần tên quốc gia.

## 5. Sắp xếp theo name
Bấm **Sắp xếp theo tên** để lấy danh sách theo thứ tự tên A → Z.
