package com.example.librarymanagement.Datamanagement;

import com.example.librarymanagement.SQLServerHelper.SQLmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookCatalogings {
    private String IdBookCataloging;
    private String Heading;
    private String Author;
    private String ISBN;
    private String Publishing;
    private String Genre;

    public  BookCatalogings(){};
    public BookCatalogings(String idBookCataloging){
        this.IdBookCataloging = idBookCataloging;
    }

    public BookCatalogings(String idBookCataloging, String heading, String author, String ISBN, String publishing, String genre) {
        IdBookCataloging = idBookCataloging;
        Heading = heading;
        Author = author;
        this.ISBN = ISBN;
        Publishing = publishing;
        Genre = genre;
    }

    public static ArrayList<BookCatalogings> getuserlist() throws SQLException { // Hàm lấy dữ liệu
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL server
        ArrayList<BookCatalogings> list = new ArrayList<>(); // Tạo list để lưu dữ liệu
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from BookCatalogings"; // Câu lênh truy vấn SQL Server lấy ra dữ liệu trong bảng
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new BookCatalogings(
                    rs.getString("IdBookCataloging").trim(), // Lấy dữ liệu ỏ cột IdBookSummary
                    rs.getString("Heading").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("Author"), // Lấy dữ liệu ỏ cột
                    rs.getString("ISBN"), // Lấy dữ liệu ỏ cột
                    rs.getString("Publishing"), // Lấy dữ liệu ỏ cột
                    rs.getString("Genre")));// Đọc dữ liệu từ ResultSet
        }
        statement.close(); // Đóng đối tương statement
        connection.close();// Đóng kết nối
        return list; // Trả về list
    }

    public static BookCatalogings getuserlist(String idBookCataloging) throws SQLException{
        Connection connection = SQLmanagement.connectionSQLSever();
        BookCatalogings bookCatalogings = new BookCatalogings();
        Statement statement = connection.createStatement();
        String sql = "select * from BookCatalogings where IdBookCataloging = '" + idBookCataloging + "'";
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            bookCatalogings = new BookCatalogings(
                    rs.getString("IdBookCataloging").trim(), // Lấy dữ liệu ỏ cột IdBookSummary
                    rs.getString("Heading").trim(), // Lấy dữ liệu ỏ cột MainContent
                    rs.getString("Author"), // Lấy dữ liệu ỏ cột
                    rs.getString("ISBN"), // Lấy dữ liệu ỏ cột
                    rs.getString("Publishing"), // Lấy dữ liệu ỏ cột
                    rs.getString("Genre")
            );
        }
        return bookCatalogings;
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
                "',N'" + heading + "',N'" + author + "',N'" + isbn + "',N'" +  publishing + "',N'" + genre + "')"; // Câu lênh SQL Server thêm hàng mới trong bảng BookSummary
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }

    public static void updateList(String idBookCataloging,String heading,String author,String isbn,String publishing,String genre) throws SQLException{ // Hàm thêm 1 tóm tắt sách
        Connection connection = SQLmanagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "update BookCatalogings set Heading = '" + heading + "',Author = '" + author + "',ISBN = '" + isbn + "',Publishing = '" +
            publishing +"',Genre = '" + genre + "' where IdBookCataloging = '" + idBookCataloging + "'"; // Câu lênh SQL Server thêm hàng mới trong bảng BookSummary
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }


    public String getIdBookCataloging() {
        return IdBookCataloging;
    }

    public void setIdBookCataloging(String idBookCataloging) {
        IdBookCataloging = idBookCataloging;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublishing() {
        return Publishing;
    }

    public void setPublishing(String publishing) {
        Publishing = publishing;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
