package com.library.library_app.models;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int yearPublished;
    private int availableCopies;

    public Book() {}

    public Book(int id, String title, String author, String isbn, String category, int yearPublished, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.yearPublished = yearPublished;
        this.availableCopies = availableCopies;
    }

    // Getters and Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public int getYearPublished() {return yearPublished;}
    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}

    public int getAvailableCopies() {return availableCopies;}
    public void setAvailableCopies(int availableCopies) {this.availableCopies = availableCopies;}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", category='" + category + '\'' +
                ", yearPublished=" + yearPublished +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
