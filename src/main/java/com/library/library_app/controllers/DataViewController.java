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

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepo.getAllBooks();
    }

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberRepo.getAllMembers();
    }

    @GetMapping("/staff")
    public List<Staff> getStaff() {
        return staffRepo.getAllStaff();
    }

    @GetMapping("/books-borrowed")
    public List<BooksBorrowed> getBooksBorrowed() {
        return borrowedRepo.getAllBorrowedBooks();
    }
}
