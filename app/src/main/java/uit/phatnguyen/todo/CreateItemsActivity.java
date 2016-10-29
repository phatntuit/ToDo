package uit.phatnguyen.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import uit.phatnguyen.todo.model.Todo;

public class CreateItemsActivity extends AppCompatActivity {

    Button btnNgay, btnGio,btnSaveItem,btnCancel;
    EditText edtTenCongViec,edtDiaDiem;
    CheckBox ckNhacNho,ckHoanTat;
    Bundle itemBundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_detail);

        //lấy intent gọi Activity này
        Intent callerIntent = getIntent();
        Bundle list = new Bundle();
        list = callerIntent.getBundleExtra("list");
        getControls();
        setDefault();
        addEvents();
        if(list.containsKey("action")){
            Todo todo = (Todo) list.getSerializable("item");
            itemBundle.putString("idList",todo.getTODO_FK());
            showItem(todo);
        }
    }
    private void getControls(){
        btnNgay = (Button) findViewById(R.id.btnNgay);
        btnGio = (Button) findViewById(R.id.btnGio);
        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        edtTenCongViec = (EditText) findViewById(R.id.edtTenCongViec);
        edtDiaDiem = (EditText) findViewById(R.id.edtDiaDiem);
        ckNhacNho = (CheckBox) findViewById(R.id.ckNhacNho);
        ckHoanTat = (CheckBox) findViewById(R.id.ckHoanTat);
    }
    private  void setDefault(){
        String date = MyUtility.getCurrentDate();
        String time = MyUtility.getCurrentTime();

        btnGio.setText(time);
        btnNgay.setText(date);
    }
    private void addEvents(){
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idList = "";
                goBackList(idList);
            }
        });
    }
    private void goBackList(String idList) {
        Intent intent = new Intent(CreateItemsActivity.this,CreateListActivity.class);
        // Lay thong tin list tu idList de hien thi lai man hinh
    }

    /**
     * Hàm hiển thị DatePickerDialog
     */
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                btnNgay.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        int ngay,thang,nam;
        String namsinh[] = btnNgay.getText().toString().split("/");
        ngay = Integer.parseInt(namsinh[0]);
        thang = Integer.parseInt(namsinh[1]);
        nam = Integer.parseInt(namsinh[2]);

        DatePickerDialog dateDialog=new DatePickerDialog(this, callBack, nam, thang, ngay);
        dateDialog.setTitle("Chọn Ngày");
        dateDialog.show();
    }
    /**
     * Hàm hiển thị TimePickerDialog
     */
    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {

                String h = ( hourOfDay >9 ) ? Integer.toString(hourOfDay) : "0" + hourOfDay;
                String m = ( minute >9 ) ? Integer.toString(minute) : "0" + minute;
                String time = h + ":" + m;

                btnGio.setText(time);
                ///xu ly luu vao CSDl
                /// tu lam
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=btnGio.getText()+"";
        System.out.println("Thoi gian lay duoc la : " +s);
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                CreateItemsActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn thời gian");
        time.show();


    }
    private void showItem(Todo item){
        edtTenCongViec.setText(item.getCONTENT());
        btnNgay.setText(item.getDATE());
        btnGio.setText(item.getHOUR());
        edtDiaDiem.setText(item.getLOCATION());
        if(item.getIsNOTIFICATION()==1){
            ckNhacNho.setChecked(true);
        }
        else
        {
            ckNhacNho.setChecked(false);
        }
        if(item.getSTATUS()==1){
            ckHoanTat.setChecked(true);
        }
        else{
            ckHoanTat.setChecked(false);
        }
    }
}
