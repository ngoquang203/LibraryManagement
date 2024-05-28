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

    // khởi tạo các biến
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
    private boolean clickGetImage = false;
    private TextInputEditText idBooks,numberPage,price,numberBook,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Init(); // hàm ánh xạ các biến
        changePageBack(); // hàm quay lại màn hình chính
        saveDataBooks(); // hàm lưu sách
        setLauncher(); // hàm set launcher
        setOnClickImage(); // hàm set click image
    }



    private void setOnClickImage() {
        // sự kiện khi click button image
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickGetImage = true;
                PickImageFromGallery(); // hàm chuyển sang màn hình chọn ảnh
            }
        });
    }



    private void setLauncher(){
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        // tạo intent
                        Intent data = result.getData();
                        if(data != null && data.getData() != null){
                            // lấy địa chỉ của ảnh
                            ImageUri = data.getData();
                            try {
                                // set giá trị vào bitmap
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
                                    bitmap, 300, 500, false); // réize lại ảnh
                            bitmap = resizedBitmap; // set lại bitmap
                            imageView.setImageBitmap(resizedBitmap); // hiển thị ảnh lên màn hình
                        }
                    }
                }
        );
    }
    private void PickImageFromGallery(){
        // chuyển sang màn hình chọn ảnh
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    private void changeToPage(){
        // quay lại màn hình quản lí sách
        Intent intent = new Intent(AddBook.this,BookManagement.class);
        startActivity(intent);

    }


    private void saveDataBooks() {
        // update lại giá trị của sách
        if(initData){
            saveDataBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // lấy ra IdBookCataloging
                    IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();
                    // lấy ra IdBookSummary
                    IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();

                    // lấy dữ liệu từ ô nhập và update dữ liệu vào SQL
                    String NumberPage = numberPage.getText().toString();
                    String Price = price.getText().toString();
                    String NumberBook = numberBook.getText().toString();
                    String Status = status.getText().toString();
                    if(NumberPage.isEmpty() && Price.isEmpty() && NumberBook.isEmpty() && Status.isEmpty()){
                        Toast.makeText(AddBook.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else if(clickGetImage == false){
                        Toast.makeText(AddBook.this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            // chuyển ảnh dạng bitmap sang byte[]
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                            byte[] coverImage = byteArrayOutputStream.toByteArray();
                            // truyền giá trị vào hàm update
                            Books.updateList(
                                    IdBooks,
                                    coverImage,
                                    IdBookCataloging,
                                    IdBookSummary,
                                    NumberPage,
                                    Price,
                                    NumberBook,
                                    Status
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí sách
                    }

                }
            });
        }else{ // thêm sách mới
            saveDataBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // lấy ra IdBookCataloging từ spinner
                    IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();

                    // Lấy ra IdBookSummary từ spinner
                    IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();

                    // lấy dữ liệu và thêm dữ liệu vào SQL
                    String id = idBooks.getText().toString();
                    String NumberPage = numberPage.getText().toString();
                    String Price = price.getText().toString();
                    String NumberBook = numberBook.getText().toString();
                    String Status = status.getText().toString();
                    if(id.isEmpty() && NumberPage.isEmpty() && Price.isEmpty() && NumberBook.isEmpty() && Status.isEmpty()){
                        Toast.makeText(AddBook.this, "Bạn chưa nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    }else if(clickGetImage == false){
                        Toast.makeText(AddBook.this, "Bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            // chuyển ảnh dạng bitmap sang byte[]
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
                            byte[] coverImage = byteArrayOutputStream.toByteArray();
                            // truyền dữ liệu để thêm sách
                            Books.insertList(
                                    id,
                                    coverImage,
                                    IdBookCataloging,
                                    IdBookSummary,
                                    NumberPage,
                                    Price,
                                    NumberBook,
                                    Status
                            );
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        changeToPage(); // chuyển màn hình quản lí sách
                    }
                }
            });
        }
    }

    private void changePageBack() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPage(); // quay lại trang quản lí sách
            }
        });
    }

    private void Init() {
        // set các giá trị
        initData = getIntent().getBooleanExtra("initData",false); // dùng để chỉnh sửa giao diện sửa hoặc thêm
        IdBooks = getIntent().getStringExtra("idBooks"); // lấy Idbooks

        try {
            catalogingsArrayList = BookCatalogings.getuserlist();
            summarysArrayList = BookSummarys.getuserlist();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // khởi tạo thêm nút add
        catalogingsArrayList.add(new BookCatalogings("Thêm biên mục"));
        summarysArrayList.add(new BookSummarys("Thêm tóm tắt"));

        // ánh xạ spinner
        spinnerCataloging = findViewById(R.id.addBook_spinnerCataloging);
        spinnerSummary = findViewById(R.id.addBook_spinnerSummary);

        // tạo adapter
        adapterSpinnerCataloging = new AdapterSpinnerCataloging(this,R.layout.item_selected,catalogingsArrayList);
        adapterSpinnerSummary = new AdapterSpinnerSummary(this,R.layout.item_selected,summarysArrayList);

        // set adapter cho spinner
        spinnerCataloging.setAdapter(adapterSpinnerCataloging);
        spinnerSummary.setAdapter(adapterSpinnerSummary);

        // set giá trị cho spinner
        IdBookCataloging =catalogingsArrayList.get(spinnerCataloging.getSelectedItemPosition()).getIdBookCataloging();
        IdBookSummary = summarysArrayList.get(spinnerSummary.getSelectedItemPosition()).getIdBookSummary();

        // ánh xạ view
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
        // set giá trị
        if(initData){
            textHeading.setText("Sửa sách");
            idBooks.setText(IdBooks.toString());
            idBooks.setEnabled(false);
        }
    }
}