package uit.phatnguyen.todo.activity;

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
import android.widget.Toast;

import uit.phatnguyen.todo.helper.MyUtility;
import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.model.Todo;

public class CreateItemsActivity extends AppCompatActivity {

    Button btnNgay, btnGio,btnSaveItem,btnCancel;
    EditText edtTenCongViec,edtDiaDiem;
    CheckBox ckNhacNho,ckHoanTat;
    Bundle itemBundle = new Bundle();
    ToDoHelper toDoHelper;
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
        Bundle list = new Bundle();

        list = callerIntent.getBundleExtra("toItem");
        int listId = list.getInt("listId");
        System.out.println("listId lay duoc o item la : "+listId);
        String action = list.getString("action") +"";
        String listTitle = list.getString("listTitle");
        itemBundle.putString("listTitle",listTitle);
        itemBundle.putInt("listId",listId);
        addEvents();
        System.out.println("Action o item lay tu list qua la "+action);
        if(action.equals("update")){
            Todo todo = (Todo) list.getSerializable("item");

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
    }
    private  void setDefault(){
        String date = MyUtility.getCurrentDate();
        String time = MyUtility.getCurrentTime();

        btnGio.setText(time);
        btnNgay.setText(date);
    }
    private void addEvents(){
        btnNgay.setOnClickListener(new processMyFunct());
        btnGio.setOnClickListener(new processMyFunct());
        btnCancel.setOnClickListener(new processMyFunct());
        btnSaveItem.setOnClickListener(new processMyFunct());
    }
    private void goBackList(int listId) {
        Intent intent = new Intent(CreateItemsActivity.this,CreateListActivity.class);
        // Lay thong tin list tu idList de hien thi lai man hinh
        Bundle toList = new Bundle();
        String listTitle = itemBundle.getString("listTitle");
        toList.putInt("listId",listId);
        toList.putString("listTitle",listTitle);
        toList.putString("action","update");
        intent.putExtra("toList",toList);
        startActivity(intent);
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
        String namsinh[] = btnNgay.getText().toString().split("-");
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

        int itemId = itemBundle.getInt("itemId");
        System.out.println("Item Id o saveItemACction la : "+itemId);
        String noidung = edtTenCongViec.getText().toString()+"";
        String ngay = btnNgay.getText().toString()+"";
        String gio = btnGio.getText().toString()+"";
        String diadiem = edtDiaDiem.getText().toString() +"";
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
            todo.setNGAYSUA(MyUtility.getCurrentDate());

            switch (iaction){
                case "new":
                    int listId = itemBundle.getInt("listId");
                    todo.setTODO_FK(listId);
                    todo.setNGAYTAO(MyUtility.getCurrentDate());
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
