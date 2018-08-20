package cn.smallc.footballcollection.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SC_IOUtils {
    public static List<String> bufferedReadFile(String propertiesName){
        List<String> list=new ArrayList<String>();
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesName), "UTF-8"));
            String str="";
            while( ( str = br.readLine() ) != null )  {
                if(str.startsWith("#")){
                    continue;
                }
//                System.out.println(str);
                list.add(str);
            }
            br.close();
        }
        catch( FileNotFoundException e )
        {
            e.printStackTrace();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return list;
    }
}
