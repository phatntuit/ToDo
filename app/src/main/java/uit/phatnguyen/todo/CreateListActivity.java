package uit.phatnguyen.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/**
 * Created by PhatNguyen on 2016-10-25.
 */

public class CreateListActivity extends AppCompatActivity {
    Button btnAddItems,btnSaveList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_items);
        getControls();
        addEvents();
    }
    private void getControls(){
        btnAddItems = (Button) findViewById(R.id.btnAddItem);
        btnSaveList = (Button) findViewById(R.id.btnSaveList);
    }
    private void addEvents(){
        btnAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateListActivity.this,CreateItemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
