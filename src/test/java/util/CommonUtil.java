package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonUtil {

    public static String getCurrentSystemDateTime(){
        SimpleDateFormat format = new SimpleDateFormat("_YYYY-MM-dd-HH-mm-ss");
        Calendar cal = Calendar.getInstance();
        return format.format(cal.getTime());
    }
}
