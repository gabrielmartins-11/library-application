package com.library.library_app.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.library.library_app.models.Book;
import com.library.library_app.models.Member;
import com.library.library_app.models.Staff;
import com.library.library_app.models.BooksBorrowed;
import com.library.library_app.repositories.BookRepository;
import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.repositories.StaffRepository;
import com.library.library_app.repositories.BooksBorrowedRepository;

// Added imports for create/delete endpoints
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class DataViewController {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final StaffRepository staffRepo;
    private final BooksBorrowedRepository borrowedRepo;

    public DataViewController(BookRepository bookRepo, MemberRepository memberRepo, StaffRepository staffRepo, BooksBorrowedRepository borrowedRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.staffRepo = staffRepo;
        this.borrowedRepo = borrowedRepo;
    }

    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return bookRepo.getAllBooks();
    }

    @GetMapping("/api/members")
    public List<Member> getMembers() {
        return memberRepo.getAllMembers();
    }

    @GetMapping("/api/staff")
    public List<Staff> getStaff() {
        return staffRepo.getAllStaff();
    }

    @GetMapping("/api/books-borrowed")
    public List<BooksBorrowed> getBooksBorrowed() {
        return borrowedRepo.getAllBorrowedBooks();
    }

    // Add endpoints to create resources 

    @PostMapping("/api/members")
    public ResponseEntity<String> addMember(@RequestBody Member member) {
        int rows = memberRepo.addMember(member);
        if (rows > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Member added");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add member");
    }

    @PostMapping("/api/books")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        int rows = bookRepo.addBook(book);
        if (rows > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book");
    }

    @PostMapping("/api/staff")
    public ResponseEntity<String> addStaff(@RequestBody Staff staff) {
        int rows = staffRepo.addStaff(staff);
        if (rows > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Staff added");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add staff");
    }

    // Add endpoints to delete resources 

    @DeleteMapping("/api/members/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable("id") int id) {
        int rows = memberRepo.deleteMember(id);
        if (rows > 0) {
            return ResponseEntity.ok("Member deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        int rows = bookRepo.deleteBook(id);
        if (rows > 0) {
            return ResponseEntity.ok("Book deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
    }

    @DeleteMapping("/api/staff/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable("id") int id) {
        int rows = staffRepo.deleteStaff(id);
        if (rows > 0) {
            return ResponseEntity.ok("Staff deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");
    }
}
