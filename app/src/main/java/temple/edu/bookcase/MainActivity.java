package temple.edu.bookcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener {

    BookDetailsFragment fragdetail;
    //ArrayList<Book> bookNames;
    ArrayList bookNames = new ArrayList<Book>();


    // Query code
    TextView title;
    String titleName;
    Handler jSonHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                //title.setText("hey");
                //JSONObject bookJS = new JSONObject(msg.obj.toString());
                JSONArray jsArr = new JSONArray(msg.obj.toString());

                for(int i =0; i< jsArr.length();i++) {
                    //Book b = new Book(jsArr.getJSONObject(0).getString("title"));

                    //b.setTitle(jsArr.getJSONObject(0).getString("title"));
                    //bookNames.add(b);
                    //title.setText(jsArr.getJSONObject(0).getString("book_id"));
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

        // Query code
        //title = findViewById(R.id.title);

        Log.d("helpplease1234", "sup");

        new Thread() {
            @Override
            public void run() {
                URL url = null;
                try {
                    Log.d("helpplease123", "inside");
                    url = new URL("https://kamorris.com/lab/audlib/booksearch.php");



                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));
                    StringBuilder builder = new StringBuilder();
                    String response;

                    Log.d("helpplease123", "still inside");
                    while((response = reader.readLine()) != null){
                        builder.append(response);
                    }


                    Message msg = Message.obtain();
                    msg.obj = builder.toString();
                    Log.d("helpplease123", msg.obj.toString());


                    jSonHandler.sendMessage(msg);

                } catch (
                        Exception e) {
                    Log.d("helpplease123", "we dead");
                    e.printStackTrace();
                }
            }
        }.start();

         Book b = new Book("1");
        Book c = new Book("2");
        Book d = new Book("3");
        //b.setTitle("book");
        if (b!= null) {
            bookNames.add(b);
            bookNames.add(c);
            bookNames.add(d);
        }


        Boolean twoPane = (findViewById(R.id.frag2) != null);

        //String bookList[] = getResources().getStringArray(R.array.books);
        //ArrayList books = new ArrayList<String>();

        /*
        for (int i = 0; i < bookList.length - 1; i++) {
            books.add(bookList[i]);
        }
*/

        //Make listView
        Fragment frag = BookListFragment.newInstance(bookNames);
        fragdetail = new BookDetailsFragment();

        ViewPagerFragment bf = ViewPagerFragment.newInstance(bookNames);

        if (twoPane) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(bf)
                    .replace(R.id.frag1, frag)
                    .add(R.id.frag2, fragdetail)
                    .commitNow();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag1, bf)
                    .commitNow();
        }

    }
        @Override
        public void onFragmentInteraction (Book x){
            fragdetail.displayBook(x);
        }



}