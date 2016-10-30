package uit.phatnguyen.todo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.model.Todo;

/**
 * Created by PhatNguyen on 2016-10-30.
 */

public class TDArrayAdapter extends ArrayAdapter<Todo>{
    Context context;
    int layoutResourceId;
    ArrayList<Todo> tds = null;

    public TDArrayAdapter(Context context,int layoutResourceId, ArrayList<Todo> tds) {
        super(context, layoutResourceId, tds);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tds = tds;
    }

    @SuppressWarnings("deprecation")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Lấy đối tượng TodoList tại vị trí position
        Todo td = (Todo) getItem(position);
        //Kiểm tra xem view này tồn tại chưa ? Nếu chưa thì chèn vào
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.todo_item_row, parent, false);
        }
        //Lấy các control ra để set giá trị
        TextView tvNoiDung, tvDiaDiem, tvDate, tvHour, tvKey, tvHoanTat, tvNgaySua;
        ImageView imgBaoThuc,imgHoanTat;
        Drawable iconHoanTat = getContext().getResources().getDrawable(R.drawable.tick);
        Drawable iconChuaHoanTat = getContext().getResources().getDrawable(R.drawable.nottick);

        tvNoiDung = (TextView) convertView.findViewById(R.id.tvNoiDung);
        tvDiaDiem = (TextView) convertView.findViewById(R.id.tvDiaDiem);
        tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        tvHour = (TextView) convertView.findViewById(R.id.tvHour);
        tvKey = (TextView) convertView.findViewById(R.id.tvKey);
        tvHoanTat = (TextView) convertView.findViewById(R.id.tvHoanTat);
        tvNgaySua = (TextView) convertView.findViewById(R.id.tvNgaySua);

        imgBaoThuc = (ImageView) convertView.findViewById(R.id.imgBaoThuc);
        imgHoanTat = (ImageView) convertView.findViewById(R.id.imgHoanTat);

        //neu co bao thuc thi imgBaoThuc.setVisibility(View.VISIBLE);
        // neu item này đã hoàn tất thì đổi src của imgHoanTat
        //tvKey chứa thông tin khoá chính và kháo ngoại của mỗi item

        /**
         * + "( ID = " + td.getID() + ")"
         */

        String noidung = position + 1 + "." + td.getCONTENT() ;
        String diadiem = "Địa điểm : " +td.getLOCATION()+"";
        String ngay = "Ngày : " +td.getDATE()+"";
        String gio = "Lúc : " +td.getHOUR()+"";
        String key = "( ID = " + td.getID() + " ,LIST_ID = " + td.getTODO_FK() +")";
        int baothuc = td.getIsNOTIFICATION();
        int hoantat = td.getSTATUS();
        String ngaysua = "Cập nhật lần cuối lúc : " + td.getNGAYSUA();

        tvNoiDung.setText(noidung);
        tvDiaDiem.setText(diadiem);
        tvDate.setText(ngay);
        tvHour.setText(gio);
        tvKey.setText(key);
        tvNgaySua.setText(ngaysua);

        if(baothuc == 1){
            imgBaoThuc.setVisibility(View.VISIBLE);
        }
        else{
            imgBaoThuc.setVisibility(View.GONE);
        }
        if(hoantat == 1){
            imgHoanTat.setImageDrawable(iconHoanTat);
            tvHoanTat.setText("Đã hoàn tất");
        }
        else{
            imgHoanTat.setImageDrawable(iconChuaHoanTat);
            tvHoanTat.setText("Chưa hoàn tất");
        }

        return convertView;
    }
}
