package uit.phatnguyen.todo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.helper.MyUtility;
import uit.phatnguyen.todo.model.Color;
import uit.phatnguyen.todo.model.TodoList;

/**
 * Created by PhatNguyen on 2016-10-30.
 */

public class TDLArrayAdapter extends ArrayAdapter<TodoList> {
    Context context;
    int layoutResourceId;
    ArrayList<TodoList> tdls = null;

    //Phuc vu cho color
    ArrayList<Color> colorArrayList;
    int[] myIntColors;
    String[] myStringColors;

    public TDLArrayAdapter(Context context,int layoutResourceId, ArrayList<TodoList> tdls) {
        super(context, layoutResourceId, tdls);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tdls = tdls;

        //Khoi tao mang de luu color
        colorArrayList = new ArrayList<Color>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Khoi tao cac gia tri cua color
        myIntColors = context.getResources().getIntArray(R.array.intColors);
        myStringColors = context.getResources().getStringArray(R.array.stringColors);
        for (int i = 0 ; i<myStringColors.length ; i++ ){
            colorArrayList.add(new Color(myStringColors[i],myIntColors[i]));
        }

        //Lấy đối tượng TodoList tại vị trí position
        TodoList tdl = (TodoList) getItem(position);
        //Kiểm tra xem view này tồn tại chưa ? Nếu chưa thì chèn vào
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.todo_list_row, parent, false);
        }
        //Lấy các control ra để set giá trị
        //tvTenList = STT + Title của list đó
        TextView tvTenList,tvListId,tvNgaySua;
        LinearLayout llList;

        tvTenList = (TextView) convertView.findViewById(R.id.tvTenList);
        tvListId = (TextView) convertView.findViewById(R.id.tvListId);
        tvNgaySua = (TextView) convertView.findViewById(R.id.tvNgaySua);
        llList = (LinearLayout) convertView.findViewById(R.id.llList);

        String tenlist = position + 1 + "." + tdl.getTITLE() + "";
        String id = "ID = " + tdl.getID() + " test Color :" + tdl.getCOLOR();
        String ngaysua = "Cập nhật lần cuối lúc :\n" +tdl.getNGAYSUA();

        int listColorIndex = MyUtility.getIndex(colorArrayList,tdl.getCOLOR());
        int colorValue = colorArrayList.get(listColorIndex).getColorValue();

        tvTenList.setText(tenlist);
        tvListId.setText(id);
        tvNgaySua.setText(ngaysua);

        llList.setBackgroundColor(colorValue);
        return convertView;
    }
}
