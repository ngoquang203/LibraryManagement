package com.example.librarymanagement.Datamanagement;

import com.example.librarymanagement.SQLServerHelper.SQLmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookSummarys {
    private String IdBookSummary;
    private String MainContent;
    private String Meaning;
    private String CommunicationGoals;

    // hàm constructor
    public BookSummarys(){};
    public BookSummarys(String idBookSummary){
        this.IdBookSummary = idBookSummary;
    }

    public BookSummarys(String idBookSummary, String mainContent, String meaning, String communicationGoals) {
        IdBookSummary = idBookSummary;
        MainContent = mainContent;
        Meaning = meaning;
        CommunicationGoals = communicationGoals;
    }
    // hàm in ra danh sách tóm tắt sách
    public static ArrayList<BookSummarys> getuserlist() throws SQLException { // Hàm lấy dữ liệu
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL server
        ArrayList<BookSummarys> list = new ArrayList<>(); // Tạo list để lưu dữ liệu
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from BookSummarys"; // Câu lênh truy vấn SQL Server lấy ra dữ liệu trong bảng
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new BookSummarys(
                    rs.getString("IdBookSummary").trim(), // Lấy dữ liệu ỏ cột IdBookSummary
                    rs.getString("MainContent").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("Meaning"), // Lấy dữ liệu ỏ cột
                    rs.getString("CommunicationGoals")));// Đọc dữ liệu từ ResultSet
        }
        statement.close(); // Đóng đối tương statement
        connection.close();// Đóng kết nối
        return list; // Trả về list
    }
    // hàm in ra tóm tắt sách thông qua idBookSummary
    public static BookSummarys getuserlist(String idBookSummary) throws SQLException{
        Connection connection = SQLmanagement.connectionSQLSever(); // kết nối SQL
        BookSummarys bookSummarys = new BookSummarys(); // tạo đối tượng
        Statement statement = connection.createStatement(); // tạo statement
        String sql = "select * from BookSummarys where IdBookSummary = '" + idBookSummary + "'"; // tạo câu lệnh query
        ResultSet rs = statement.executeQuery(sql); // thực thi câu lênh SQL trả về đối tượng réultset
        if(rs.next()){
            bookSummarys = new BookSummarys(
                    rs.getString("IdBookSummary").trim(), // Lấy dữ liệu ỏ cột IdBookSummary
                    rs.getString("MainContent").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("Meaning"), // Lấy dữ liệu ỏ cột
                    rs.getString("CommunicationGoals")// Đọc dữ liệu từ ResultSet
            );
        }
        return bookSummarys;
    }
    // hàm xóa tóm tắt sách thông qua idBookSummary
    public static void deleteList(String idBookSummary) throws SQLException{ // Hàm xóa dữ liệu hàng trong bảng BookSumary
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "delete from BookSummarys where IdBookSummary = '" + idBookSummary + "'"; // Câu lênh SQL Server xóa hàng có Cột IdBookSummary trung với dữ liệu truyền vào
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tương Statament
        connection.close(); // Đóng kết nối
    }
    // hàm ínsert tóm tắt sách
    public static void insertList(String idBookSummary,String mainContent,String meaning,String communicationGoals) throws SQLException{ // Hàm thêm 1 tóm tắt sách
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "insert into BookSummarys(IdBookSummary,MainContent,Meaning,CommunicationGoals) values ('"+
                idBookSummary + "',N'" + mainContent + "',N'" + meaning + "',N'" + communicationGoals + "')"; // Câu lênh SQL Server thêm hàng mới trong bảng BookSummary
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }

    // hàm update tóm tắt sách
    public static void updateList(String idBookSummary,String mainContent,String meaning,String communicationGoals) throws SQLException{ // Hàm thêm 1 tóm tắt sách
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "update BookSummarys set MainContent = '" + mainContent + "',Meaning = '" + meaning + "',CommunicationGoals = '" +
                communicationGoals + "' where IdBookSummary = '" + idBookSummary + "'"; // Câu lênh SQL Server sửa đổi thông tin tóm tắt sách
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối

    }





    public String getIdBookSummary() {
        return IdBookSummary;
    }

    public void setIdBookSummary(String idBookSummary) {
        IdBookSummary = idBookSummary;
    }

    public String getMainContent() {
        return MainContent;
    }

    public void setMainContent(String mainContent) {
        MainContent = mainContent;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getCommunicationGoals() {
        return CommunicationGoals;
    }

    public void setCommunicationGoals(String communicationGoals) {
        CommunicationGoals = communicationGoals;
    }
}
