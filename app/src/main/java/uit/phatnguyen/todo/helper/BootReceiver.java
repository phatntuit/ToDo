package uit.phatnguyen.todo.helper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

import uit.phatnguyen.todo.db.ToDoHelper;
import uit.phatnguyen.todo.model.Todo;


public class BootReceiver extends BroadcastReceiver {
    AlarmReceiver alarm = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            ToDoHelper toDoHelper = new ToDoHelper(context);
            Cursor cursor = toDoHelper.getQuery("Select * from TODOITEMS",null);
            try{
                if(cursor.getCount() > 0){
                    for (int i =0 ; i< cursor.getCount() ; i++){
                        //chuyen con tro ve vi tri thu i
                        cursor.moveToPosition(i);
                        //cursor.getColumnIndex(TodoList.COL_TITLE) : LAY INDEX CUA COLUMN TITLE
                        String DATE = cursor.getString(cursor.getColumnIndex(Todo.COL_DATE));
                        String HOUR = cursor.getString(cursor.getColumnIndex(Todo.COL_HOUR));
                        int STATUS = cursor.getInt(cursor.getColumnIndex(Todo.COL_STATUS));
                        int isNOTIFICATION = cursor.getInt(cursor.getColumnIndex(Todo.COL_NHACNHO));

                        if(STATUS == 0 && isNOTIFICATION == 1){
                            alarm.setAlarm(context,DATE,HOUR);
                        }
                    }
                }
            }catch (SQLiteException ex){
                System.out.println(ex.getMessage().toString());
            }
            finally {
                cursor.close();
            }
        }
    }
}
