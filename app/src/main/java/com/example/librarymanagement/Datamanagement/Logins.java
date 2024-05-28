package com.example.librarymanagement.Datamanagement;

import com.example.librarymanagement.SQLServerHelper.SQLmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Logins {
    String Users;
    String Passwords;

    // hàm constructor
    public Logins(){
        Users = "";
        Passwords = "";
    };
    public Logins(String user, String pass) {
        Users = user;
        this.Passwords = pass;
    }

    // hàm lấy tài khoản
    public static Logins getuserlist(String user,String passWords) throws SQLException {
        Connection connection = SQLmanagement.connectionSQLSever();
        Logins logins = new Logins();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Logins where Users = '" + user + "' and Passwords = '" + passWords +"'";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            logins = new Logins(
                    rs.getString(1).trim(),
                    rs.getString(2).trim());// Đọc dữ liệu từ ResultSet)
        }

        statement.close();
        connection.close();// Đóng kết nối
        return logins;
    }


    public String getUser() {
        return Users;
    }

    public void setUser(String user) {
        Users = user;
    }

    public String getPass() {
        return Passwords;
    }

    public void setPass(String pass) {
        this.Passwords = pass;
    }
}
