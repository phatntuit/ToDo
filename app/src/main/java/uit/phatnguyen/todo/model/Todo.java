package uit.phatnguyen.todo.model;

import java.io.Serializable;

/**
 * Created by PhatNguyen on 2016-10-26.
 */
public class Todo implements Serializable{
    private int ID;
    private int TODO_FK;
    private String CONTENT;
    private String DATE;
    private String HOUR;
    private String LOCATION;
    private int STATUS; //1 LA HOAN TAT
    private int isNOTIFICATION; //1 LA CO NHAC NHO
    private String COLOR;
    private String NGAYTAO;
    private String NGAYSUA;

    //Khai bao truong cua cac cot trong CSDL;
    public final static String COL_ID = "ID";
    public final static String COL_TODOFK = "TODO_FK";
    public final static String COL_CONTENT = "CONTENT";
    public final static String COL_DATE = "DATE";
    public final static String COL_HOUR = "TIME";
    public final static String COL_LOCATION = "LOCATION";
    public final static String COL_STATUS = "STATUS";
    public final static String COL_COLOR = "COLOR";
    public final static String COL_NGAYTAO = "NGAYTAO";
    public final static String COL_NGAYSUA = "NGAYSUA";
    public final static String COL_NHACNHO = "isNOTIFICATION";

    public final static String TABLE_NAME = "TODOITEMS";

    public Todo() {
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getTODO_FK() {
        return TODO_FK;
    }
    public void setTODO_FK(int TODO_FK) {
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
    public int getSTATUS() {
        return STATUS;
    }
    public void setSTATUS(int STATUS) {
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
    public int getIsNOTIFICATION() {
        return isNOTIFICATION;
    }
    public void setIsNOTIFICATION(int isNOTIFICATION) {
        this.isNOTIFICATION = isNOTIFICATION;
    }
    @Override
    public String toString() {
        String todo = "";
        todo += "TASK : " + this.getID() ;
        todo += "--" + "CONTENT : " + this.getCONTENT();
        return todo;
    }
}
