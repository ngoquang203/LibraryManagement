package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.AdapterManagement.AdapterBookCataloging;
import com.example.librarymanagement.AdapterManagement.AdapterBookSummary;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.Datamanagement.BookSummarys;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookCatalogingManagement extends AppCompatActivity {
    // tạo các biến dữ liệu
    private ListView listView;
    private ArrayList<BookCatalogings> arrayList;
    private TextView noData;
    private Button buttonAddCataloging;
    private ImageButton backPage;
    private AdapterBookCataloging adapterBookCataloging;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_cataloging_management);
        Init(); // hàm khởi tạo
        changePageAddBookCataloging(); // hàm chuyển màn hình qua thêm biên mục
        setNoData();
        setClickSearchView();
    }

    // hàm chuyển màn hình qua thêm biên mục
    private void changePageAddBookCataloging() {
        buttonAddCataloging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookCatalogingManagement.this,AddBookCataloging.class);
                startActivity(intent);
            }
        });
        // set sự kiện cho nút back
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookCatalogingManagement.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // hàm set hiển thị khi size của listview = 0
    private void setNoData() {
        if(arrayList.size() == 0){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }

    // hàm xử lí khi click tìm kiếm
    private void setClickSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    // hàm để tìm các biên mục có cùng kí tự với giá trị tìm kiếm
    private void filterList(String newText) {
        ArrayList<BookCatalogings> catalogingsArrayList = new ArrayList<>();
        for(BookCatalogings bookCatalogings : arrayList){
            if(bookCatalogings.getIdBookCataloging().toString().toLowerCase().contains(newText.toString().toLowerCase())){
                catalogingsArrayList.add(bookCatalogings);
            }
        }
        // xử lí sự kiện khi hiển thị danh sách tìm kiếm
        if(catalogingsArrayList.isEmpty()){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            adapterBookCataloging.setFilterList(catalogingsArrayList);
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }


    private void Init() {
        // ánh xạ view
        listView = findViewById(R.id.bookCatalogingManagement_listView);
        noData = findViewById(R.id.bookCatalogingManagement_noData);
        buttonAddCataloging = findViewById(R.id.bookCatalogingManagement_buttonAddCataloging);
        backPage = findViewById(R.id.bookCatalogingManagement_back);
        try {
            arrayList = BookCatalogings.getuserlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // set adapter cho listview
        adapterBookCataloging = new AdapterBookCataloging(this,arrayList);
        listView.setAdapter(adapterBookCataloging);
        searchView = findViewById(R.id.bookCatalogingManagement_searchView);
        searchView.clearFocus();
    }
}