package uit.phatnguyen.todo.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import uit.phatnguyen.todo.model.Todo;
import uit.phatnguyen.todo.model.TodoList;

/**
 * Created by PhatNguyen on 2016-10-27.
 */

public class ToDoHelper {
    private final static  String DATABASE_NAME = "TODO.sqlite";
    private  static SQLiteDatabase database;
    private Activity context;

    public ToDoHelper(Activity context) {
        this.context = context;
        database = MySQLiteDB.initDatabase(context,DATABASE_NAME);
    }
    public static SQLiteDatabase getDatabase() {
        return database;
    }
    public Cursor getQuery(String sql,String[] params){
        Cursor cursor;
        if (params == null){
            cursor = database.rawQuery(sql,null);
        }
        cursor = database.rawQuery(sql,params);
        return  cursor;
    }
    public void executeQuery(String sql,String[] params){
        database.execSQL(sql,params);
    }
    public long insertToDoList(TodoList tdl){
        ContentValues contentValues = new ContentValues();
        //Put các trường vào
        contentValues.put(TodoList.COL_ID,tdl.getID());
        contentValues.put(TodoList.COL_TITLE,tdl.getTITLE());
        contentValues.put(TodoList.COL_COLOR,tdl.getCOLOR());
        contentValues.put(TodoList.COL_NGAYTAO,tdl.getNGAYTAO());
        contentValues.put(TodoList.COL_NGAYSUA,tdl.getNGAYSUA());

        return database.insert(TodoList.TABLE_NAME,null,contentValues);
    }
    public long insertToDoItem(Todo todo){
        ContentValues contentValues = new ContentValues();
        //Put các trường vào
        contentValues.put(Todo.COL_ID,todo.getID());
        contentValues.put(Todo.COL_TODOFK,todo.getTODO_FK());
        contentValues.put(Todo.COL_CONTENT,todo.getCONTENT());
        contentValues.put(Todo.COL_DATE,todo.getDATE());
        contentValues.put(Todo.COL_HOUR,todo.getHOUR());
        contentValues.put(Todo.COL_LOCATION,todo.getLOCATION());
        contentValues.put(Todo.COL_STATUS,todo.getSTATUS());
        contentValues.put(Todo.COL_COLOR,todo.getCOLOR());
        contentValues.put(Todo.COL_NGAYTAO,todo.getNGAYTAO());
        contentValues.put(Todo.COL_NGAYSUA,todo.getNGAYSUA());

        return database.insert(Todo.TABLE_NAME,null,contentValues);
    }
    public int getNext(String table_name, String col_integer){
        String query = "SELECT " + col_integer + " FROM " + table_name + " ORDER BY "+ col_integer
                +" DESC LIMIT 1";
        System.out.println(query);
        Cursor cursor = getQuery(query,null);
        cursor.moveToFirst();
        if(cursor.isNull(0))
            return 0;
        else
            return cursor.getInt(0) + 1;
    }
    /*
    for loop data use cursor
            for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new NhanVien(id, ten, sdt, anh));
        }
     */


}
