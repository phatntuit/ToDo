package uit.phatnguyen.todo.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

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

        Cursor cursor = null;
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

        long kq = database.insert(TodoList.TABLE_NAME,null,contentValues);

        return kq;
    }
    public int updateToDoList(TodoList tdl){

        ContentValues contentValues = new ContentValues();
        String[] whereArgs = new String[]{tdl.getID()+""};
        //Put các trường vào
        //bo ID ra vi ID la khoa chinh de where
        //contentValues.put(TodoList.COL_ID,tdl.getID());
        contentValues.put(TodoList.COL_TITLE,tdl.getTITLE());
        contentValues.put(TodoList.COL_COLOR,tdl.getCOLOR());
        contentValues.put(TodoList.COL_NGAYTAO,tdl.getNGAYTAO());
        contentValues.put(TodoList.COL_NGAYSUA,tdl.getNGAYSUA());

        int kq = database.update(tdl.TABLE_NAME,contentValues,tdl.WHERE_ID,whereArgs);

        return kq;
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
        contentValues.put(Todo.COL_NHACNHO,todo.getIsNOTIFICATION());

        long kq = database.insert(Todo.TABLE_NAME,null,contentValues);

        return kq;
    }
    public int updateToDoItem(Todo todo){
        ContentValues contentValues = new ContentValues();
        String[] whereArgs = new String[]{todo.getID()+""};
        //Put các trường vào
        //contentValues.put(Todo.COL_ID,todo.getID());
        contentValues.put(Todo.COL_TODOFK,todo.getTODO_FK());
        contentValues.put(Todo.COL_CONTENT,todo.getCONTENT());
        contentValues.put(Todo.COL_DATE,todo.getDATE());
        contentValues.put(Todo.COL_HOUR,todo.getHOUR());
        contentValues.put(Todo.COL_LOCATION,todo.getLOCATION());
        contentValues.put(Todo.COL_STATUS,todo.getSTATUS());
        contentValues.put(Todo.COL_COLOR,todo.getCOLOR());
        contentValues.put(Todo.COL_NGAYTAO,todo.getNGAYTAO());
        contentValues.put(Todo.COL_NGAYSUA,todo.getNGAYSUA());
        contentValues.put(Todo.COL_NHACNHO,todo.getIsNOTIFICATION());

        int kq = database.update(Todo.TABLE_NAME,contentValues,Todo.WHERE_ID,whereArgs);

        return kq;
    }
    public int getNext(String tableName, String colInteger){

        int id = 0;
        String query = "SELECT " + colInteger + " FROM " + tableName + " ORDER BY "+ colInteger
                +" DESC LIMIT 1";
        System.out.println(query);
        Cursor cursor = getQuery(query,null);
        try {
            cursor.moveToFirst();
            if(cursor.isNull(0))
                id = 0;
            else
                id = cursor.getInt(0) + 1;
        }catch (SQLiteException ex){
            System.out.println(ex.getMessage());
        }finally {
            cursor.close();
        }

        return id;
    }
    public boolean checkId(String tableName,String colName,int value){

        boolean kq = false;
        String query = "SELECT * FROM " + tableName + " WHERE " + colName + " = "+value;
        System.out.println(query);
        Cursor cursor = getQuery(query,null);
        try{
            cursor.moveToFirst();
            if(cursor.getCount() == 0)
                kq = false;
            else
                kq = true;
        }catch (SQLiteException ex){
            System.out.println(ex.getMessage());
        }finally {
            cursor.close();
        }

        return kq;
    }
    public ArrayList<Todo> getList(int listId){
        ArrayList<Todo> arr = new ArrayList<Todo>();

        //Truy van lay du lieu tu bang TODOLIST co id = idList
        Cursor  cursor = getQuery("SELECT * FROM TODOITEMS WHERE TODO_FK = " + listId,null);
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
    public int deleteToDoItem(int id, int type){
        String[] whereArgs = new String[]{id+""};
        // type = 1 xoa item khi biet itemId
        //type = 0 xoa iten khi biet listId
        int kq = 0;
        String where_clause = (type == 1) ? Todo.WHERE_ID : Todo.WHERE_TODOFK;

        kq = database.delete(Todo.TABLE_NAME,where_clause,whereArgs);
        return kq;
    }
    public boolean deleteToDoList(int listId){
        String[] whereArgs = new String[]{listId+""};
        int kqItem = 0;
        int kqLsit = 0;
        //Lay so item cua list truoc
        int numItems = 0;
        //do minh can xoa item dua vao listId nen truyen vao type = 0
        try{
            numItems = numItemList(listId);
            kqItem = deleteToDoItem(listId,0);
            kqLsit = database.delete(TodoList.TABLE_NAME,TodoList.WHERE_ID,whereArgs);
        }catch (SQLiteException ex){
            System.out.println("Loi : "+ex.getMessage());
        }
        if(kqItem == numItems && kqLsit == 1)
            return true;
        return false;
    }
    public int numItemList(int listId){
        Cursor cursor = null;
        int num = 0;
        try{
            cursor  = getQuery("SELECT * FROM TODOITEMS WHERE TODO_FK = " + listId,null);
            cursor.moveToFirst();
            num = cursor.getCount();
        }catch (SQLiteException ex){
            System.out.println("numItemList error : " + ex.getMessage());
        }
        finally {
            cursor.close();
        }
        return  num;
    }
}
