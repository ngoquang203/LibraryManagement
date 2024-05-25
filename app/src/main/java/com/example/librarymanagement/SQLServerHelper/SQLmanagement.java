package com.example.librarymanagement.SQLServerHelper;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLmanagement {
    private static String sql = "jdbc:jtds:sqlserver://192.168.0.219:1433;databasename=CNPM;user=dataCNPM;password=ngoquanghy123"; // dia chi ket noi
    public static Connection connectionSQLSever(){
        Connection connection = null;
        try {
            try {
                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build(); // lay tat ca cac quyen
                StrictMode.setThreadPolicy(policy); // thiet lap chinh sac ket noi bao gom tat ca cac quyen
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(sql);
                Log.i("THONG BAO:","Ket noi thanh cong");
            } catch (Exception e) {
                Log.e("THONGBAO","LOSS");
                throw new RuntimeException(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
