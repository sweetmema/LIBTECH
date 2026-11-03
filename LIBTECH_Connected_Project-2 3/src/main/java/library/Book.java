package library;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private int year;
    private String category;
    private boolean available;

    public Book(int bookId, String title, String author, int year, String category, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.available = available;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }

    public void setBookId(int bookId) { this.bookId = bookId; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(int year) { this.year = year; }
    public void setCategory(String category) { this.category = category; }
    public void setAvailable(boolean available) { this.available = available; }
}
