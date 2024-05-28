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
    private byte[] coverImage;
    private String IdBookCataloging;
    private String IdBookSummary;
    private int NumberPage;
    private long Price;
    private int NumberBook;
    private String Status;

    // tạo constructor

    public Books(String idBooks, byte[] coverImage, String idBookCataloging, String idBookSummary, int numberPage, long price, int numberBook, String status) {
        IdBooks = idBooks;
        this.coverImage = coverImage;
        IdBookCataloging = idBookCataloging;
        IdBookSummary = idBookSummary;
        NumberPage = numberPage;
        Price = price;
        NumberBook = numberBook;
        Status = status;
    }

    // hàm lấy danh sách
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
                    rs.getBytes("CoverImages"),
                    rs.getString("IdBookCataloging").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("IdBookSummary"), // Lấy dữ liệu ỏ cột
                    rs.getInt("NumberPage"),// Lấy dữ liệu ỏ cột
                    rs.getLong("Price"),// Lấy dữ liệu ỏ cột
                    rs.getInt("NumberBook"), // Lấy dữ liệu ỏ cột
                    rs.getString("Status")));// Đọc dữ liệu từ ResultSet
        }
        statement.close(); // Đóng đối tương statement
        connection.close();// Đóng kết nối
        return list; // Trả về list
    }

    // hàm delete sách
    public static void deleteList(String idBooks) throws SQLException{ // Hàm xóa dữ liệu hàng trong bảng Book
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "delete from Books where IdBooks =  '" + idBooks + "'"; // Câu lênh SQL Server xóa hàng có Cột IdBook trung với dữ liệu truyền vào
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tương Statament
        connection.close(); // Đóng kết nối
    }

    // hàm thêm sách vào danh sách
    public static void insertList(String idBooks, byte[] coverImage, String idBookCataloging, String idBookSummary, String numberPage, String price, String numberBook, String status) throws SQLException{
        Connection connection = SQLmanagement.connectionSQLSever(); // kết nối SQL
        String sql = "insert into Books(IdBooks,CoverImages,IdBookCataloging,IdBookSummary,NumberPage,Price,NumberBook,Status) values  (?,?,?,?,?,?,?,?)"; // câu lênh query
        PreparedStatement preparedStatement = connection.prepareStatement(sql); // tạo đối tượng PreparedStatement
        // truyền dữ liệu
        preparedStatement.setString(1,idBooks);
        preparedStatement.setBytes(2,coverImage);
        preparedStatement.setString(3,idBookCataloging);
        preparedStatement.setString(4,idBookSummary);
        preparedStatement.setInt(5, Integer.parseInt(numberPage));
        preparedStatement.setLong(6, Long.parseLong(price));
        preparedStatement.setInt(7, Integer.parseInt(numberBook));
        preparedStatement.setString(8,status);
        preparedStatement.execute(); // thực thi câu lệnh
        preparedStatement.close(); // đóng đối tượng
        connection.close(); // đóng kết nối
    }

    // hàm cập nhật sách
    public static void updateList(String idBooks, byte[] coverImage, String idBookCataloging, String idBookSummary, String numberPage, String price, String numberBook, String status) throws SQLException{
        Connection connection = SQLmanagement.connectionSQLSever(); // kết nối sql
        String sql = "update Books set CoverImages = ?, IdBookCataloging = ?, IdBookSummary = ?, NumberPage = ?, Price = ?, NumberBook = ?, Status = ? where IdBooks = ?"; // câu lênh query
        PreparedStatement preparedStatement = connection.prepareStatement(sql); // tạo đối tượng PreparedStatement
        // truyền dữ liệu
        preparedStatement.setBytes(1,coverImage);
        preparedStatement.setString(2,idBookCataloging);
        preparedStatement.setString(3,idBookSummary);
        preparedStatement.setInt(4,Integer.valueOf(numberPage));
        preparedStatement.setLong(5,Long.valueOf(price));
        preparedStatement.setInt(6,Integer.valueOf(numberBook));
        preparedStatement.setString(7,status);
        preparedStatement.setString(8,idBooks);
        preparedStatement.executeUpdate(); // thực thi câu lệnh
        preparedStatement.close(); // đóng đối tượng
        connection.close(); // đóng kết nối
    }
    public byte[] getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        this.coverImage = coverImage;
    }

    public long getPrice() {
        return Price;
    }

    public void setPrice(long price) {
        Price = price;
    }

    public String getIdBooks() {
        return IdBooks;
    }

    public void setIdBooks(String idBooks) {
        IdBooks = idBooks;
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
