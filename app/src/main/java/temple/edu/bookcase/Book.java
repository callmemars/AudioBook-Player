package temple.edu.bookcase;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    int book;
    String title;
    String author;
    int published;
    String coverURL;

    ArrayList<Book> bookList;

    public void setBook(int book) {
        this.book = book;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public int getBook() {
        return book;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublished() {
        return published;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }
}
