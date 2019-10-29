package temple.edu.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String bookList[] = getResources().getStringArray(R.array.books);
        ArrayList books = new ArrayList<String>();

        for(int i = 0;i < bookList.length-1;i++){
            books.add(bookList[i]);
        }

        //Test code for listView
        Fragment frag = BookListFragment.newInstance(books);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag1, frag)
                .commit();


    }

    @Override
    public void onFragmentInteraction(String s) {

    }
}
