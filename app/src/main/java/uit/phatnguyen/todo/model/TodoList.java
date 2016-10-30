package uit.phatnguyen.todo.model;

import uit.phatnguyen.todo.helper.MyUtility;

/**
 * Created by PhatNguyen on 2016-10-26.
 */

public class TodoList {
    private int ID;
    private String TITLE;
    private String COLOR ;
    private String NGAYTAO;
    private String NGAYSUA;

    //Khai bao truong cua cac cot trong CSDL;
    public final static String COL_ID = "ID";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_COLOR = "COLOR";
    public final static String COL_NGAYTAO = "NGAYTAO";
    public final static String COL_NGAYSUA = "NGAYSUA";
    public final static String TABLE_NAME = "TODOLIST";
    public final static String WHERE_ID = "ID =?";

    public TodoList() {
       this.COLOR = "YELLOW";
        this.NGAYSUA = MyUtility.getCurrentDate();
        this.NGAYTAO = MyUtility.getCurrentDate();
    }

    public TodoList(int ID, String TITLE, String COLOR, String NGAYTAO, String NGAYSUA) {
        this.ID = ID;
        this.TITLE = TITLE;
        this.COLOR = COLOR;
        this.NGAYTAO = NGAYTAO;
        this.NGAYSUA = NGAYSUA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public void setCOLOR(String COLOR) {
        this.COLOR = COLOR;
    }

    public String getNGAYTAO() {
        return NGAYTAO;
    }

    public void setNGAYTAO(String NGAYTAO) {
        this.NGAYTAO = NGAYTAO;
    }

    public String getNGAYSUA() {
        return NGAYSUA;
    }

    public void setNGAYSUA(String NGAYSUA) {
        this.NGAYSUA = NGAYSUA;
    }

    @Override
    public String toString() {
        String out = "";
        out += "ID : " + this.getID();
        out += "--";
        out += "TITLE : " + this.getTITLE();
        return out;
    }
}
