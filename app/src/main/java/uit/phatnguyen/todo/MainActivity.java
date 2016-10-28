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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.model.TodoList;

public class MainActivity extends AppCompatActivity {

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
                int idlist = tdl.getID();
                String title = tdl.getTITLE();
                //Toast.makeText(MainActivity.this,"Da vao item select",Toast.LENGTH_LONG).show();
                Log.d("item click","Da vao roi");
                displayList(idlist,title);
            }
        });
        lvToDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Da vao item  long click",Toast.LENGTH_LONG).show();
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

        int id = toDoHelper.getNext(TodoList.TABLE_NAME,TodoList.COL_ID);
        mainBundle.putInt("id",id);
        intent.putExtra("main",mainBundle);

        startActivity(intent);
    }
    private void displayList(int id, String title) {
        Intent intent = new Intent(MainActivity.this,CreateListActivity.class);
        mainBundle.putInt("id",id);
        mainBundle.putString("title",title);
        intent.putExtra("main",mainBundle);
        startActivity(intent);
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
