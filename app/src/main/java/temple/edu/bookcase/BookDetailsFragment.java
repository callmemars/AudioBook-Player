package temple.edu.bookcase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import static android.text.TextUtils.isEmpty;

public class BookDetailsFragment extends Fragment {

    String bookTitle;
    TextView textView;

    Book book;


    public BookDetailsFragment() {
    }

    public static BookDetailsFragment newInstance(Book x) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("bookKey", x);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //bookTitle= getArguments().getString("bookKey");
            book = (Book)getArguments().getParcelable("bookKey");
            bookTitle = book.getTitle();
        }
    }

    public void displayBook(Book x){

        if (textView != null) {
            textView.setText(x.getTitle() + " - " + x.getAuthor() + " (" + x.getPublished()
            + ")");
        }
        else
            textView.setText("null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        textView = (TextView) v.findViewById(R.id.title);
        textView.setTextSize(20);

        if (bookTitle != null) {
            displayBook(book);
        }
        return v;
    }

}
