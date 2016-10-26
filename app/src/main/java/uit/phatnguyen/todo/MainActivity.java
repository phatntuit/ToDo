package uit.phatnguyen.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnAddList;
    ListView lvToDoList;
    TextView tvNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControls();
        setDefault();
        addEvents();
    }
    private void getControls(){
        btnAddList = (Button) findViewById(R.id.btnAddList);
        lvToDoList = (ListView) findViewById(R.id.lvToDoList);
        tvNow = (TextView) findViewById(R.id.tvNow);
    }
    private void addEvents(){
        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateListActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void setDefault(){
        String date = MyUtility.getCurrentDate();
        tvNow.setText(date);
    }
}
