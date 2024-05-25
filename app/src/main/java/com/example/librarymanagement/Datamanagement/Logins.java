package com.example.librarymanagement.Datamanagement;

import com.example.librarymanagement.SQLServerHelper.SQLmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Logins {
    String User;
    String pass;
    public Logins(){};
    public Logins(String user, String pass) {
        User = user;
        this.pass = pass;
    }

    public static ArrayList<Logins> getuserlist() throws SQLException {
        Connection connection = SQLmanagement.connectionSQLSever();
        ArrayList<Logins> list = new ArrayList<>();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Logins";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new Logins(
                    rs.getString("ID").trim(),
                    rs.getString("Passwords").trim()));// Đọc dữ liệu từ ResultSet
        }
        statement.close();
        connection.close();// Đóng kết nối
        return list;
    }


    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
