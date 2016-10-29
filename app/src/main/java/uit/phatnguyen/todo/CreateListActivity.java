package uit.phatnguyen.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.model.Todo;
import uit.phatnguyen.todo.model.TodoList;

/**
 * Created by PhatNguyen on 2016-10-25.
 */
public class CreateListActivity extends AppCompatActivity {
    Button btnAddItems,btnSaveList,btnBack;
    EditText edtTenList;
    TextView tvNotification;
    Bundle listBundle = new Bundle();
    ToDoHelper toDoHelper ;
    //Listview
    ListView lvToDoItems ;
    ArrayList<Todo> arrayListTodo ;
    ArrayAdapter<Todo> arrayAdapterTodo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_items);
        //Khoi tao database
        toDoHelper = new ToDoHelper(this);
        getControls();
        setDefault();
        //lấy intent gọi Activity này
        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào key "main" ở activity 1 mà ta đã gửi
        //qua hàm intent.putExtra("main",mainBundle);
        Bundle main = new Bundle();
        main = callerIntent.getBundleExtra("toList");
        int listId = main.getInt("listId");
        String action = main.getString("action") +"";
        //Lưu idlist vao bundle của activity này
        listBundle.putInt("listId",listId);
        listBundle.putString("action",action);
        addEvents();

        if(action.equals("update")){
            String listTitle = main.getString("listTitle");
            listBundle.putString("listTitle",listTitle);
            listBundle.putString("action","update");
            showList(listId,listTitle);
        }
    }

    private void getControls(){
        btnAddItems = (Button) findViewById(R.id.btnAddItem);
        btnSaveList = (Button) findViewById(R.id.btnSaveList);
        btnBack = (Button) findViewById(R.id.btnBack);
        edtTenList = (EditText) findViewById(R.id.edtTenList);
        tvNotification = (TextView) findViewById(R.id.tvNotification);
        lvToDoItems = (ListView) findViewById(R.id.lvToDoItems);
    }
    private void addEvents(){
        btnAddItems.setOnClickListener(new processMyFunct());
        btnSaveList.setOnClickListener(new processMyFunct());
        btnBack.setOnClickListener(new processMyFunct());
        lvToDoItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = new Todo();
                todo = (Todo) arrayListTodo.get(position);
                System.out.println("Todo dang request display la :\n" +todo.toString());
                requestDisplayItem(todo);
            }
        });

    }
    private void setDefault(){
        arrayListTodo = new ArrayList<Todo>();
        arrayAdapterTodo = new ArrayAdapter<Todo>(this,
                android.R.layout.simple_list_item_1,arrayListTodo);
        lvToDoItems.setAdapter(arrayAdapterTodo);
    }
    private void requestDisplayItem(Todo item) {
        Intent intent = new Intent(CreateListActivity.this,CreateItemsActivity.class);
        //gui object qua
        listBundle.putSerializable("item",item);
        listBundle.putString("action","update");
        intent.putExtra("toItem",listBundle);
        startActivity(intent);
    }
    private void gobackMain() {
        Intent intent = new Intent(CreateListActivity.this,MainActivity.class);
        startActivity(intent);
        /**
         * activity 1 -> activity 2
         * Tại activity 1
         * //Tạo Intent để mở ResultActivity
         Intent myIntent=new Intent(MainActivity.this, ResultActivity.class);
         //Khai báo Bundle
         Bundle bundle=new Bundle();
         int a=Integer.parseInt(txta.getText().toString());
         int b=Integer.parseInt(txtb.getText().toString());
         //đưa dữ liệu riêng lẻ vào Bundle
         bundle.putInt("soa", a);
         bundle.putInt("sob", b);
         //Đưa Bundle vào Intent
         myIntent.putExtra("MyPackage", bundle);
         //Mở Activity ResultActivity
         startActivity(myIntent);
         */
        /**
         * Tại activity 2
         * //lấy intent gọi Activity này
         Intent callerIntent=getIntent();
         //có intent rồi thì lấy Bundle dựa vào MyPackage
         Bundle packageFromCaller=
         callerIntent.getBundleExtra("MyPackage");
         //Có Bundle rồi thì lấy các thông số dựa vào soa, sob
         int a=packageFromCaller.getInt("soa");
         int b=packageFromCaller.getInt("sob");
         */
    }
    private void addItemsAction() {
        Intent intent = new Intent(CreateListActivity.this,CreateItemsActivity.class);

        //Dùng bundle này gửi dữ liệu cho itemActivity
        String listTitle = "";
        int listId;
        if(edtTenList.getText().toString().trim().length() ==0){
            String tenlist = MyUtility.getCurrentDate() + "-" + MyUtility.getCurrentTime();
            edtTenList.setText(tenlist);
        }
        listTitle = edtTenList.getText().toString();
        listId = listBundle.getInt("listId");

        System.out.println("listId trong ham addItemsAction() cua CreateListActivity la " + listId);

        listBundle.putString("listTitle",listTitle);
        listBundle.putString("action","new");

        boolean check = toDoHelper.checkId(TodoList.TABLE_NAME,TodoList.COL_ID,listId);
        if(!check){
            //save list lai truoc khi them item
            saveListAction();
        }
        /*saveListAction();*/
        //gắn listBundle vào intent
        intent.putExtra("toItem",listBundle);
        startActivity(intent);
    }
    private void saveListAction(){
        //viet lai theo 2 action la update va new
        //se chinh lai sau
        String listTitle;
        int listId;
        TodoList tdl;
        if(edtTenList.getText().toString().trim().length() ==0){
            String title = MyUtility.getCurrentDate() + "-" + MyUtility.getCurrentTime();
            edtTenList.setText(title);
        }
        listTitle = edtTenList.getText().toString();

        tdl = new TodoList();
        listId = listBundle.getInt("listId");

        tdl.setID(listId);
        tdl.setTITLE(listTitle);

        String action = listBundle.getString("action")+"";

        System.out.println("listId trong ham saveListAction() cua CreateListActivity la " + listId);
        System.out.println("action trong ham saveListAction() cua CreateListActivity la " + action);
        switch (action){
            case "new":
                insertList(tdl);
                break;
            case "update":
                updateList(tdl);
                /*gobackMain();*/
                break;
            default:
                System.out.println("Xay ra loi khi them list!");
                break;
        }
    }
    private void insertList(TodoList tdl) {
        //xu ly khi them moi 1 list cong viec
        long ketqua = toDoHelper.insertToDoList(tdl);
        if(ketqua == -1){
            Toast.makeText(this,
                    "Lỗi khi thêm TodoList title :"+tdl.getTITLE().toString(),
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,
                    "Thêm thành công TodoList title :"+tdl.getTITLE().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void updateList(TodoList tdl) {
        //xu ly cap nhat list cong viec
        int ketqua = toDoHelper.updateToDoList(tdl);
        if(ketqua == -1){
            Toast.makeText(this,
                    "Lỗi khi update TodoList ID = "+tdl.getID(),
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,
                    "Update thành công TodoList ID = "+tdl.getID(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void showList(int listId,String listTitle){
        ArrayList<Todo> arr = new ArrayList<Todo>();
        //set gia tri cho edtTenList
        edtTenList.setText(listTitle);
        arrayListTodo.clear();
        //gan gia tri moi cho arrayListTodo
        arr = toDoHelper.getList(listId);
        System.out.println("Todo item ="+arrayListTodo.size());
        if(arr.size()==0){
            tvNotification.setVisibility(View.VISIBLE);
            tvNotification.setText("Không có công việc nào cả !");
        }
        else {
            tvNotification.setVisibility(View.GONE);
            for (Todo td : arr){
                arrayListTodo.add(td);
                System.out.println("Da them vao arrayListTodo td ="+td.toString());
            }
            arrayAdapterTodo.notifyDataSetChanged();
        }
    }
    public ArrayList<Todo> getList(int listId){
        ArrayList<Todo> arr = new ArrayList<Todo>();

        //Truy van lay du lieu tu bang TODOLIST co id = idList
        Cursor cursor = toDoHelper.getQuery("SELECT * FROM TODOITEMS WHERE TODO_FK = " + listId,null);
        try{
            if(cursor.getCount() > 0){
                for (int i =0 ; i< cursor.getCount() ; i++){
                    //chuyen con tro ve vi tri thu i
                    cursor.moveToPosition(i);
                    //cursor.getColumnIndex(TodoList.COL_TITLE) : LAY INDEX CUA COLUMN TITLE
                    int ID = cursor.getInt(cursor.getColumnIndex(Todo.COL_ID));
                    int TODO_FK = cursor.getInt(cursor.getColumnIndex(Todo.COL_TODOFK));
                    String CONTENT = cursor.getString(cursor.getColumnIndex(Todo.COL_CONTENT));
                    String DATE = cursor.getString(cursor.getColumnIndex(Todo.COL_DATE));
                    String HOUR = cursor.getString(cursor.getColumnIndex(Todo.COL_HOUR));
                    String LOCATION = cursor.getString(cursor.getColumnIndex(Todo.COL_LOCATION));
                    int STATUS = cursor.getInt(cursor.getColumnIndex(Todo.COL_STATUS));
                    int isNOTIFICATION = cursor.getInt(cursor.getColumnIndex(Todo.COL_NHACNHO));
                    String COLOR = cursor.getString(cursor.getColumnIndex(Todo.COL_COLOR));
                    String NGAYTAO = cursor.getString(cursor.getColumnIndex(Todo.COL_NGAYTAO));
                    String NGAYSUA = cursor.getString(cursor.getColumnIndex(Todo.COL_NGAYSUA));

                    Todo td = new Todo();
                    //gan gia tri cho td
                    td.setID(ID);
                    td.setTODO_FK(TODO_FK);
                    td.setCONTENT(CONTENT);
                    td.setDATE(DATE);
                    td.setHOUR(HOUR);
                    td.setLOCATION(LOCATION);
                    td.setSTATUS(STATUS);
                    td.setIsNOTIFICATION(isNOTIFICATION);
                    td.setCOLOR(COLOR);
                    td.setNGAYTAO(NGAYTAO);
                    td.setNGAYSUA(NGAYSUA);
                    //them tdl vao mang arraylist
                    arr.add(td);
                }
            }
        }catch (SQLiteException ex){
            System.out.println(ex.getMessage().toString());
        }
        finally {
            cursor.close();
        }
        return arr;
    }
    public class processMyFunct implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddItem:
                    addItemsAction();
                    break;
                case R.id.btnSaveList:
                    saveListAction();
                    break;
                case R.id.btnBack:
                    gobackMain();
                    break;
                default:
                    break;
            }
        }
    }

}
