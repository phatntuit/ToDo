package uit.phatnguyen.todo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.model.TodoList;

/**
 * Created by PhatNguyen on 2016-10-30.
 */

public class TDLArrayAdapter extends ArrayAdapter<TodoList> {
    Context context;
    int layoutResourceId;
    ArrayList<TodoList> tdls = null;

    public TDLArrayAdapter(Context context,int layoutResourceId, ArrayList<TodoList> tdls) {
        super(context, layoutResourceId, tdls);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tdls = tdls;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        tvTenList = (TextView) convertView.findViewById(R.id.tvTenList);
        tvListId = (TextView) convertView.findViewById(R.id.tvListId);
        tvNgaySua = (TextView) convertView.findViewById(R.id.tvNgaySua);

        String tenlist = position + 1 + "." + tdl.getTITLE() + "";
        String id = "ID = " + tdl.getID();
        String ngaysua = "Cập nhật lần cuối lúc :\n" +tdl.getNGAYSUA();

        tvTenList.setText(tenlist);
        tvListId.setText(id);
        tvNgaySua.setText(ngaysua);
        return convertView;
    }
}
