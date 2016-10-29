package uit.phatnguyen.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    ListView lvToDoItems;
    ArrayList<Todo> arrayListTodo = new ArrayList<Todo>();
    ArrayAdapter<Todo> arrayAdapterTodo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_items);
        //Khoi tao database
        toDoHelper = new ToDoHelper(CreateListActivity.this);
        getControls();
        //lấy intent gọi Activity này
        Intent callerIntent = getIntent();
        //có intent rồi thì lấy Bundle dựa vào key "main" ở activity 1 mà ta đã gửi
        //qua hàm intent.putExtra("main",mainBundle);
        Bundle main = new Bundle();
        main = callerIntent.getBundleExtra("main");
        int idList = main.getInt("idList");
        //Lưu idlist vao bundle của activity này
        listBundle.putInt("idList",idList);
        addEvents();
        if(main.containsKey("title")){
            Log.d("id list", idList + "");

            String listTitle = main.getString("title") +"";
            //Cap nhat action la update
            listBundle.putString("action","update");
            listBundle.putString("title",listTitle);

            edtTenList.setText(listTitle);
            //Show todoList co id la idList ra man hinh
            showList(idList);
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
                todo = arrayListTodo.get(position);
                requestDisplayItem(todo);
            }
        });
    }
    private void requestDisplayItem(Todo item) {
        Intent intent = new Intent(CreateListActivity.this,CreateItemsActivity.class);
        //gui object qua
        listBundle.putSerializable("item",item);
        listBundle.putString("action","view");
        intent.putExtra("list",listBundle);
        startActivity(intent);
    }
    private void backMain() {
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
    private void addItems() {
        Intent intent = new Intent(CreateListActivity.this,CreateItemsActivity.class);

        //Dùng bundle này gửi dữ liệu cho itemActivity
        Bundle listBundle = new Bundle();
        String tenList = "";
        if(edtTenList.getText().toString().trim().length() ==0){
            String tenlist = MyUtility.getCurrentDate() + "-" + MyUtility.getCurrentTime();
            edtTenList.setText(tenlist);
        }
        tenList = edtTenList.getText().toString();
        listBundle.putString("tenList",tenList);
        //gắn listBundle vào intent
        intent.putExtra("list",listBundle);
        startActivity(intent);
    }
    private void saveList(){
        String listTitle;
        int listId;
        TodoList tdl;
        if(edtTenList.getText().toString().trim().length() ==0){
            String title = MyUtility.getCurrentDate() + "-" + MyUtility.getCurrentTime();
            edtTenList.setText(title);
        }
        listTitle = edtTenList.getText().toString();

        tdl = new TodoList();
        listId = toDoHelper.getNext(tdl.TABLE_NAME,tdl.COL_ID);
        tdl.setID(listId);
        tdl.setTITLE(listTitle);
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
    private void showList(int idList){
        getList(idList);
        arrayAdapterTodo = new ArrayAdapter<Todo>(this,
                android.R.layout.simple_list_item_1,arrayListTodo);
        lvToDoItems.setAdapter(arrayAdapterTodo);
    }
    private void getList(int idList){
        ToDoHelper toDoHelper = new ToDoHelper(this);
        Cursor cursor ;
        cursor = toDoHelper.getQuery("SELECT * FROM TODOITEMS WHERE TODO_FK = " + idList,null);
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
                //gan gia tri cho td
                //them tdl vao mang arraylist
                arrayListTodo.add(td);
            }
            //System.out.println("So phan tu arraylist todolist :" + arrayListTodoList.size());
        }
        else{
            tvNotification.setVisibility(View.VISIBLE);
            tvNotification.setText("Không có công việc nào cả !");
        }
    }
    public class processMyFunct implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddItem:
                    addItems();
                    break;
                case R.id.btnSaveList:
                    saveList();
                    break;
                case R.id.btnBack:
                    backMain();
                    break;
                default:
                    break;
            }
        }
    }
}
