package uit.phatnguyen.todo.model;

/**
 * Created by PhatNguyen on 2016-10-26.
 */

public class Todo {
    private String ID;
    private String TODO_FK;
    private String CONTENT;
    private String DATE;
    private String HOUR;
    private String LOCATION;
    private Boolean STATUS; //TRUE LA HOAN THANH
    private String COLOR;
    private String NGAYTAO;
    private String NGAYSUA;

    //Khai bao truong cua cac cot trong CSDL;
    public final static String COL_ID = "ID";
    public final static String COL_TODOFK = "TODO_FK";
    public final static String COL_CONTENT = "CONTENT";
    public final static String COL_DATE = "DATE";
    public final static String COL_HOUR = "HOUR";
    public final static String COL_LOCATION = "LOCATION";
    public final static String COL_STATUS = "STATUS";
    public final static String COL_COLOR = "COLOR";
    public final static String COL_NGAYTAO = "NGAYTAO";
    public final static String COL_NGAYSUA = "NGAYSUA";

    public final static String TABLE_NAME = "TODOITEMS";

    public Todo(String ID, String TODO_FK, String CONTENT, String DATE, String HOUR,
                String LOCATION, Boolean STATUS, String COLOR, String NGAYTAO, String NGAYSUA) {
        this.ID = ID;
        this.TODO_FK = TODO_FK;
        this.CONTENT = CONTENT;
        this.DATE = DATE;
        this.HOUR = HOUR;
        this.LOCATION = LOCATION;
        this.STATUS = STATUS;
        this.COLOR = COLOR;
        this.NGAYTAO = NGAYTAO;
        this.NGAYSUA = NGAYSUA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTODO_FK() {
        return TODO_FK;
    }

    public void setTODO_FK(String TODO_FK) {
        this.TODO_FK = TODO_FK;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getHOUR() {
        return HOUR;
    }

    public void setHOUR(String HOUR) {
        this.HOUR = HOUR;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public Boolean getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Boolean STATUS) {
        this.STATUS = STATUS;
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
}
