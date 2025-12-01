-- Clear tables before inserting
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE books_borrowed;
TRUNCATE TABLE staff;
TRUNCATE TABLE members;
TRUNCATE TABLE books;
SET FOREIGN_KEY_CHECKS = 1;

DELETE FROM books_borrowed;
DELETE FROM staff;
DELETE FROM members;
DELETE FROM books;

-- Insert sample books
INSERT INTO books (title, author, year_published, genre) VALUES
('1984', 'George Orwell', 1949, 'Dystopian'),
('To Kill a Mockingbird', 'Harper Lee', 1960, 'Fiction'),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 'Fiction'),
('Pride and Prejudice', 'Jane Austen', 1813, 'Romance'),
('The Catcher in the Rye', 'J.D. Salinger', 1951, 'Fiction'),
('Don Quixote', 'Miguel de Cervantes', 1615, 'Novel'),
('Moby Dick', 'Herman Melville', 1851, 'Fiction'),
('The Three Musketeers', 'Alexandre Dumas', 1844, 'Historical'),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 'Tragedy'),
('The Count of Monte Cristo', 'Alexandre Dumas', 1846, 'Historical'),
('A Farewell to Arms', 'Ernest Hemingway', 1929, 'Realism'),
('Of Mice and Men', 'John Steinbeck', 1937, 'Tragedy'),
('The Alchemist', 'Paulo Coelho', 1988, 'Fables'),
('The Hunger Games', 'Suzanne Collins', 2008, 'Fiction'),
('A Game of Thrones', 'George R.R. Martin', 1996, 'Fantasy');

-- Insert sample members
INSERT INTO members (first_name, last_name, email, fines) VALUES
('John', 'Doe', 'john.doe@gmail.com'),
('Jane', 'Smith', 'jane.smith@gmail.com'),
('Michael', 'Johnson', 'michael.johnson@gmail.com'),
('Sarah', 'Williams', 'sarah.williams@gmail.com'),
('Robert', 'Brown', 'robert.brown@gmail.com'),
('Vincent', 'Nguyen', 'vincent.nguyen@gmail.com'),
('Emily', 'Hu', 'emily.hu@gmail.com'),
('Richard', 'Baker', 'richard.baker@gmail.com'),
('Alan', 'Walker', 'alan.walker@gmail.com'),
('Evan', 'Campbell', 'evan.campbell@gmail.com'),
('Walter', 'White', 'walter.white@gmail.com'),
('Jennifer', 'Reyes', 'jennifer.reyes@gmail.com'),
('Elizabeth', 'White', 'elizabeth.white@gmail.com'),
('Sarah', 'Morgan', 'sarah.morgan@gmail.com'),
('Lisa', 'Ortiz', 'lisa.ortiz@gmail.com');

-- Insert sample staff
INSERT INTO staff (first_name, last_name, email) VALUES
('Alice', 'Johnson', 'alice.j@library.com'),
('Bob', 'Williams', 'bob.w@library.com'),
('Carol', 'Davis', 'carol.d@library.com'),
('John', 'Johnson', 'john.j@library.com'),
('Jeffrey', 'Trump', 'jeffrey.t@library.com'),
('Christine', 'Caufield', 'christine.c@library.com'),
('Mary', 'Magdalene', 'mary.m@library.com'),
('James', 'Bond', 'james.b@library.com'),
('Gabriel', 'Martins', 'gabriel.m@library.com'),
('Nancy', 'Smith', 'nancy.s@library.com'),
('John', 'Carlos', 'john.c@library.com'),
('Sabrina', 'Fernandez', 'sabrina.f@library.com'),
('Karly', 'Smith', 'karly.s@library.com'),
('Luciana', 'Lopez', 'luciana.l@library.com'),
('Samuel', 'Martinez', 'samuel.m@library.com'),
('Daniela', 'Rodriguez', 'daniela.r@library.com'),
('Mark', 'Thomas', 'mark.t@library.com'),
('William', 'Taylor', 'william.t@library.com'),
('Mary', 'Walker', 'mary.w@library.com'),
('Richard', 'Young', 'richard.y@library.com');

-- Insert sample borrow records
INSERT INTO books_borrowed (book_id, member_id, date_borrowed, return_date) VALUES
(1, 1, '2025-11-01', NULL),
(2, 3, '2025-11-05', '2025-12-05'),
(3, 6, '2025-11-10', NULL),
(4, 9, '2025-11-12', '2025-12-12'),
(5, 10, '2025-11-15', NULL),
(6, 5, '2025-11-18', '2025-12-18'),
(7, 14, '2025-11-20', '2025-12-20'),
(8, 6, '2025-11-20', NULL),
(9, 13, '2025-11-21', '2025-12-21'),
(10, 2, '2025-11-23', '2025-12-23'),
(11, 9, '2025-11-24', '2025-12-24'),
(12, 12, '2025-11-24', '2025-12-24'),
(13, 4, '2025-11-24', '2025-12-24'),
(14, 5, '2025-11-26', NULL),
(15, 11, '2025-11-27', '2025-12-27');
