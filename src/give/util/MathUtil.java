/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package give.util;

/**
 *
 * @author Administrator
 */
public class MathUtil {
    public static String round(String a, int n){
        int s = 1;
        String result;
        for (int i = 0;i < n;i ++)
            s *= 10;
        int temp = Math.round(Float.valueOf(a) * s); 
        float c = (float)temp / s;
        result = String.valueOf(c);
        if (c == 0) {
            result = "0";
        }

        return result;
    }
}
