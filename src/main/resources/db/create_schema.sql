CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Create books table (updated)
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    category VARCHAR(100) NOT NULL,
    year_published SMALLINT NOT NULL,
    available_copies INT NOT NULL DEFAULT 1,
    INDEX idx_books_title (title),
    INDEX idx_books_author (author),
    INDEX idx_books_isbn (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create members table (updated)
CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fines DECIMAL(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create books_borrowed table (updated)
CREATE TABLE IF NOT EXISTS books_borrowed (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    INDEX idx_bb_book_id (book_id),
    INDEX idx_bb_member_id (member_id),
    CONSTRAINT fk_bb_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_bb_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create staff table (updated)
CREATE TABLE IF NOT EXISTS staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','LIBRARIAN') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Overdue_books table 
CREATE TABLE IF NOT EXISTS overdue_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    due_date DATE NOT NULL,
    fine_amount DECIMAL(10,2) NOT NULL,
    INDEX idx_ob_book_id (book_id),
    INDEX idx_ob_member_id (member_id),
    CONSTRAINT fk_ob_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    CONSTRAINT fk_ob_member FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;