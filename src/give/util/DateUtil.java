/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date strToDate(String strFormat, String strDate) throws ParseException {
        SimpleDateFormat dateFormater = new SimpleDateFormat(strFormat);
        Date date = dateFormater.parse(strDate);

        return date;
    }

    public static String dateToStr(String strFormat, Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(strFormat);
        return dateFormatter.format(date);
    }


    public static int getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }
}