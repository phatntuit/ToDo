package uit.phatnguyen.todo.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import uit.phatnguyen.todo.db.ToDoHelper;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmMgr;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("OnReceive","going there"+intent.toString());

        Bundle bundle1 = null;

        bundle1 = intent.getBundleExtra("alarm");

        String test = intent.getAction();
        Log.d("test", "onReceive: "+test);
        int alarmId = bundle1.getInt("alarmId",0);
        Log.d("ID", "onReceive: "+alarmId);


        Intent service = new Intent(context, SchedulingService.class);



        String title="";
        String content="";
        ToDoHelper toDoHelper = new ToDoHelper(context);
        Cursor cursor = toDoHelper.getQuery("Select * from TODOITEMS,TODOLIST where TODOLIST.ID = TODOITEMS.TODO_FK and TODOITEMS.ID = " + alarmId,null);
        try{
            if(cursor.getCount() > 0){
                for (int i =0 ; i< cursor.getCount() ; i++){
                    //chuyen con tro ve vi tri thu i
                    cursor.moveToPosition(i);
                    //cursor.getColumnIndex(TodoList.COL_TITLE) : LAY INDEX CUA COLUMN TITLE
                    title = cursor.getString(cursor.getColumnIndex("TITLE"));
                    content = cursor.getString(cursor.getColumnIndex("CONTENT"));

                }
            }
        }catch (SQLiteException ex){
            System.out.println(ex.getMessage().toString());
        }
        finally {
            cursor.close();
        }
        Bundle serviceBundle = new Bundle();
        serviceBundle.putString("title", title);
        serviceBundle.putString("content", content);
        service.putExtra("service",serviceBundle);
        startWakefulService(context, service);
    }
    public  void setAlarm(Context context,String ngayhen,String giohen,int id){
        Log.d("setAlarm","going there");
        Bundle bundle = new Bundle();
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        bundle.putInt("alarmId",id);
        Log.d("AddBundle alaramID",id+"");


        intent.putExtra("alarm",bundle);
        intent.putExtra("test","tau la du lieu gui qua tu set alarm");


        pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        int interval = 1000 * 60 * 10;

        int ngay,thang,nam;
        String date[] = ngayhen.split("-");
        ngay = Integer.parseInt(date[0]);
        thang = Integer.parseInt(date[1]);
        nam = Integer.parseInt(date[2]);
        int gio,phut;
        String hour[] = giohen.split(":");
        gio = Integer.parseInt(hour[0]);
        phut = Integer.parseInt(hour[1]);

        Log.d("Ngay", "setAlarm: "+ngay+"--"+thang+"--"+nam);
        Log.d("Gio", "setAlarm: "+gio+":"+phut);
        Log.d("ID", "setAlarm: "+id);
        /* Set the alarm to start*/

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, gio);
        calendar.set(Calendar.MINUTE, phut);
        calendar.set(Calendar.SECOND,00);
        calendar.set(Calendar.YEAR,nam);
        calendar.set(Calendar.MONTH,thang-1);
        calendar.set(Calendar.DATE,ngay);

        Log.d("Calendarinfo",calendar.toString()+"");
        /* Repeating on every 10 minutes interval */
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                 interval,pendingIntent);

        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    public void cancel(Context context,int id) {
        // If the alarm has been set, cancel it.
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        if (alarmMgr!= null) {
            alarmMgr.cancel(pendingIntent);
            Log.d("Cancel alarm", "cancel: ");
            Log.d("ID", "cancelAlarm: "+id);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
