package uit.phatnguyen.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.model.Color;

/**
 * Created by PhatNguyen on 2016-11-01.
 */

public class ColorAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    ArrayList<Color> colorArrayList;

    public ColorAdapter(Context context, int myLayout, ArrayList<Color> colorArrayList) {
        this.context = context;
        this.myLayout = myLayout;
        this.colorArrayList = colorArrayList;
    }

    @Override
    public int getCount() {
        return colorArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);
        //anh xa control o row_color
        TextView tvColor,tvKey;
        tvColor = (TextView) convertView.findViewById(R.id.tvColor);
        tvKey = (TextView) convertView.findViewById(R.id.tvKey);

        tvKey.setText(colorArrayList.get(position).getColorKey());
        tvColor.setBackgroundColor(colorArrayList.get(position).getColorValue());

        return convertView;
    }
}
