package dev.dfonline.getactiondump;

import java.io.IOException;
import java.util.Date;

public class database {
    public static Date startTime = new Date(System.currentTimeMillis());
    public static String Data;

    public static int Length;
    public static int Lines;

    public database(){
        startTime = new Date(System.currentTimeMillis());
        Data = "";
        Length = 0;
        Lines = 0;
    }

    public static void addData(String data){
        Lines++;
        Length += data.length() + 1;
        Data += data + "\n";
    }

    public static void save() throws IOException{
        FileManager.WriteFile("db.json",Data.replaceAll("\\n$",""));
    }

    public void close(){
        GetActionDump.overlayText = new String[]{};
        GetActionDump.reportKeepAlives = false;
        if(GetActionDump.db != null){
            GetActionDump.db = null;
        }
    }
}
