package temple.edu.bookcase;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.squareup.picasso.Picasso;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.MODE_PRIVATE;


public class BookDetailsFragment extends Fragment {

    String bookTitle;
    TextView textView;
    ImageView bookImage;

    // Audio book vars
    private OnBookPlay parentFrag;
    private downloadBooks letsDwonload;
    Button playButton;
    Button downloadButton;
    Button deleteButton;

    Book book;
    File file;


    public BookDetailsFragment() {
    }

    public static BookDetailsFragment newInstance(Book x) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("bookKey", x);
        fragment.setArguments(args);


        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = (Book)getArguments().getParcelable("bookKey");
            bookTitle = book.getTitle();

            file = new File(getContext().getFilesDir(), book.getTitle());
        }
    }

    public void displayBook(Book x){

        if (textView != null) {
            textView.setText(x.getTitle() + " - " + x.getAuthor() + " (" + x.getPublished()
            + ")");
        }
        else
            textView.setText("null");

        Picasso.get().load(x.coverURL).into(bookImage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);


        file = new File(getContext().getFilesDir(), book.getTitle());
        textView = (TextView) v.findViewById(R.id.title);
        bookImage = v.findViewById(R.id.image);

        textView.setTextSize(20);
        if (bookTitle != null) {
            displayBook(book);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (book != null) {
            displayBook(book);

            playButton = getView().findViewById(R.id.playButton);
            downloadButton = getView().findViewById(R.id.download);
            deleteButton = getView().findViewById(R.id.DeleteButton);

            // Play button
            if (playButton != null) {
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(file.exists()) {
                            parentFrag.playBook(book, file);
                            Toast toast = Toast.makeText(getActivity(), "playing mp3", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else
                            parentFrag.playBook(book);
                    }
                });
            }

            // Delete BUtton
            if (deleteButton != null) {
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(file.exists()) {
                            file.delete();
                        }
                    }
                });
            }


            // Download button
            if (downloadButton != null) {
                downloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getActivity(), "downloaded", Toast.LENGTH_SHORT);
                        toast.show();
                        new Thread() {
                            @Override
                            public void run() {
                                URL bookurl = null;
                                try {
                                    bookurl = new URL("https://kamorris.com/lab/audlib/download.php?id=" + book.getId());
                                    URLConnection conn = bookurl.openConnection();
                                    int contentLength = conn.getContentLength();
                                    DataInputStream input = new DataInputStream(bookurl.openStream());
                                    byte[] buffer = new byte[contentLength];
                                    input.readFully(buffer);
                                    input.close();

                                    DataOutputStream out = new DataOutputStream((new FileOutputStream(file)));
                                    out.write(buffer);
                                    out.flush();
                                    out.close();


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();


                    }

                });
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if ((context instanceof BookDetailsFragment.OnBookPlay)){
            parentFrag = (BookDetailsFragment.OnBookPlay) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookPlay interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentFrag = null;
    }


    public interface OnBookPlay {
        //File file;
        void playBook(Book book);

        void playBook(Book book, File mp3);

    }

    public interface downloadBooks {
        void downloadBook(Book book);
    }

}
