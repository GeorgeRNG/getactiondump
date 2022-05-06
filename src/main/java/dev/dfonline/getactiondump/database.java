package dev.dfonline.getactiondump;

import java.io.IOException;
import java.util.Date;

public class database {
    public static Date startTime = new Date(System.currentTimeMillis());
    public static String Data;

    public database(){
        startTime = new Date(System.currentTimeMillis());
        Data = "";
    }

    public static void addData(String data){
        Data += data + "\n";
    }

    public static void save() throws IOException{
        FileManager.WriteFile("db.json",Data.replaceAll("\\n$",""));
    }
}
