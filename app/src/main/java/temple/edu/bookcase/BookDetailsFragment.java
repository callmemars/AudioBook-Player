package temple.edu.bookcase;


import android.content.Context;
import android.os.Bundle;

import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import static android.text.TextUtils.isEmpty;

public class BookDetailsFragment extends Fragment {

    String bookTitle;
    TextView textView;
    ImageView bookImage;

    // Audio book vars
    OnBookPlay parentFrag;
    Button playButton;

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

        Picasso.get().load(x.coverURL).into(bookImage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        textView = (TextView) v.findViewById(R.id.title);
        bookImage = v.findViewById(R.id.image);

        textView.setTextSize(20);


        if (bookTitle != null) {
            displayBook(book);
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookDetailsFragment.OnBookPlay) {
            parentFrag = (BookDetailsFragment.OnBookPlay) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookPlay interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentFrag = null;
    }


    public interface OnBookPlay {
        void playBoo
    }

}
