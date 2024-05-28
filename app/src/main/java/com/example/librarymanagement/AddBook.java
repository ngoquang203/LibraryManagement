package com.example.librarymanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarymanagement.AdapterManagement.AdapterSpinnerCataloging;
import com.example.librarymanagement.AdapterManagement.AdapterSpinnerSummary;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.Datamanagement.Books;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddBook extends AppCompatActivity {
    private boolean initData;
    private String IdBooks;
    private Spinner spinnerCataloging;
    private Spinner spinnerSummary;
    private TextView textHeading;
    private ImageButton imageButtonBack;
    private Button saveDataBook,selectedImage;
    private ImageView imageView;
    private Uri ImageUri;
    private Bitmap bitmap;
    private ActivityResultLauncher<Intent> launcher;
    private AdapterSpinnerCataloging adapterSpinnerCataloging;
    private AdapterSpinnerSummary adapterSpinnerSummary;
    private ArrayList<BookCatalogings> catalogingsArrayList;
    private ArrayList<BookSummarys> summarysArrayList;
    private String IdBookCataloging,IdBookSummary;

    private String PhotoUrl;


    private TextInputEditText idBooks,numberPage,price,numberBook,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Init();
        changePageBack();
        saveDataBooks();
        setLauncher();
        setOnClickImage();
    }



    private void setOnClickImage() {
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageFromGallery();
            }
        });
    }



    private void setLauncher(){
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data != null && data.getData() != null){
                            ImageUri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(
                                        getContentResolver(),
                                        ImageUri
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if(ImageUri != null){
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                    bitmap, 300, 500, false);
                            bitmap = resizedBitmap;
                            imageView.setImageBitmap(resizedBitmap);
                        }
                    }
                }
        );
    }
    private void PickImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    private void changeToPage(){
        Intent intent = new Intent(AddBook.this,BookManagement.class);
        startActivity(intent);

    }


    private void saveDataBooks() {
        if(initData){
            saveDataBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                    byte[] coverImage = byteArrayOutputStream.toByteArray();
                    IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();
                    IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();
                    try {
                        Books.updateList(
                                IdBooks,
                                coverImage,
                                IdBookCataloging,
                                IdBookSummary,
                                Integer.valueOf(numberPage.getText().toString()),
                                Long.valueOf(price.getText().toString()),
                                Integer.valueOf(numberBook.getText().toString()),
                                status.getText().toString()
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    changeToPage();
                }
            });
        }else{
            saveDataBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                    byte[] coverImage = byteArrayOutputStream.toByteArray();
                    IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();
                    IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();
                    try {
                        Books.insertList(
                                idBooks.getText().toString(),
                                coverImage,
                                IdBookCataloging,
                                IdBookSummary,
                                Integer.valueOf(numberPage.getText().toString()),
                                Long.valueOf(price.getText().toString()),
                                Integer.valueOf(numberBook.getText().toString()),
                                status.getText().toString()
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
        IdBooks = getIntent().getStringExtra("idBooks");

        try {
            catalogingsArrayList = BookCatalogings.getuserlist();
            summarysArrayList = BookSummarys.getuserlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catalogingsArrayList.add(new BookCatalogings("Thêm biên mục"));
        summarysArrayList.add(new BookSummarys("Thêm tóm tắt"));

        spinnerCataloging = findViewById(R.id.addBook_spinnerCataloging);
        spinnerSummary = findViewById(R.id.addBook_spinnerSummary);

        adapterSpinnerCataloging = new AdapterSpinnerCataloging(this,R.layout.item_selected,catalogingsArrayList);
        adapterSpinnerSummary = new AdapterSpinnerSummary(this,R.layout.item_selected,summarysArrayList);

        spinnerCataloging.setAdapter(adapterSpinnerCataloging);
        spinnerSummary.setAdapter(adapterSpinnerSummary);

        IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();
        IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();

        imageView = findViewById(R.id.addBook_saveImageFireBase);
        textHeading = findViewById(R.id.addBook_textHeading);
        imageButtonBack = findViewById(R.id.addBook_back);
        saveDataBook = findViewById(R.id.addBook_saveData);
        idBooks = findViewById(R.id.addBook_idBooks);
        selectedImage = findViewById(R.id.addBook_selectedImage);
        numberPage = findViewById(R.id.addBook_numberPage);
        price = findViewById(R.id.addBook_price);
        numberBook = findViewById(R.id.addBook_numberBook);
        status = findViewById(R.id.addBook_status);
        if(initData){
            textHeading.setText("Sửa sách");
            idBooks.setText(IdBooks.toString());
            idBooks.setEnabled(false);
        }
    }
}