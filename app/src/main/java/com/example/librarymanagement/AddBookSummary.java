package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddBookSummary extends AppCompatActivity {
    private boolean initData;
    private TextView textHeading;
    private ImageButton imageButtonBack;
    private Button saveDataBookSummary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_summary);
        Init();
        changePageBack();
        saveDataBookSumary();
    }
    private void changeToPage(){
        Intent intent = new Intent(AddBookSummary.this,BookSummaryManagement.class);
        startActivity(intent);
    }
    private void saveDataBookSumary() {
        if(initData){
            saveDataBookSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeToPage();
                }
            });
        }else{
            saveDataBookSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        textHeading = findViewById(R.id.addBookSummary_textHeading);
        imageButtonBack = findViewById(R.id.addBookSummary_back);
        saveDataBookSummary = findViewById(R.id.addBookSummary_saveData);
        if(initData){
            textHeading.setText("Sửa tóm tắt sách");
        }
    }
}