package temple.edu.bookcase;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class bookAdapter extends BaseAdapter{


    Context context;
    //ArrayList<Book> colors;
    //ArrayList colors = new ArrayList<Book>();
    ArrayList<Book> colors = new ArrayList<Book>();
    String displayColors[];

    // The native language colors
    //String ogColors[];

    public bookAdapter(Context context, ArrayList<Book> books)
    {
        this.context = context;
        this.colors = books;
        //this.displayColors = context.getResources().getStringArray(R.array.books);
    }



    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {

        return colors.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        Book colorValue = colors.get(position);

        textView.setText(colorValue.getTitle());

        textView.setTextSize(24);

        return textView;
    }
}
