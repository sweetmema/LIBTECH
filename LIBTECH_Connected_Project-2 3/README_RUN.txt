LIBTECH Library Management System - Connected to MySQL Database

1) Open MySQL Workbench or MySQL command line.
2) Run the file: LIBTCH_database.sql
   This creates database LIBTCH and user:
   username: lib_user
   password: 123456

3) Open this folder in NetBeans as a Maven Project.
4) Make sure MySQL Server is running.
5) Run the main class:
   library.LoginForm

Login examples from your database:
Student:
  ali@mail.com / 1234
  sara@mail.com / 1234
  ahmed@mail.com / 1234

Admin:
  admin@mail.com / admin123

Librarian:
  librarian@mail.com / lib123

Connected features:
- Login checks User + Student/Admin/Librarian tables.
- Manage Books inserts/updates/deletes from Book table.
- Borrowing page inserts into Borrow table and updates Book.is_available.
- Review page inserts into Review table.

Important:
- Your SQL file uses Book columns: title, author, year, category, is_available.
- The Java code was adjusted to match these exact columns.
