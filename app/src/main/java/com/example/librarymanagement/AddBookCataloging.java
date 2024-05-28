package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class AddBookCataloging extends AppCompatActivity {
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
        Init();
        changePageBack();
        saveDataBookCataloging();
    }
    private void changeToPage(){
        Intent intent = new Intent(AddBookCataloging.this,BookCatalogingManagement.class);
        startActivity(intent);
    }

    private void saveDataBookCataloging() {
        if(initData){
            saveDataBookCataloging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                    changeToPage();
                }
            });
        }else{
            saveDataBookCataloging.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        BookCatalogings.insertList(
                                idBookCataloging.getText().toString(),
                                heading.getText().toString(),
                                author.getText().toString(),
                                ISBN.getText().toString(),
                                publishing.getText().toString(),
                                genre.getText().toString()
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    changeToPage();
                }
            });
        }
    }

    private void changePageBack() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPage();
            }
        });
    }

    private void Init() {
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
        if(initData){
            textHeading.setText("Sửa biên mục sách");
            idBookCataloging.setText(IdBookCataLoging);
            idBookCataloging.setEnabled(false);
        }
    }
}