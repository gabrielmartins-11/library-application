-- Clear tables before inserting
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE books_borrowed;
TRUNCATE TABLE staff;
TRUNCATE TABLE members;
TRUNCATE TABLE books;
TRUNCATE TABLE overdue_books;
SET FOREIGN_KEY_CHECKS = 1;

DELETE FROM books_borrowed;
DELETE FROM staff;
DELETE FROM members;
DELETE FROM books;
DELETE FROM overdue_books;

-- Insert sample books (with isbn/category/available_copies)
INSERT INTO books (title, author, isbn, category, year_published, available_copies) VALUES
('1984', 'George Orwell', '9780451524935', 'Dystopian', 1949, 3),
('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'Fiction', 1960, 2),
('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'Fiction', 1925, 4),
('Pride and Prejudice', 'Jane Austen', '9781503290563', 'Romance', 1813, 2),
('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 'Fiction', 1951, 1),
('Don Quixote', 'Miguel de Cervantes', '9780060934347', 'Novel', 1615, 3),
('Moby Dick', 'Herman Melville', '9781503280786', 'Fiction', 1851, 2),
('The Three Musketeers', 'Alexandre Dumas', '9780140449266', 'Historical', 1844, 3),
('The Alchemist', 'Paulo Coelho', '9780061122415', 'Fables', 1988, 5),
('The Hunger Games', 'Suzanne Collins', '9780439023528', 'Fiction', 2008, 4),
('A Game of Thrones', 'George R.R. Martin', '9780553593716', 'Fantasy', 1996, 2),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '9780590353427', 'Fantasy', 1997, 4),
('The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'Fantasy', 1937, 3),
('The Da Vinci Code', 'Dan Brown', '9780307474278', 'Thriller', 2003, 5),
('Brave New World', 'Aldous Huxley', '9780060850525', 'Dystopian', 1932, 2);

-- Insert sample members (name, email, password, fines)
INSERT INTO members (name, email, password, fines) VALUES
('John Doe', 'john.doe@gmail.com', 'john123', 0.00),
('Jane Smith', 'jane.smith@gmail.com', 'jane123', 0.00),
('Michael Johnson', 'michael.johnson@gmail.com', 'michael123', 0.00),
('Sarah Williams', 'sarah.williams@gmail.com', 'sarah123', 0.00),
('Robert Brown', 'robert.brown@gmail.com', 'robert123', 0.00),
('Vincent Nguyen', 'vincent.nguyen@gmail.com', 'vincent123', 0.00),
('Emily Hu', 'emily.hu@gmail.com', 'emily123', 0.00),
('Richard Baker', 'richard.baker@gmail.com', 'richard123', 0.00),
('Alan Walker', 'alan.walker@gmail.com', 'alan123', 0.00),
('Evan Campbell', 'evan.campbell@gmail.com', 'evan123', 0.00),
('Walter White', 'walter.white@gmail.com', 'heisenberg', 0.00),
('Maria Garcia', 'maria.garcia@gmail.com', 'maria123', 0.00),
('David Kim', 'david.kim@gmail.com', 'david123', 0.00),
('Sophia Patel', 'sophia.patel@gmail.com', 'sophia123', 0.00),
('Liam Turner', 'liam.turner@gmail.com', 'liam123', 0.00),
('Olivia Brooks', 'olivia.brooks@gmail.com', 'olivia123', 0.00);

-- Insert sample staff (name, email, password, role)
INSERT INTO staff (name, email, password, role) VALUES
('Alice Johnson', 'alice.j@library.com', 'alicepw', 'ADMIN'),
('Bob Williams', 'bob.w@library.com', 'bobpw', 'LIBRARIAN'),
('Carol Davis', 'carol.d@library.com', 'carolpw', 'LIBRARIAN'),
('Daniel Harris', 'daniel.h@library.com', 'danielpw', 'ADMIN'),
('Laura Martinez', 'laura.m@library.com', 'laurapw', 'LIBRARIAN'),
('Peter Wilson', 'peter.w@library.com', 'peterpw', 'LIBRARIAN'),
('Stephanie Young', 'stephanie.y@library.com', 'stephpw', 'ADMIN'),
('Kevin Roberts', 'kevin.r@library.com', 'kevinpw', 'LIBRARIAN'),
('Nina Lopez', 'nina.l@library.com', 'ninapw', 'LIBRARIAN'),
('Thomas Gray', 'thomas.g@library.com', 'thomaspw', 'ADMIN'),
('Olivia Reed', 'olivia.r@library.com', 'oliviapw', 'LIBRARIAN'),
('Patrick Hughes', 'patrick.h@library.com', 'patrickpw', 'LIBRARIAN'),
('Rachel Scott', 'rachel.s@library.com', 'rachelpw', 'ADMIN'),
('Samuel King', 'samuel.k@library.com', 'samuelpw', 'LIBRARIAN'),
('Diana Foster', 'diana.f@library.com', 'dianapw', 'LIBRARIAN');

-- Insert sample borrow records (borrow_date, due_date, return_date)
INSERT INTO books_borrowed (book_id, member_id, borrow_date, due_date, return_date) VALUES
(1, 1, '2025-11-20', '2025-12-04', NULL),
(2, 3, '2025-11-18', '2025-12-02', '2025-12-02'),
(3, 6, '2025-11-22', '2025-12-06', NULL),
(4, 9, '2025-11-15', '2025-11-29', '2025-11-29'),
(5, 2, '2025-11-19', '2025-12-03', NULL),
(6, 4, '2025-11-10', '2025-11-24', '2025-11-23'),
(7, 5, '2025-11-25', '2025-12-09', NULL),
(8, 7, '2025-11-12', '2025-11-26', '2025-11-26'),
(9, 8, '2025-11-21', '2025-12-05', NULL),
(10, 10, '2025-11-14', '2025-11-28', '2025-11-27'),
(11, 11, '2025-11-23', '2025-12-07', NULL),
(12, 12, '2025-11-17', '2025-12-01', '2025-12-01'),
(13, 13, '2025-11-11', '2025-11-25', NULL),
(14, 14, '2025-11-26', '2025-12-10', NULL),
(15, 15, '2025-11-09', '2025-11-23', '2025-11-22');

-- Insert sample overdue books
INSERT INTO overdue_books (book_id, member_id, due_date, fine_amount) VALUES
(1, 2, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1.50),
(3, 4, DATE_SUB(CURDATE(), INTERVAL 10 DAY), 5.00),
(5, 6, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 1.00),
(7, 8, DATE_SUB(CURDATE(), INTERVAL 20 DAY), 10.00),
(9, 10, DATE_SUB(CURDATE(), INTERVAL 7 DAY), 3.50),
(11, 1, DATE_SUB(CURDATE(), INTERVAL 15 DAY), 7.50),
(1, 3, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 0.50),
(2, 1, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 2.50),
(3, 2, DATE_SUB(CURDATE(), INTERVAL 12 DAY), 6.00),
(6, 5, DATE_SUB(CURDATE(), INTERVAL 9 DAY), 4.50),
(8, 7, DATE_SUB(CURDATE(), INTERVAL 14 DAY), 7.00),
(10, 9, DATE_SUB(CURDATE(), INTERVAL 11 DAY), 5.50),
(3, 11, DATE_SUB(CURDATE(), INTERVAL 4 DAY), 2.00),
(1, 10, DATE_SUB(CURDATE(), INTERVAL 6 DAY), 3.00),
(2, 2, DATE_SUB(CURDATE(), INTERVAL 8 DAY), 4.00);
