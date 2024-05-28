package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class AddBookCataloging extends AppCompatActivity {
    // tạo các biến
    private boolean initData;
    private String IdBookCataLoging;
    private TextView textHeading;
    private ImageButton imageButtonBack;
    private Button saveDataBookCataloging;
    private TextInputEditText idBookCataloging,heading,author,ISBN,publishing,genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_cataloging);
        Init(); // ánh xạ các view
        changePageBack(); // quay lại màn hình quản lí biên mục sách
        saveDataBookCataloging(); // lưu biên mục sách
    }
    // hàm chuyển màn hình
    private void changeToPage(){
        Intent intent = new Intent(AddBookCataloging.this,BookCatalogingManagement.class);
        startActivity(intent);
    }

    private void saveDataBookCataloging() {
        if(initData){
            saveDataBookCataloging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // lấy dữ liệu từ ô nhập và update dữ liệu vào SQL
                    String Heading = heading.getText().toString();
                    String Author = author.getText().toString();
                    String isbn = ISBN.getText().toString();
                    String Publishing = publishing.getText().toString();
                    String Genre = genre.getText().toString();
                    if(Heading.isEmpty() && Author.isEmpty() && isbn.isEmpty() && Publishing.isEmpty() && Genre.isEmpty()){
                        Toast.makeText(AddBookCataloging.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            BookCatalogings.updateList(
                                    IdBookCataLoging,
                                    heading.getText().toString(),
                                    author.getText().toString(),
                                    ISBN.getText().toString(),
                                    publishing.getText().toString(),
                                    genre.getText().toString()
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí biên mục sách
                    }


                }
            });
        }else{
            saveDataBookCataloging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // lấy dữ liệu và thêm dữ liệu vào SQL
                    String id = idBookCataloging.getText().toString();
                    String Heading = heading.getText().toString();
                    String Author = author.getText().toString();
                    String isbn = ISBN.getText().toString();
                    String Publishing = publishing.getText().toString();
                    String Genre = genre.getText().toString();
                    if(id.isEmpty() && Heading.isEmpty() && Author.isEmpty() && isbn.isEmpty() && Publishing.isEmpty() && Genre.isEmpty()){
                        Toast.makeText(AddBookCataloging.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            BookCatalogings.insertList(
                                    id,
                                    Heading,
                                    Author,
                                    isbn,
                                    Publishing,
                                    Genre
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí biên mục sách
                    }

                }
            });
        }
    }
    // quay lại màn hình khi click
    private void changePageBack() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPage();
            }
        });
    }

    private void Init() {
        // ánh xạ view
        initData = getIntent().getBooleanExtra("initData",false);
        IdBookCataLoging = getIntent().getStringExtra("IdBookCataloging");
        textHeading = findViewById(R.id.addBookCataloging_textHeading);
        imageButtonBack = findViewById(R.id.addBookCataloging_back);
        saveDataBookCataloging = findViewById(R.id.addBookCataloging_saveData);
        idBookCataloging = findViewById(R.id.addBookCataloging_idBookCataloging);
        heading = findViewById(R.id.addBookCataloging_heading);
        author = findViewById(R.id.addBookCataloging_author);
        ISBN = findViewById(R.id.addBookCataloging_ISBN);
        publishing = findViewById(R.id.addBookCataloging_publishing);
        genre = findViewById(R.id.addBookCataloging_genre);
        // set data init
        if(initData){
            textHeading.setText("Sửa biên mục sách");
            idBookCataloging.setText(IdBookCataLoging);
            idBookCataloging.setEnabled(false);
        }
    }
}