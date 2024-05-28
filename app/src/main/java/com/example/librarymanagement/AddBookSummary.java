package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class AddBookSummary extends AppCompatActivity {
    // tạo các biến
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
        Init(); // khởi tạo giá trị
        changePageBack(); // chuyển màn hình
        saveDataBookSumary(); // lưu dữ liệu vào sql
    }
    // hàm chuyển màn hình
    private void changeToPage(){
        Intent intent = new Intent(AddBookSummary.this,BookSummaryManagement.class);
        startActivity(intent);
    }
    // hàm lưu dữ liệu vào sql
    private void saveDataBookSumary() {
        if(initData){
            saveDataBookSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String MainContent = mainContent.getText().toString();
                    String Meaning = meaning.getText().toString();
                    String CommunicationGoals = communicationGoals.getText().toString();
                    // lấy dữ liệu từ ô nhập và update dữ liệu vào SQL
                    if(MainContent.isEmpty() && Meaning.isEmpty() && CommunicationGoals.isEmpty()){
                        Toast.makeText(AddBookSummary.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            // hàm update dữ liệu
                            BookSummarys.updateList(
                                    IdBookSummary,
                                    mainContent.getText().toString(),
                                    meaning.getText().toString(),
                                    communicationGoals.getText().toString()
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí tóm tắt sách
                    }

                }
            });
        }else{
            saveDataBookSummary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // lấy dữ liệu và thêm dữ liệu vào SQL
                    String Id = idBookSummary.getText().toString();
                    String MainContent = mainContent.getText().toString();
                    String Meaning = meaning.getText().toString();
                    String CommunicationGoals = communicationGoals.getText().toString();
                    if(Id.isEmpty() && MainContent.isEmpty() && Meaning.isEmpty() && CommunicationGoals.isEmpty()){
                        Toast.makeText(AddBookSummary.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            // hàm thêm dữ liệu
                            BookSummarys.insertList(
                                    Id,
                                    MainContent,
                                    Meaning,
                                    CommunicationGoals
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí tóm tắt sách
                    }


                }
            });
        }
    }
    // hàm set sự kiện cho nút back
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
        IdBookSummary = getIntent().getStringExtra("IdBookSummary");
        textHeading = findViewById(R.id.addBookSummary_textHeading);
        imageButtonBack = findViewById(R.id.addBookSummary_back);
        saveDataBookSummary = findViewById(R.id.addBookSummary_saveData);
        idBookSummary = findViewById(R.id.addBookSummary_idBookSummary);
        mainContent = findViewById(R.id.addBookSummary_mainContent);
        meaning = findViewById(R.id.addBookSummary_meaning);
        communicationGoals = findViewById(R.id.addBookSummary_communicationGoals);
        // set data init
        if(initData){
            textHeading.setText("Sửa tóm tắt sách");
            idBookSummary.setText(IdBookSummary);
            idBookSummary.setEnabled(false);
        }
    }
}