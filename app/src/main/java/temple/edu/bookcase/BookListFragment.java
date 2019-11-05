package temple.edu.bookcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookListFragment extends Fragment {

    //private static final String ARG_PARAM1 = "param1";
    ListView listView;
    ArrayList<String> bookArrayList;
    String[] bookKey;
    String[] bookListArray;

    String title;
    Boolean isFirstAccess = true;

    OnFragmentInteractionListener mListener;

    public BookListFragment() {
        // Required empty public constructor
    }


    public static BookListFragment newInstance(ArrayList<String> bookListKey) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("book_key", bookListKey);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookArrayList = getArguments().getStringArrayList("book_key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_list, container, false);

        bookAdapter adapter = new bookAdapter(getActivity(), bookArrayList);
        listView = v.findViewById(R.id.fragListView);
        listView.setAdapter(adapter);
        //listAdapter = new ArrayAdapter<String>(this, R.layout.);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //title = listView.getItemAtPosition(position).toString();
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
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String x);
    }


}
