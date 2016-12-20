package uit.phatnguyen.todo.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.adapter.ColorAdapter;
import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.helper.AlarmReceiver;
import uit.phatnguyen.todo.helper.MyUtility;
import uit.phatnguyen.todo.model.Color;
import uit.phatnguyen.todo.model.Todo;

public class CreateItemsActivity extends AppCompatActivity {

    Button btnNgay, btnGio,btnSaveItem,btnCancel;
    EditText edtTenCongViec,edtDiaDiem;
    CheckBox ckNhacNho,ckHoanTat;
    Bundle itemBundle = new Bundle();
    ToDoHelper toDoHelper;

    //set color
    Spinner spColorChooser ;
    ArrayList<Color> colorArrayList ;
    ColorAdapter colorAdapter;

    //array cua colo trong file xml
    int[] myIntColors;
    String[] myStringColors;
    LinearLayout llItemDetail;

    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_detail);
        //Khoi tao co so du lieu
        toDoHelper = new ToDoHelper(this);

        getControls();
        setDefault();
        //lấy intent gọi Activity này
        Intent callerIntent = getIntent();
        Bundle fromListBundle = new Bundle();

        fromListBundle = callerIntent.getBundleExtra("toItem");
        int listId = fromListBundle.getInt("listId");
        System.out.println("listId lay duoc o item la : "+listId);
        String action = fromListBundle.getString("action") +"";
        String listTitle = fromListBundle.getString("listTitle");
        int lisColorIndex = fromListBundle.getInt("listColorIndex");

        itemBundle.putString("listTitle",listTitle);
        itemBundle.putInt("listId",listId);
        itemBundle.putInt("listColorIndex",lisColorIndex);

        addEvents();
        System.out.println("Action o item lay tu list qua la "+action + " listColorIndex :"+lisColorIndex);
        if(action.equals("update")){
            Todo todo = (Todo) fromListBundle.getSerializable("item");

            //gán itemaction vao itembundle
            itemBundle.putString("iaction","update");
            itemBundle.putInt("itemId",todo.getID());
            itemBundle.putSerializable("item",todo);
            //show item Todo len
            System.out.println("Item ID(update) la : "+ itemBundle.getInt("itemId") + "\n" + todo.toString());
            showItem(todo);
        }
        else{
            int itemId = 0;
            itemId = toDoHelper.getNext(Todo.TABLE_NAME,Todo.COL_ID);
            itemBundle.putString("iaction","new");
            itemBundle.putInt("itemId",itemId);
            System.out.println("Item ID(new) la : "+ itemBundle.getInt("itemId"));
        }
        System.out.println("Test getdate time :"+ MyUtility.getCurrentDateTime());

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

        spColorChooser = (Spinner) findViewById(R.id.spColorChooser);
        llItemDetail = (LinearLayout) findViewById(R.id.llItemDetail);
    }
    private  void setDefault(){
        String date = MyUtility.getCurrentDate();
        String time = MyUtility.getCurrentTime();
        System.out.println(" date set default la :"+date);
        btnGio.setText(time);
        btnNgay.setText(date);

        //color
        colorArrayList = new ArrayList<Color>();
        colorAdapter = new ColorAdapter(this,R.layout.color_row,colorArrayList);
        myIntColors = getResources().getIntArray(R.array.intColors);
        myStringColors = getResources().getStringArray(R.array.stringColors);
        for (int i = 0 ; i<myStringColors.length ; i++ ){
            colorArrayList.add(new Color(myStringColors[i],myIntColors[i]));
        }
        spColorChooser.setAdapter(colorAdapter);
        spColorChooser.setSelection(0);

    }
    private void addEvents(){
        btnNgay.setOnClickListener(new processMyFunct());
        btnGio.setOnClickListener(new processMyFunct());
        btnCancel.setOnClickListener(new processMyFunct());
        btnSaveItem.setOnClickListener(new processMyFunct());
        spColorChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Color color = colorArrayList.get(position);
                llItemDetail.setBackgroundColor(color.getColorValue());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void goBackList(int listId) {
        Intent intent = new Intent(CreateItemsActivity.this,CreateListActivity.class);
        // Lay thong tin list tu idList de hien thi lai man hinh
        Bundle toList = new Bundle();
        String listTitle = itemBundle.getString("listTitle");
        int listColorIndex = itemBundle.getInt("listColorIndex");

        toList.putInt("listId",listId);
        toList.putString("listTitle",listTitle);
        toList.putInt("listColorIndex",listColorIndex);
        toList.putString("action","update");
        intent.putExtra("toList",toList);
        startActivity(intent);
        System.out.println("gobackList(): listId : "+listId +" listTitle :"+listTitle
                +" listColorIndex :"+listColorIndex);
        System.out.println("Da ra khoi gobackList(); voi action duoc gan la :"
                +toList.getString("action"));
    }
    /**
     * Hàm hiển thị DatePickerDialog
     */
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month,
                                  int dayOfMonth) {
                btnNgay.setText(dayOfMonth + "-" + month + "-" + year);
            }
        };
        int ngay,thang,nam;
        String date[] = btnNgay.getText().toString().split("-");
        ngay = Integer.parseInt(date[0]);
        thang = Integer.parseInt(date[1]);
        nam = Integer.parseInt(date[2]);

        System.out.println("Ngay :" + ngay + " thang :"+thang + " nam :"+nam);

        DatePickerDialog dateDialog = new DatePickerDialog(this, callBack, nam, thang, ngay);
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

        int index = MyUtility.getIndex(colorArrayList,item.getCOLOR());
        spColorChooser.setSelection(index);

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
        //Dùng selected item để set giá trị cho todo
        /*Color s =(Color) spColorChooser.getSelectedItem();
        s.getColorId();*/
        //Them item nay vao itemBundle
        itemBundle.putSerializable("item",item);
    }
    private void saveItemAction() {
        //fix lai ham nay ..chua luu duoc
        String iaction = itemBundle.getString("iaction");
        System.out.println("iaction o saveItemACction la : "+iaction);
        Todo todo = new Todo();
        if(iaction.equals("update")){
            todo = (Todo) itemBundle.getSerializable("item");
        }
        //Lay color cua spinner
        int index = spColorChooser.getSelectedItemPosition();
        System.out.println("index spinner la "+index);

        int itemId = itemBundle.getInt("itemId");
        System.out.println("Item Id o saveItemACction la : "+itemId);
        String noidung = edtTenCongViec.getText().toString()+"";
        String ngay = btnNgay.getText().toString()+"";
        String gio = btnGio.getText().toString()+"";
        String diadiem = edtDiaDiem.getText().toString() +"";
        String mau = colorArrayList.get(index).getColorKey().toString();
        int nhacnho = ckNhacNho.isChecked() ? 1 : 0 ;
        int hoantat = ckHoanTat.isChecked() ? 1 : 0 ;
        if(noidung.trim().length()==0 || ngay.trim().length()==0 ||
                gio.trim().length()==0 || diadiem.trim().length()==0){
            System.out.println("Loi!Dien thieu thong tin");
            Toast.makeText(this,
                    "Lỗi ! Vui lòng điền đầy đủ thông tin!",
                    Toast.LENGTH_LONG).show();

        }
        else{
            //set gia tri cho todo
            todo.setID(itemId);
            todo.setCONTENT(noidung);
            todo.setDATE(ngay);
            todo.setHOUR(gio);
            todo.setLOCATION(diadiem);
            todo.setIsNOTIFICATION(nhacnho);
            todo.setSTATUS(hoantat);
            todo.setNGAYSUA(MyUtility.getCurrentDateTime());
            todo.setCOLOR(mau);

            switch (iaction){
                case "new":
                    int listId = itemBundle.getInt("listId");
                    todo.setTODO_FK(listId);
                    System.out.println("Case new and item id ="+itemId);
                    insertTodo(todo);
                    break;
                case "update":
                    System.out.println("Case update and item id ="+itemId);
                    updateTodo(todo);
                    break;
            }
        }
    }
    private void insertTodo(Todo todo){
        int listId = itemBundle.getInt("listId");
        //xu ly khi them moi 1 cong viec
        System.out.println("Todo dang insert la :\n" +todo.toString());
        long ketqua = toDoHelper.insertToDoItem(todo);
        if(ketqua == -1){
            Toast.makeText(this,
                    "Lỗi khi thêm TodoItem CONTENT :"+todo.getCONTENT().toString(),
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,
                    "Thêm thành công TodoItem CONTENT :"+todo.getCONTENT().toString(),
                    Toast.LENGTH_LONG).show();
            if(todo.getIsNOTIFICATION()==1 && todo.getSTATUS() != 1){
                int id =  toDoHelper.getCurrentId(Todo.TABLE_NAME,Todo.COL_ID);
                Log.d("ID", "insertTodo: "+id);
                alarm.setAlarm(this,todo.getDATE(),todo.getHOUR(),id);
            }
        }
        goBackList(listId);
    }
    private void updateTodo(Todo todo){
        int listId = itemBundle.getInt("listId");
        //xu ly cap nhat list cong viec
        System.out.println("Todo dang update la :\n" +todo.toString());
        int ketqua = toDoHelper.updateToDoItem(todo);
        if(ketqua == -1){
            Toast.makeText(this,
                    "Lỗi khi update TodoItem ID = "+todo.getID(),
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,
                    "Update thành công TodoItem ID = "+todo.getID(),
                    Toast.LENGTH_LONG).show();
            alarm.cancel(this,todo.getID());
            if(todo.getIsNOTIFICATION()==1 && todo.getSTATUS() != 1){
                alarm.setAlarm(this,todo.getDATE(),todo.getHOUR(),todo.getID());
            }
        }
        goBackList(listId);
    }
    public class processMyFunct implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnNgay:
                    showDatePickerDialog();
                    break;
                case R.id.btnGio:
                    showTimePickerDialog();
                    break;
                case R.id.btnCancel:
                    int listId ;
                    listId = itemBundle.getInt("listId");
                    System.out.println("listId lay duoc o btnCancel la :"+listId);
                    goBackList(listId);
                    //Xoa bundle
                    itemBundle.clear();
                    break;
                case R.id.btnSaveItem:
                    saveItemAction();
                    break;
                default:
                    break;
            }
        }
    }
}
