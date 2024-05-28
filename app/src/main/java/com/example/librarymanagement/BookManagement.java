package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.AdapterManagement.AdapterBook;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.Datamanagement.Books;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookManagement extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Books> arrayList;
    private TextView noData;
    private Button buttonAddBook;
    private ImageButton backPage;
    private ArrayList<BookCatalogings> catalogingsArrayList;
    private ArrayList<BookSummarys> summarysArrayList;
    private AdapterBook adapterBook;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_management);
        Init();
        changePageAddBookCataloging();
        setNoData();
        setonClickItemListView();
        setClickSearchView();
    }

    private void setonClickItemListView() {

    }

    private void changePageAddBookCataloging() {
        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(catalogingsArrayList.size() > 0 && summarysArrayList.size() > 0){
                    Intent intent = new Intent(BookManagement.this,AddBook.class);
                    startActivity(intent);
                }else if(catalogingsArrayList.size() == 0){
                    Toast.makeText(BookManagement.this, "Bạn chưa có biên mục sách nào", Toast.LENGTH_SHORT).show();
                }
                else if(summarysArrayList.size() == 0){
                    Toast.makeText(BookManagement.this, "Bạn chưa có tóm tắt sách nào", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookManagement.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setNoData() {
        if(arrayList.size() == 0){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }

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
    private void filterList(String newText) {
        ArrayList<Books> booksArrayList = new ArrayList<>();
        for(Books books : arrayList){
            if(books.getIdBooks().toString().toLowerCase().contains(newText.toString().toLowerCase())){
                booksArrayList.add(books);
            }
        }
        if(booksArrayList.isEmpty()){
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }else{
            adapterBook.setFilterList(booksArrayList);
            listView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
    }
    private void Init() {
        listView = findViewById(R.id.bookManagement_listView);
        noData = findViewById(R.id.bookManagement_noData);
        buttonAddBook = findViewById(R.id.bookManagement_buttonAddBook);
        backPage = findViewById(R.id.bookManagement_back);
        try {
            catalogingsArrayList = BookCatalogings.getuserlist();
            summarysArrayList = BookSummarys.getuserlist();
            arrayList = Books.getuserlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        adapterBook = new AdapterBook(this,arrayList);
        listView.setAdapter(adapterBook);
        searchView = findViewById(R.id.bookManagement_searchView);
        searchView.clearFocus();
    }
}