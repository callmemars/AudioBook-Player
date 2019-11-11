package temple.edu.bookcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener {

    FragmentManager fm;
    BookDetailsFragment fragdetail;
    Boolean twoPane;

    ArrayList<Book> bookNames = new ArrayList<>();
    ArrayList<Book> searchNames = new ArrayList<>();

    ViewPagerFragment bf;
    Fragment frag;
    EditText search;
    String searchText = "";
    Button button;

    Boolean isTitle = false;
    Boolean isAuthor = false;
    Boolean isYear = false;
    RadioButton title;
    RadioButton author;


    Handler jSonHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                JSONArray jsArr = new JSONArray(msg.obj.toString());
                for (int i = 0; i < jsArr.length(); i++) {
                    Book b = new Book(jsArr.getJSONObject(i).getString("title"));
                    b.setAuthor(jsArr.getJSONObject(i).getString("author"));
                    b.setPublished(jsArr.getJSONObject(i).getString("published"));
                    b.setCoverURL(jsArr.getJSONObject(i).getString("cover_url"));
                    bookNames.add(b);
                    updateViewPager();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        // Search Ids
        search = findViewById(R.id.searchText);
        button = findViewById((R.id.button));
        title = findViewById(R.id.titleRadio);
        author = findViewById(R.id.author);

        // Gets fragment information after restart
        Fragment f1 = fm.findFragmentById(R.id.frag1);

        // Grabs currently displayed panes
        ViewPagerFragment vpFrag = null;
        BookListFragment bfFrag = null;

        if (f1 instanceof ViewPagerFragment) {
            vpFrag = (ViewPagerFragment) fm.findFragmentById(R.id.frag1);
            if (vpFrag != null) {
                bookNames = vpFrag.getCurrentList();
            }
        } else if (f1 instanceof BookDetailsFragment) {
            bfFrag = (BookListFragment) fm.findFragmentById(R.id.frag1);
            if (bfFrag != null) {
                bookNames = bfFrag.getCurrentList();
            }

        }
        // Check how many panes there are
         twoPane = (findViewById(R.id.frag2) != null);

        // Query code
        if (vpFrag == null && bfFrag == null) {
            new Thread() {
                @Override
                public void run() {
                    URL url = null;
                    try {
                        url = new URL("https://kamorris.com/lab/audlib/booksearch.php");

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        url.openStream()));
                        StringBuilder builder = new StringBuilder();
                        String response;

                        Log.d("helpplease123", "still inside");
                        while ((response = reader.readLine()) != null) {
                            builder.append(response);
                        }

                        Message msg = Message.obtain();
                        msg.obj = builder.toString();
                        Log.d("helpplease123", msg.obj.toString());

                        jSonHandler.sendMessage(msg);

                        reader.close();
                    } catch (
                            Exception e) {
                        Log.d("helpplease123", "we dead");
                        e.printStackTrace();
                    }
                }
            }.start();
        }


        //Manual book test code
        /*
        Book b = new Book("hey");
        Book c = new Book("hey2");
        Book d = new Book("hey3");
        bookNames.add(b);
        bookNames.add(c);
        bookNames.add(d);
*/
        //Make listView
        /* Put this into a method
        frag = BookListFragment.newInstance(bookNames);
        fragdetail = new BookDetailsFragment();

        bf = ViewPagerFragment.newInstance(bookNames);



        if (twoPane) {
            fm.beginTransaction()
                    .replace(R.id.frag1, frag)
                    .replace(R.id.frag2, fragdetail)
                    .commitNow();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag1, bf)
                    .remove(fragdetail)
                    .commitNow();
        }
*/

        changeFragments(bookNames);

        // Search function
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (search.getText().toString() != "") {
                        searchNames.clear();
                        searchText = search.getText().toString();

                        Log.d("hy", searchText + " searching..");

                        if (isAuthor) {
                            for (int i = 0; i < bookNames.size(); i++) {

                                if (bookNames.get(i).getAuthor().equals(searchText)) {
                                    searchNames.add(bookNames.get(i));
                                    Log.d("hy", bookNames.get(i).getAuthor() + " this exists!");
                                }
                            }
                        } else if (isYear) {
                            for (int i = 0; i < bookNames.size(); i++) {

                                if (bookNames.get(i).getPublished().equals(searchText)) {
                                    searchNames.add(bookNames.get(i));
                                }
                            }
                        } else if (isTitle) {
                            for (int i = 0; i < bookNames.size(); i++) {

                                if (bookNames.get(i).getTitle().equals(searchText)) {
                                    searchNames.add(bookNames.get(i));
                                }
                            }
                        }
                    }
                    if (!(searchText.equals(""))){
                        changeFragments(searchNames);
                    }
                    else
                        changeFragments(bookNames);
                }
            });
        }
    }

        @Override
        public void onFragmentInteraction (Book x){
            fragdetail.displayBook(x);
        }

// Update view pager when query finishes
    public void updateViewPager (){
        if (bf.pager != null)
            bf.pager.getAdapter().notifyDataSetChanged();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

         isTitle = false;
         isAuthor = false;
         isYear = false;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.titleRadio:
                if (checked)
                    isTitle = true;
                    break;
            case R.id.author:
                if (checked)
                    isAuthor = true;
                    break;
            case R.id.yearPublished:
                if (checked)
                    isYear = true;
                    break;
        }
    }
    public void changeFragments(ArrayList<Book> currentBook){
        frag = BookListFragment.newInstance(currentBook);
        fragdetail = new BookDetailsFragment();

        bf = ViewPagerFragment.newInstance(currentBook);

        if (twoPane) {
            fm.beginTransaction()
                    .replace(R.id.frag1, frag)
                    .replace(R.id.frag2, fragdetail)
                    .commitNow();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag1, bf)
                    .remove(fragdetail)
                    .commitNow();
        }

    }

}