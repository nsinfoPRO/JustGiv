/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class ValidationUtil {
    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_NAME  = "^[\\w\\s-]+$";
    
    public static final int CHECK_TYPE_EMAIL = 1;
    public static final int CHECK_TYPE_USER  = 2;
    
    public static final boolean isVaild(int nType, String strVal) {
        String strPattern = "";
        if(nType == CHECK_TYPE_EMAIL) {
            strPattern = REGEX_EMAIL;
        }
        else if(nType == CHECK_TYPE_USER) {
            strPattern = REGEX_NAME;
        }
        
        if(strPattern.length() == 0) {
            return false;
        }
        
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strVal);
        
        return m.find();
    }
    
    public static boolean isValidEmail(String strEmail) {
        return isVaild(CHECK_TYPE_EMAIL, strEmail);
    }
    
    public static boolean isVaildName(String strName) {
        return isVaild(CHECK_TYPE_USER, strName);
    }
}
