package com.cpuh.model;

public class Library {

    private int libraryId;

    private String bookId;
    private String bookTitle;
    private String author;
    private String category;
    private String isbn;

    private int quantity;
    private int availableQuantity;

    private String status;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Library() {
    }


    public Library(
            String bookId,
            String bookTitle,
            String author,
            String category,
            String isbn,
            int quantity,
            int availableQuantity,
            String status
    ) {

        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.status = status;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getLibraryId() {
        return libraryId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public String getStatus() {
        return status;
    }


    // ==========================================
    // SETTERS
    // ==========================================

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}