package temple.edu.bookcase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity
        implements BookListFragment.OnFragmentInteractionListener, BookDetailsFragment.OnBookPlay {

    FragmentManager fm;
    BookDetailsFragment fragdetail;
    Boolean twoPane;

    ArrayList<Book> bookNames = new ArrayList<>();
    ArrayList<Book> searchNames = new ArrayList<>();

    ViewPagerFragment bf;
    Fragment frag;
    EditText search;
    String searchText = "";

    private static int currentDuration;
    private static int currentProgress;

    Button button;

    // Audiobook vars
    Button pauseButton;
    Button stopButton;

    AudiobookService.MediaControlBinder servBinder;
    SeekBar seekbar;
    Boolean isConnected;
    Intent audioBook;

    Boolean paused;
    Boolean playing;

    Boolean isTitle = false;
    Boolean isAuthor = false;
    Boolean isYear = false;
    RadioButton title;
    RadioButton author;

    ServiceConnection sv = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("did service", "service connected!");
            isConnected = true;
            servBinder = (AudiobookService.MediaControlBinder) service;
            servBinder.setProgressHandler(seekHandler);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("did service", "service not connected");

            servBinder = null;
            isConnected = false;

        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbindService(sv);
    }

    Handler seekHandler = new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.d("bannan", "ban");
            if (msg.obj != null) {
                seekbar = findViewById(R.id.seekBar);

                if (seekbar != null) {
                    AudiobookService.BookProgress bookSeek = (AudiobookService.BookProgress) msg.obj;
                    Log.d("hey", "Progress is: " + String.valueOf(bookSeek.getProgress()));

                    if (isConnected) {
                        //servBinder.stop();

                        if (seekbar != null && (bookSeek.getProgress() < MainActivity.currentDuration)) {
                            seekbar.setProgress(bookSeek.getProgress());
                            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if (fromUser && isConnected) {

                                        servBinder.seekTo(progress);
                                        currentProgress = progress;
                                    } else {
                                        currentProgress = progress;
                                    }
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                        }
                    } else if (seekbar != null && (bookSeek.getProgress() == currentDuration)) {
                        if (isConnected) {
                            servBinder.stop(); // Stop the book, which changes the playingState in AudiobookService accordingly
                            //Title = "";
                            //currentTitleText.setText("");
                            seekbar.setProgress(0);
                        }
                    }
                }

            }
            return true;
        }
    });





            // Gets the book data
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
                    b.setId(jsArr.getJSONObject(i).getInt("book_id"));
                    b.setDuration(jsArr.getJSONObject(i).getInt("duration"));

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
        seekbar = findViewById(R.id.seekBar);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        seekbar.setProgress(MainActivity.currentProgress);
        seekbar.setMax(currentDuration);

        audioBook = new Intent(this, AudiobookService.class);
        bindService(audioBook, sv, Context.BIND_AUTO_CREATE);

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
        } else if (f1 instanceof BookListFragment) {
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
        changeFragments(bookNames);

        // Search function
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("hy", searchText + " searching for <");

                    if (search.getText().toString() != "") {
                        searchNames.clear();
                        searchText = search.getText().toString();

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


        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isConnected && !paused){
                    servBinder.pause();
                    playing = false;
                    paused = true;
                } else if (isConnected) {
                    servBinder.pause();
                    playing = true;
                    paused = false;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(isConnected) {
                    servBinder.stop();

                    playing = false;
                    paused = false;

                    seekbar.setProgress(0);
                    stopService(audioBook);
                }
            }
        });
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

    // To see if the user is searching for the title, author, or year published.
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

    @Override
    public void playBook(Book book){
        startService(audioBook);

        Log.d("did service", "banoo");

        if(isConnected){
            seekbar.setMax(book.getDuration());

            currentDuration = book.getDuration();

            Log.d("did services", String.valueOf(book.getId()));
            servBinder.play(book.getId());

            playing = true;
            paused = false;
        }
    }
}