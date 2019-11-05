package temple.edu.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener {

    //Fragment fragdetail = BookDetailsFragment.newInstance("text");

    BookDetailsFragment fragdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean twoPane = (findViewById(R.id.frag2) != null);

        String bookList[] = getResources().getStringArray(R.array.books);
        ArrayList books = new ArrayList<String>();

        for(int i = 0;i < bookList.length-1;i++){
            books.add(bookList[i]);
        }

        //Make listView
        Fragment frag = BookListFragment.newInstance(books);
        fragdetail = new BookDetailsFragment();

        ViewPagerFragment bf = ViewPagerFragment.newInstance(books);

        if(twoPane) {
            getSupportFragmentManager()
                    .beginTransaction()

                    .remove(bf)

                    .replace(R.id.frag1, frag)
                    .add(R.id.frag2, fragdetail)
                    .commitNow();
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()

                    .replace(R.id.frag1, bf)
                    .commitNow();
        }
    }
    @Override
    public void onFragmentInteraction(String x) {
        fragdetail.displayBook(x);
    }
}
