// Tạo user admin cho database admin
db = db.getSiblingDB('admin');

// Kiểm tra xem user đã tồn tại chưa
const adminUser = db.getUser("root");
if (!adminUser) {
    db.createUser({
        user: "root",
        pwd: "root",
        roles: [{ role: "root", db: "admin" }]
    });
}

// Tạo user cho ứng dụng với quyền hạn giới hạn hơn
db = db.getSiblingDB('broad-leaf-db');
db.createUser({
    user: "app_user",
    pwd:  "app_password",
    roles: [
        { role: "readWrite", db: "broad-leaf-db" }
    ]
});