# Bai 5 - Test chuc nang Admin (30/11/2026)

## 1. Category

URL: http://localhost:8081/admin/categories

- Create: them Category 01 den Category 07 (7 ten khac nhau).
- Read: trang 1 hien 5 dong; trang 2 hien 2 dong.
- Search: tim `Category 07` phai co ket qua, bam Clear de reset.
- Update: sua ten va mo ta, Save va reload van con.
- Delete: xoa mot category, danh sach cap nhat.
- Validation: ten rong va ten trung phai bi tu choi.

## 2. User

URL: http://localhost:8081/admin/users

- Create: tao 6 user voi username/email rieng, password it nhat 6 ky tu.
- Read: trang 1 hien 5 dong, trang 2 hien 1 dong.
- Search: username, full name, email khong phan biet hoa/thuong.
- Update: sua Full Name, Email, Phone, Role; bo trong Password de giu mat khau cu.
- Delete: xoa mot user test, danh sach cap nhat.
- Validation: username hoac email trung phai bao loi.
- Safety: khong duoc xoa tai khoan admin dang dung.

## 3. Phan quyen, session, luu du lieu

- Logout; login bang USER; truy cap admin URL phai bi chan (403).
- Login lai ADMIN; CRUD su dung binh thuong.
- Khoi dong lai ung dung; du lieu da tao khong bi mat.

## 4. GitHub

- Repo moi, khong dung nhom Bai03/Bai04.
- `git status` de kiem tra truoc commit.
- Khong push `data/`, `target/`, API secret, database password.
