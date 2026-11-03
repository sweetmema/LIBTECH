DROP DATABASE IF EXISTS LIBTCH;
CREATE DATABASE LIBTCH;
USE LIBTCH;

DROP USER IF EXISTS 'lib_user'@'localhost';
CREATE USER 'lib_user'@'localhost' IDENTIFIED BY '123456';
GRANT SELECT, INSERT, UPDATE, DELETE ON LIBTCH.* TO 'lib_user'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE Student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Librarian (
    librarian_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100),
    year INT,
    category VARCHAR(100),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE Borrow (
    borrow_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    book_id INT,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    book_id INT,
    rating INT,
    comment TEXT,
    FOREIGN KEY (student_id) REFERENCES Student(student_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

CREATE TABLE Manages (
    librarian_id INT,
    book_id INT,
    PRIMARY KEY (librarian_id, book_id),
    FOREIGN KEY (librarian_id) REFERENCES Librarian(librarian_id),
    FOREIGN KEY (book_id) REFERENCES Book(book_id)
);

INSERT INTO User (name, email, password) VALUES
('Ali Ahmed', 'ali@mail.com', '1234'),
('Sara Khalid', 'sara@mail.com', '1234'),
('Ahmed Saleh', 'ahmed@mail.com', '1234'),
('Nora Fahad', 'nora@mail.com', '1234'),
('Omar Saad', 'omar@mail.com', '1234'),
('Admin User', 'admin@mail.com', 'admin123'),
('Librarian User', 'librarian@mail.com', 'lib123');

INSERT INTO Student (user_id) VALUES (1), (2), (3), (4), (5);
INSERT INTO Admin (user_id) VALUES (6);
INSERT INTO Librarian (user_id) VALUES (7);

INSERT INTO Book (title, author, year, category, is_available) VALUES
('Clean Code', 'Robert C. Martin', 2008, 'Programming', TRUE),
('The Pragmatic Programmer', 'Andrew Hunt', 1999, 'Programming', TRUE),
('Java Programming', 'Herbert Schildt', 2021, 'Programming', TRUE),
('Effective Java', 'Joshua Bloch', 2018, 'Programming', TRUE),
('Head First Java', 'Kathy Sierra', 2022, 'Programming', TRUE),
('Python Crash Course', 'Eric Matthes', 2023, 'Programming', TRUE),
('Automate the Boring Stuff with Python', 'Al Sweigart', 2019, 'Programming', TRUE),
('C Programming Language', 'Brian Kernighan', 1988, 'Programming', TRUE),
('Introduction to Algorithms', 'Thomas Cormen', 2022, 'Programming', TRUE),
('Design Patterns', 'Erich Gamma', 1994, 'Programming', TRUE),
('Database System Concepts', 'Abraham Silberschatz', 2019, 'Database', TRUE),
('Fundamentals of Database Systems', 'Ramez Elmasri', 2016, 'Database', TRUE),
('SQL for Beginners', 'John Russel', 2020, 'Database', TRUE),
('Learning SQL', 'Alan Beaulieu', 2020, 'Database', TRUE),
('MySQL Cookbook', 'Paul DuBois', 2021, 'Database', TRUE),
('Database Design for Mere Mortals', 'Michael Hernandez', 2013, 'Database', TRUE),
('NoSQL Distilled', 'Pramod Sadalage', 2012, 'Database', TRUE),
('Data Modeling Made Simple', 'Steve Hoberman', 2015, 'Database', TRUE),
('SQL Performance Explained', 'Markus Winand', 2019, 'Database', TRUE),
('High Performance MySQL', 'Silvia Botros', 2021, 'Database', TRUE),
('Computer Networks', 'Andrew Tanenbaum', 2021, 'Networking', TRUE),
('Data Communications and Networking', 'Behrouz Forouzan', 2012, 'Networking', TRUE),
('Network Warrior', 'Gary Donahue', 2011, 'Networking', TRUE),
('CCNA Official Cert Guide', 'Wendell Odom', 2024, 'Networking', TRUE),
('TCP IP Illustrated', 'W. Richard Stevens', 2011, 'Networking', TRUE),
('Routing TCP IP', 'Jeff Doyle', 2016, 'Networking', TRUE),
('Network Security Essentials', 'William Stallings', 2020, 'Networking', TRUE),
('Wireless Communications', 'Andrea Goldsmith', 2005, 'Networking', TRUE),
('5G NR', 'Sassan Ahmadi', 2019, 'Networking', TRUE),
('Cloud Networking', 'Gary Lee', 2014, 'Networking', TRUE),
('Cybersecurity Essentials', 'Charles Brooks', 2018, 'Cyber Security', TRUE),
('The Web Application Hacker Handbook', 'Dafydd Stuttard', 2011, 'Cyber Security', TRUE),
('Hacking The Art of Exploitation', 'Jon Erickson', 2008, 'Cyber Security', TRUE),
('Computer Security', 'William Stallings', 2018, 'Cyber Security', TRUE),
('Practical Malware Analysis', 'Michael Sikorski', 2012, 'Cyber Security', TRUE),
('Security Engineering', 'Ross Anderson', 2020, 'Cyber Security', TRUE),
('Blue Team Handbook', 'Don Murdoch', 2014, 'Cyber Security', TRUE),
('Penetration Testing', 'Georgia Weidman', 2014, 'Cyber Security', TRUE),
('Applied Cryptography', 'Bruce Schneier', 2015, 'Cyber Security', TRUE),
('Linux Basics for Hackers', 'OccupyTheWeb', 2018, 'Cyber Security', TRUE),
('Artificial Intelligence A Modern Approach', 'Stuart Russell', 2020, 'Artificial Intelligence', TRUE),
('Deep Learning', 'Ian Goodfellow', 2016, 'Artificial Intelligence', TRUE),
('Hands On Machine Learning', 'Aurelien Geron', 2022, 'Artificial Intelligence', TRUE),
('Pattern Recognition and Machine Learning', 'Christopher Bishop', 2006, 'Artificial Intelligence', TRUE),
('Machine Learning Yearning', 'Andrew Ng', 2018, 'Artificial Intelligence', TRUE),
('Natural Language Processing with Python', 'Steven Bird', 2009, 'Artificial Intelligence', TRUE),
('Reinforcement Learning', 'Richard Sutton', 2018, 'Artificial Intelligence', TRUE),
('Generative Deep Learning', 'David Foster', 2023, 'Artificial Intelligence', TRUE),
('AI Superpowers', 'Kai Fu Lee', 2018, 'Artificial Intelligence', TRUE),
('The Hundred Page Machine Learning Book', 'Andriy Burkov', 2019, 'Artificial Intelligence', TRUE),
('Digital Design', 'M. Morris Mano', 2017, 'Engineering', TRUE),
('Electric Circuits', 'James Nilsson', 2018, 'Engineering', TRUE),
('Engineering Circuit Analysis', 'William Hayt', 2019, 'Engineering', TRUE),
('Power System Analysis', 'John Grainger', 1994, 'Engineering', TRUE),
('Control Systems Engineering', 'Norman Nise', 2020, 'Engineering', TRUE),
('Signals and Systems', 'Alan Oppenheim', 1996, 'Engineering', TRUE),
('Microelectronic Circuits', 'Adel Sedra', 2020, 'Engineering', TRUE),
('Electrical Machines', 'P. S. Bimbhra', 2011, 'Engineering', TRUE),
('Power Electronics', 'Muhammad Rashid', 2017, 'Engineering', TRUE),
('Communication Systems', 'Simon Haykin', 2009, 'Engineering', TRUE),
('Atomic Habits', 'James Clear', 2018, 'Self Development', TRUE),
('Deep Work', 'Cal Newport', 2016, 'Self Development', TRUE),
('The 7 Habits of Highly Effective People', 'Stephen Covey', 1989, 'Self Development', TRUE),
('Mindset', 'Carol Dweck', 2006, 'Self Development', TRUE),
('Think and Grow Rich', 'Napoleon Hill', 1937, 'Self Development', TRUE),
('Rich Dad Poor Dad', 'Robert Kiyosaki', 1997, 'Self Development', TRUE),
('Start with Why', 'Simon Sinek', 2009, 'Self Development', TRUE),
('The Power of Now', 'Eckhart Tolle', 1997, 'Self Development', TRUE),
('Essentialism', 'Greg McKeown', 2014, 'Self Development', TRUE),
('Make It Stick', 'Peter Brown', 2014, 'Self Development', TRUE),
('Harry Potter and the Sorcerer Stone', 'J.K. Rowling', 1997, 'Novel', TRUE),
('The Alchemist', 'Paulo Coelho', 1988, 'Novel', TRUE),
('1984', 'George Orwell', 1949, 'Novel', TRUE),
('Animal Farm', 'George Orwell', 1945, 'Novel', TRUE),
('Pride and Prejudice', 'Jane Austen', 1813, 'Novel', TRUE),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 'Novel', TRUE),
('To Kill a Mockingbird', 'Harper Lee', 1960, 'Novel', TRUE),
('The Hobbit', 'J.R.R. Tolkien', 1937, 'Novel', TRUE),
('The Little Prince', 'Antoine de Saint Exupery', 1943, 'Novel', TRUE),
('Moby Dick', 'Herman Melville', 1851, 'Novel', TRUE),
('Principles of Management', 'OpenStax', 2019, 'Business', TRUE),
('Marketing Management', 'Philip Kotler', 2016, 'Business', TRUE),
('Supply Chain Management', 'Sunil Chopra', 2019, 'Business', TRUE),
('Business Analytics', 'James Evans', 2020, 'Business', TRUE),
('Financial Accounting', 'Jerry Weygandt', 2021, 'Business', TRUE),
('Operations Management', 'William Stevenson', 2021, 'Business', TRUE),
('Entrepreneurship', 'Robert Hisrich', 2020, 'Business', TRUE),
('The Lean Startup', 'Eric Ries', 2011, 'Business', TRUE),
('Good to Great', 'Jim Collins', 2001, 'Business', TRUE),
('Blue Ocean Strategy', 'W. Chan Kim', 2015, 'Business', TRUE),
('Calculus', 'James Stewart', 2015, 'Mathematics', TRUE),
('Linear Algebra Done Right', 'Sheldon Axler', 2015, 'Mathematics', TRUE),
('Discrete Mathematics', 'Kenneth Rosen', 2019, 'Mathematics', TRUE),
('Probability and Statistics', 'Morris DeGroot', 2012, 'Mathematics', TRUE),
('Concrete Mathematics', 'Ronald Graham', 1994, 'Mathematics', TRUE),
('Introduction to Probability', 'Joseph Blitzstein', 2019, 'Mathematics', TRUE),
('Advanced Engineering Mathematics', 'Erwin Kreyszig', 2011, 'Mathematics', TRUE),
('Numerical Methods for Engineers', 'Steven Chapra', 2020, 'Mathematics', TRUE),
('Linear Algebra and Its Applications', 'Gilbert Strang', 2016, 'Mathematics', TRUE),
('Statistics for Engineers', 'Douglas Montgomery', 2014, 'Mathematics', TRUE);

INSERT INTO Borrow (student_id, book_id, borrow_date, return_date) VALUES
(1, 1, '2026-05-01', NULL),
(2, 4, '2026-05-03', NULL),
(3, 11, '2026-05-04', '2026-05-12'),
(4, 31, '2026-05-05', NULL),
(5, 61, '2026-05-06', '2026-05-14');

UPDATE Book SET is_available = FALSE WHERE book_id IN (1, 4, 31);

INSERT INTO Review (student_id, book_id, rating, comment) VALUES
(1, 1, 5, 'Excellent programming book.'),
(2, 4, 5, 'Very useful for Java.'),
(3, 11, 4, 'Good database reference.'),
(4, 31, 4, 'Helpful for cybersecurity basics.'),
(5, 61, 5, 'Great for building habits.');

INSERT INTO Manages (librarian_id, book_id)
SELECT 1, book_id FROM Book;
