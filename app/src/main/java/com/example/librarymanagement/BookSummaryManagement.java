package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.AdapterManagement.AdapterBookSummary;
import com.example.librarymanagement.Datamanagement.BookSummarys;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookSummaryManagement extends AppCompatActivity {
    // hàm tạo các biến
    private ListView listView;
    private ArrayList<BookSummarys> arrayList;
    private TextView noData;
    private Button buttonAddSummary;
    private ImageButton backPage;
    private AdapterBookSummary adapterBookSummary;
    private android.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_summary_management);
        Init(); // hàm khởi tạo
        setNoData(); // hàm xử lí hiển thị khi listview rỗng
        changePageAddBookSummary(); // hàm chuyển màn hình sang thêm tóm tắt sách
        setClickSearchView(); // hàm cài đặt tìm kiếm
    }


    // hàm chuyển màn hình sang thêm tóm tắt sách
    private void changePageAddBookSummary() {
        buttonAddSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookSummaryManagement.this, AddBookSummary.class);
                startActivity(intent);
            }
        });
        // set sự kiện cho nút back
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookSummaryManagement.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // hàm xử lí hiển thị khi listview rỗng
    private void setNoData() {
        if(arrayList.size() == 0){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }

    // hàm cài đặt tìm kiếm
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

    // hàm tìm sách có cùng kí tự với tìm kiếm
    private void filterList(String newText) {
        ArrayList<BookSummarys> summarysArrayList = new ArrayList<>();
        for(BookSummarys bookSummarys : arrayList){
            if(bookSummarys.getIdBookSummary().toString().toLowerCase().contains(newText.toString().toLowerCase())){
                summarysArrayList.add(bookSummarys);
            }
        }
        // hàm set hiển thị lại danh sách
        if(summarysArrayList.isEmpty()){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            adapterBookSummary.setFilterList(summarysArrayList);
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }

    private void Init() {
        // ánh xạ view
        listView = findViewById(R.id.bookSummaryManagement_listView);
        noData = findViewById(R.id.bookSummaryManagement_noData);
        buttonAddSummary = findViewById(R.id.bookSummaryManagement_buttonAddSummary);
        backPage = findViewById(R.id.bookSummaryManagement_back);
        try {
            arrayList = BookSummarys.getuserlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        adapterBookSummary = new AdapterBookSummary(this,arrayList);
        listView.setAdapter(adapterBookSummary);
        searchView = findViewById(R.id.bookSummaryManagement_searchView);
        searchView.clearFocus();
    }
}