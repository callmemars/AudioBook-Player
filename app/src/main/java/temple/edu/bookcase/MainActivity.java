package temple.edu.bookcase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String bookList[] = getResources().getStringArray(R.array.books);
        ArrayList books = new ArrayList<String>();

        for(int i = 0;i < bookList.length-1;i++){
            books.add(bookList[i]);
        }
    }
}
