package temple.edu.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListFragment extends Fragment {

    ListView listView;
    ArrayList<Book> bookArrayList;
    OnFragmentInteractionListener mListener;

    public BookListFragment() {
    }


    public static BookListFragment newInstance(ArrayList<Book> bookListKey) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("book_key", bookListKey);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookArrayList = getArguments().getParcelableArrayList("book_key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);

        bookAdapter adapter = new bookAdapter(getActivity(), bookArrayList);
        listView = v.findViewById(R.id.fragListView);
        listView.setAdapter(adapter);

        // Returns what was clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mListener.onFragmentInteraction(bookArrayList.get(position));
                }
        }
        );
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {

        Log.d("hey", "disconnected");
        super.onDetach();

        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Book x);
    }

    public ArrayList<Book> getCurrentList(){
        return this.bookArrayList;
    }

}
