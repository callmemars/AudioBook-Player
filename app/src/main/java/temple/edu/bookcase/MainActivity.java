package temple.edu.bookcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener {

    //Fragment fragdetail = BookDetailsFragment.newInstance("text");

    BookDetailsFragment fragdetail;
    Boolean isHorizontal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String bookList[] = getResources().getStringArray(R.array.books);
        ArrayList books = new ArrayList<String>();

        for(int i = 0;i < bookList.length-1;i++){
            books.add(bookList[i]);
        }

        //Make listView
        Fragment frag = BookListFragment.newInstance(books);
        fragdetail = new BookDetailsFragment();

        //Check the paniage:
        isHorizontal = findViewById(R.id.frag2) != null;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag1, frag)
                .commit();

        if(isHorizontal){
            Log.d("jamjam", "slim jim");

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag2, fragdetail)
                    .commit();
        }

    }

    @Override
    public void onFragmentInteraction(String s) {
        fragdetail.displayBook(s);
    }
}
