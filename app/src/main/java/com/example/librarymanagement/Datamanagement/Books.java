package com.example.librarymanagement.Datamanagement;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.librarymanagement.SQLServerHelper.SQLmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Books {
    private String IdBooks;
    private byte[] CoverImage;
    private String IdBookCataloging;
    private String IdBookSummary;
    private int NumberPage;
    private int NumberBook;
    private String Status;

    public Books(String idBooks, byte[] coverImage, String idBookCataloging, String idBookSummary, int numberPage, int numberBook, String status) {
        IdBooks = idBooks;
        CoverImage = coverImage;
        IdBookCataloging = idBookCataloging;
        IdBookSummary = idBookSummary;
        NumberPage = numberPage;
        NumberBook = numberBook;
        Status = status;
    }

    public static ArrayList<Books> getuserlist() throws SQLException { // Hàm lấy dữ liệu
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL server
        ArrayList<Books> list = new ArrayList<>(); // Tạo list để lưu dữ liệu
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Books"; // Câu lênh truy vấn SQL Server lấy ra dữ liệu trong bảng
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new Books(
                    rs.getString("IdBooks").trim(), // Lấy dữ liệu ỏ cột IdBookSummary
                    rs.getBytes("CoverImage"),
                    rs.getString("IdBookCataloging").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("IdBookSummary"), // Lấy dữ liệu ỏ cột
                    rs.getInt("NumberPage"), // Lấy dữ liệu ỏ cột
                    rs.getInt("NumberBook"), // Lấy dữ liệu ỏ cột
                    rs.getString("Status")));// Đọc dữ liệu từ ResultSet
        }
        statement.close(); // Đóng đối tương statement
        connection.close();// Đóng kết nối
        return list; // Trả về list
    }

    public static void insertList(String idBooks,byte[] coverImage,String idBookCataloging,String idBookSummary,int numberPage,int numberBook,String status) throws SQLException{ // Hàm thêm 1 tóm tắt sách
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        String sql = "insert into Books(IdBooks,CoverImage,IdBookCataloging,IdBookSummary,NumberPage,NumberBook,Status) values (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,idBooks);
        preparedStatement.setBytes(2,coverImage);
        preparedStatement.setString(3,idBookCataloging);
        preparedStatement.setString(4,idBookSummary);
        preparedStatement.setInt(5,numberPage);
        preparedStatement.setInt(6,numberBook);
        preparedStatement.setString(7,status);
        preparedStatement.execute();
        preparedStatement.close();
        connection.close(); // Đóng kết nối
    }


    public String getIdBooks() {
        return IdBooks;
    }

    public void setIdBooks(String idBooks) {
        IdBooks = idBooks;
    }

    public static void deleteList(String idBookCataloging) throws SQLException{ // Hàm xóa dữ liệu hàng trong bảng BookSumary
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "delete from BookCatalogings where IdBookCataloging = '" + idBookCataloging + "'"; // Câu lênh SQL Server xóa hàng có Cột IdBookSummary trung với dữ liệu truyền vào
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tương Statament
        connection.close(); // Đóng kết nối
    }

    public static void insertList(String idBookCataloging,String heading,String author,String isbn,String publishing,String genre) throws SQLException{ // Hàm thêm 1 tóm tắt sách
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "insert into BookCatalogings(IdBookCataloging,Heading,Author,ISBN,Publishing,Genre) values ('" + idBookCataloging +
                "','" + heading + "','" + author + "','" + isbn + "','" +  publishing + "','" + genre + "')"; // Câu lênh SQL Server thêm hàng mới trong bảng BookSummary
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }

    public byte[] getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        CoverImage = coverImage;
    }

    public String getIdBookSummary() {
        return IdBookSummary;
    }

    public void setIdBookSummary(String idBookSummary) {
        IdBookSummary = idBookSummary;
    }

    public String getIdBookCataloging() {
        return IdBookCataloging;
    }

    public void setIdBookCataloging(String idBookCataloging) {
        IdBookCataloging = idBookCataloging;
    }

    public int getNumberPage() {
        return NumberPage;
    }

    public void setNumberPage(int numberPage) {
        NumberPage = numberPage;
    }

    public int getNumberBook() {
        return NumberBook;
    }

    public void setNumberBook(int numberBook) {
        NumberBook = numberBook;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
