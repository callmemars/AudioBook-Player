package temple.edu.bookcase;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {


    ViewPager pager;
    ArrayList<Book> bookArrayList;

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    public static ViewPagerFragment newInstance(ArrayList<Book> bookListKey) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("book_key", bookListKey);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        pager = (ViewPager) v.findViewById(R.id.pager);

        pager.setAdapter(buildAdapter());

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookArrayList = getArguments().getParcelableArrayList("book_key");
        }
    }

    private PagerAdapter buildAdapter() {
        PagerAdapter p = new temple.edu.bookcase.PagerAdapter(getChildFragmentManager(), bookArrayList);
        return (p);
    }

}
