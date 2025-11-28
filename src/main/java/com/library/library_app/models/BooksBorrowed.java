package com.library.library_app.models;

import java.time.LocalDate;

public class BooksBorrowed {
    private int id;
    private int bookId;
    private int memberId;
    private LocalDate dateBorrowed;
    private LocalDate returnDate;

    public BooksBorrowed() {}

    public BooksBorrowed(int id, int bookId, int memberId, LocalDate dateBorrowed, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.dateBorrowed = dateBorrowed;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(LocalDate dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BooksBorrowed{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", memberId=" + memberId +
                ", dateBorrowed=" + dateBorrowed +
                ", returnDate=" + returnDate +
                '}';
    }
}
