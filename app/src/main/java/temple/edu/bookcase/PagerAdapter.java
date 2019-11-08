package temple.edu.bookcase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Book> bookList;

     public PagerAdapter(FragmentManager mgr, ArrayList<Book>  b){
        super (mgr);
        this.bookList = b;

    }

    public Fragment getItem(int position){
            return BookDetailsFragment.newInstance(bookList.get(position));
    }

    @Override
    public int getCount() {
        return bookList.size();
    }


}
