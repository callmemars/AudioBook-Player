package temple.edu.bookcase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Parcelable {

    int book;
    String title;
    String author;
    String published;
    String coverURL;

    int duration;


    public Book(Parcel in) {
        //book = in.readInt();
        title = in.readString();
        //author = in.readString();
        //published = in.readInt();
        //coverURL = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(String t){
        title = t;
    }


    public void setBook(int book) {
        this.book = book;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
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

    public String getPublished() {
        return published;
    }

    public String getCoverURL() {
        return coverURL;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(book);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(published);
        dest.writeString(coverURL);
    }
}
