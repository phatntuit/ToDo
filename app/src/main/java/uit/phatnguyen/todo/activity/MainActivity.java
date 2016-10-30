package uit.phatnguyen.todo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uit.phatnguyen.todo.helper.MyUtility;
import uit.phatnguyen.todo.R;
import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.model.TodoList;

public class MainActivity extends AppCompatActivity {
    private Boolean exit = false;
    Button btnAddList;
    ListView lvToDoList;
    TextView tvNow;
    ToDoHelper toDoHelper ;
    TextView tvNotification;
    ArrayList<TodoList> arrayListTodoList;
    ArrayAdapter<TodoList> arrayAdapterTodoList;
    //luu ngay hien tai
    String date;
    //dung de gui gia tri qua cac activity
    Bundle mainBundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Khoi tao database cho app
        toDoHelper = new ToDoHelper(MainActivity.this);

        //get control setdefaul and add event
        getControls();
        setDefault();
        readTodoList();
        addEvents();
    }
    private void getControls(){
        btnAddList = (Button) findViewById(R.id.btnAddList);
        lvToDoList = (ListView) findViewById(R.id.lvToDoList);
        tvNow = (TextView) findViewById(R.id.tvNow);
        tvNotification = (TextView) findViewById(R.id.tvNotification);
    }
    private  void setDefault(){
        mainBundle = new Bundle();
        date = MyUtility.getCurrentDate();
        tvNow.setText(date);
        //set up list view
        arrayListTodoList = new ArrayList<TodoList>();
        arrayAdapterTodoList = new ArrayAdapter<TodoList>(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayListTodoList);
        lvToDoList.setAdapter(arrayAdapterTodoList);
    }
    private void addEvents(){
        btnAddList.setOnClickListener(new processMyFunct());
        lvToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList tdl = new TodoList();
                tdl = arrayListTodoList.get(position);
                int listId = tdl.getID();
                String listTitle = tdl.getTITLE();
                //Toast.makeText(MainActivity.this,"Da vao item select",Toast.LENGTH_LONG).show();
                Log.d("item click","Da vao roi");
                requestDisplayList(listId,listTitle);
            }
        });
        lvToDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoList tdl = arrayListTodoList.get(position);
                int listId = tdl.getID();
                Boolean kq = false;
                kq = toDoHelper.deleteToDoList(listId);
                if(kq == true){
                    arrayListTodoList.remove(position);
                    arrayAdapterTodoList.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"Da xoa listToDo co id = "+listId,
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Loi khi xoa listToDo co id = "+listId
                            ,Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
    private void readTodoList(){
        arrayListTodoList.clear();
        Cursor cursor = toDoHelper.getQuery("SELECT * FROM TODOLIST",null);
        if(cursor.getCount() > 0){
            for (int i =0 ; i< cursor.getCount() ; i++){
                //chuyen con tro ve vi tri thu i
                cursor.moveToPosition(i);
                //cursor.getColumnIndex(TodoList.COL_TITLE) : LAY INDEX CUA COLUMN TITLE
                String TITLE = cursor.getString(cursor.getColumnIndex(TodoList.COL_TITLE));
                int ID = cursor.getInt(cursor.getColumnIndex(TodoList.COL_ID));
                String COLOR = cursor.getString(cursor.getColumnIndex(TodoList.COL_COLOR));
                String NGAYTAO = cursor.getString(cursor.getColumnIndex(TodoList.COL_NGAYTAO));
                String NGAYSUA = cursor.getString(cursor.getColumnIndex(TodoList.COL_NGAYSUA));

                TodoList tdl = new TodoList();
                //gan gia tri cho todolist tdl
                tdl.setID(ID);
                tdl.setNGAYTAO(NGAYTAO);
                tdl.setNGAYSUA(NGAYSUA);
                tdl.setCOLOR(COLOR);
                tdl.setTITLE(TITLE);
                //them tdl vao mang arraylist
                arrayListTodoList.add(tdl);
                //cap nhat adapter
                arrayAdapterTodoList.notifyDataSetChanged();
            }
            //System.out.println("So phan tu arraylist todolist :" + arrayListTodoList.size());
        }
        else{
            tvNotification.setVisibility(View.VISIBLE);
            tvNotification.setText("Không có danh sách công việc nào cả !");
        }
    }
    private void addList() {
        Intent intent = new Intent(MainActivity.this,CreateListActivity.class);

        int listId = toDoHelper.getNext(TodoList.TABLE_NAME,TodoList.COL_ID);
        mainBundle.putInt("listId",listId);
        mainBundle.putString("action","new");
        intent.putExtra("toList",mainBundle);

        startActivity(intent);
    }
    private void requestDisplayList(int listId,String listTitle) {
        Intent intent = new Intent(MainActivity.this,CreateListActivity.class);
        mainBundle.putInt("listId",listId);
        mainBundle.putString("listTitle",listTitle);
        mainBundle.putString("action","update");
        intent.putExtra("toList",mainBundle);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (exit) {
            //Xoa bundle truoc khi thoat
            mainBundle.clear();
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Nhấn Back một lần nữa để thoát!",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    public class processMyFunct implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnAddList:
                    addList();
                    break;
                default:
                    break;
            }
        }
    }
}
