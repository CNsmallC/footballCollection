package cn.smallc.footballcollection.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
	
	public static String getErrorInfoFromException(Exception e) {
        try {  
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";  
        }  
    }

}
