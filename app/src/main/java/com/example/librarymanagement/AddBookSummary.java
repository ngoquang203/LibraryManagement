package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class AddBookSummary extends AppCompatActivity {
    private boolean initData;
    private String IdBookSummary;
    private TextView textHeading;
    private ImageButton imageButtonBack;
    private Button saveDataBookSummary;
    private TextInputEditText idBookSummary,mainContent,meaning,communicationGoals;
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
                    try {
                        BookSummarys.updateList(
                                IdBookSummary,
                                mainContent.getText().toString(),
                                meaning.getText().toString(),
                                communicationGoals.getText().toString()
                                );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    changeToPage();
                }
            });
        }else{
            saveDataBookSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        BookSummarys.insertList(
                                IdBookSummary,
                                mainContent.getText().toString(),
                                meaning.getText().toString(),
                                communicationGoals.getText().toString()
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
        IdBookSummary = getIntent().getStringExtra("IdBookSummary");
        textHeading = findViewById(R.id.addBookSummary_textHeading);
        imageButtonBack = findViewById(R.id.addBookSummary_back);
        saveDataBookSummary = findViewById(R.id.addBookSummary_saveData);
        idBookSummary = findViewById(R.id.addBookSummary_idBookSummary);
        mainContent = findViewById(R.id.addBookSummary_mainContent);
        meaning = findViewById(R.id.addBookSummary_meaning);
        communicationGoals = findViewById(R.id.addBookSummary_communicationGoals);
        if(initData){
            textHeading.setText("Sửa tóm tắt sách");
            idBookSummary.setText(IdBookSummary);
            idBookSummary.setEnabled(false);
        }
    }
}