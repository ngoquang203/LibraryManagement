package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button managementBookCataloging,managementBookSummary,managementBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        onClickChangePage();
    }

    private void onClickChangePage() {
        managementBookCataloging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookCatalogingManagement.class);
                startActivity(intent);
            }
        });
        managementBookSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSummaryManagement.class);
                startActivity(intent);
            }
        });
        managementBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookManagement.class);
                startActivity(intent);
            }
        });
    }

    private void Init() {
        managementBookCataloging = findViewById(R.id.main_managementBookCataloging);
        managementBookSummary = findViewById(R.id.main_managementBookSummary);
        managementBooks = findViewById(R.id.main_managementBooks);
    }
}