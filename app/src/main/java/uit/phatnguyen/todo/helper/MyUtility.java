package uit.phatnguyen.todo.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by PhatNguyen on 2016-10-26.
 */

public final class MyUtility {
    public static String getCurrentTime(){
        String hour24;
        //Lấy đối tượng Calendar ra, mặc định ngày hiện tại
        Calendar now = Calendar.getInstance();

        //Muốn xuất Giờ:Phút theo kiểu 24h
        String strDateFormat24 = "HH:mm";
        SimpleDateFormat sdf = null;

        //Tạo đối tượng SimpleDateFormat với định dạng 24
        sdf = new SimpleDateFormat(strDateFormat24);
        hour24 = sdf.format(now.getTime());
        return hour24;
    }
    public static String getCurrentDate(){
        String date;
        //Lấy đối tượng Calendar ra, mặc định ngày hiện tại
        Calendar now = Calendar.getInstance();
        //Lấy ngày theo kiểu ngày/tháng/năm
        String strDateFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        date = sdf.format(now.getTime());
        return date;
    }
    public static String getCurrentDateTime(){
        String datetime;
        //Lấy đối tượng Calendar ra, mặc định ngày hiện tại
        Calendar now = Calendar.getInstance();
        //Lấy ngày theo kiểu ngày/tháng/năm
        String strDateFormat = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        datetime = sdf.format(now.getTime());
        return datetime;
    }
    //Tham khảo từ duythanhcse
    public static void SudungCalendar()
    {
        //Lấy đối tượng Calendar ra, mặc định ngày hiện tại
        Calendar now = Calendar.getInstance();
        //Muốn xuất Giờ:Phút:Giây AM (PM)
        String strDateFormat12 = "hh:mm:ss a";
        String strDateFormat24 = "HH:mm:ss";
        SimpleDateFormat sdf = null;
        //Tạo đối tượng SimpleDateFormat với định dạng 12
        sdf= new SimpleDateFormat(strDateFormat12, Locale.getDefault());
        //1. gọi hàm format để lấy giờ:phút:giây loại 12
        System.out.println("Giờ định dạng 12 : " +
                sdf.format(now.getTime()));
        //Tạo đối tượng SimpleDateFormat với định dạng 24
        sdf = new SimpleDateFormat(strDateFormat24);
        //2. gọi hàm format để lấy giờ:phút:giây loại 24
        System.out.println("Giờ định dạng 24 : " +
                sdf.format(now.getTime()));

        String strDateFormat = "dd/MM/yyyy hh:mm:ss a";
        sdf = new SimpleDateFormat(strDateFormat);
        System.out.println("Bây giờ là: " + sdf.format(now.getTime()));

        System.out.println("Năm hiện tại : " + now.get(Calendar.YEAR));
        //Tháng tính từ 0 tới 11, nên phải + thêm 1
        System.out.println("Tháng hiện tại : " +
                (now.get(Calendar.MONTH) + 1 ));
        System.out.println("Ngày hiện tại : " +
                now.get(Calendar.DATE));

        System.out.println("Lấy giờ định dạng 12 là : "
                + now.get(Calendar.HOUR));
        System.out.println("Lấy giờ định dạng 24 là : "
                + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("Phút hiện tại : " +
                now.get(Calendar.MINUTE));
        System.out.println("Giây hiện tại : " +
                now.get(Calendar.SECOND));
        System.out.println("Mili giây hiện tại: " +
                now.get(Calendar.MILLISECOND));
    }
}
