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
    ArrayList<String> colors;
    String displayColors[];

    // The native language colors
    //String ogColors[];

    public bookAdapter(Context context, ArrayList<String> colors)
    {
        this.context = context;
        this.colors = colors;
        //this.displayColors = context.getResources().getStringArray(R.array.books);
    }



    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {

        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        String colorValue = colors.get(position);

        textView.setText(colorValue);

        textView.setTextSize(24);

        return textView;
    }
}
