package temple.edu.bookcase;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    int book;
    String title;
    String author;
    String published;
    String coverURL;
    int id;
    int duration;

    public Book(Parcel in) {

        title = in.readString();
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

    public void setDuration(int duration){
        this.duration = duration;
    }

    public int getDuration (){
        return duration;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
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
        dest.writeInt(duration);
        dest.writeInt(id);
    }
}
