package temple.edu.bookcase;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.text.TextUtils.isEmpty;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    //private String mParam1;
    String bookTitle;
    TextView textView;


    public BookDetailsFragment() {
        // Required empty public constructor
    }
/*
    public static BookDetailsFragment newInstance(String bookTitle) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putString("bookKey", bookTitle);
        fragment.setArguments(args);

        return fragment;


 */
    public static BookDetailsFragment newInstance(String x) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putString("bookKey", x);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookTitle= getArguments().getString("bookKey");
        }
    }

    public void displayBook(String x){

        if (textView != null) {
            textView.setText(x);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        textView = (TextView) v.findViewById(R.id.title);
        textView.setTextSize(33);

        if (bookTitle != null) {
            displayBook(bookTitle);
        }
        return v;

    }

}
