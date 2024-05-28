package com.example.librarymanagement.AdapterManagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.librarymanagement.AddBook;
import com.example.librarymanagement.AddBookSummary;
import com.example.librarymanagement.BookManagement;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.Datamanagement.Books;
import com.example.librarymanagement.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdapterBook extends BaseAdapter {
    Context context;
    ArrayList<Books> arrayList;
    LayoutInflater layoutInflater;
    public AdapterBook(Context context,ArrayList<Books> arrayList){ // hàm constructor
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_book_summary,null);
        TextView textView = convertView.findViewById(R.id.itemBookSummary_textView); // ánh xạ text view trong layout
        ImageButton imageButton = convertView.findViewById(R.id.itemBookSummary_imageButton); // ánh xạ imagebutton trong layout

        Books books = arrayList.get(position); // tạo đối tượng book

        textView.setText(books.getIdBooks()); // set text cho textview
        textView.setOnClickListener(new View.OnClickListener() { // set sự kiện hiển thị chi tiết book khi click vào item
            @Override
            public void onClick(View v) {
                BookSummarys bookSummarys = new BookSummarys(); // tạo đối tượng booksummary
                BookCatalogings bookCatalogings = new BookCatalogings(); // tạo đối tượng bookcataloging
                try {
                    bookSummarys = BookSummarys.getuserlist(books.getIdBookSummary()); // lấy dữ liệu từ SQL để gán vào đối tượng
                    bookCatalogings = BookCatalogings.getuserlist(books.getIdBookCataloging()); // lấy dữ liệu từ SQL để gán vào đối tượng
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Dialog dialog = new Dialog(context); // tạo Dialog để hiển thị
                dialog.setContentView(R.layout.dialog_supbook);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                Bitmap bitmap = BitmapFactory.decodeByteArray(books.getCoverImage(),0,books.getCoverImage().length); // tạo bitmap để lấy ảnh


                // ánh xạ các thành phần trong dialog
                ImageButton back = dialog.findViewById(R.id.dialogSupBook_back);

                TextView IdBookCataloging = dialog.findViewById(R.id.dialogSupBook_idBookCataloging);
                TextView Heading = dialog.findViewById(R.id.dialogSupBook_heading);
                TextView Author = dialog.findViewById(R.id.dialogSupBook_author);
                TextView ISBN = dialog.findViewById(R.id.dialogSupBook_ISBN);
                TextView Publishing = dialog.findViewById(R.id.dialogSupBook_publishing);
                TextView Genre = dialog.findViewById(R.id.dialogSupBook_genre);

                TextView IdBookSummary = dialog.findViewById(R.id.dialogSupBook_idBookSummary);
                TextView MainContent = dialog.findViewById(R.id.dialogSupBook_mainContent);
                TextView Meaning = dialog.findViewById(R.id.dialogSupBook_meaning);
                TextView CommunicationGoals = dialog.findViewById(R.id.dialogSupBook_communicationGoals);

                TextView IdBooks = dialog.findViewById(R.id.dialogSupBook_idBooks);
                ImageView imageView = dialog.findViewById(R.id.dialogSupBook_imageView);
                TextView NumberPage = dialog.findViewById(R.id.dialogSupBook_numberPage);
                TextView Price = dialog.findViewById(R.id.dialogSupBook_price);
                TextView NumberBook = dialog.findViewById(R.id.dialogSupBook_numberBook);
                TextView Status = dialog.findViewById(R.id.dialogSupBook_status);

                // set các giá trị
                IdBookCataloging.setText(bookCatalogings.getIdBookCataloging());
                Heading.setText(bookCatalogings.getHeading());
                Author.setText(bookCatalogings.getAuthor());
                ISBN.setText(bookCatalogings.getISBN());
                Publishing.setText(bookCatalogings.getPublishing());
                Genre.setText(bookCatalogings.getGenre());

                IdBookSummary.setText(bookSummarys.getIdBookSummary());
                MainContent.setText(bookSummarys.getMainContent());
                Meaning.setText(bookSummarys.getMeaning());
                CommunicationGoals.setText(bookSummarys.getCommunicationGoals());

                IdBooks.setText(books.getIdBooks());
                imageView.setImageBitmap(bitmap);
                NumberPage.setText(String.valueOf(books.getNumberPage()));
                Price.setText(String.valueOf(books.getPrice()));
                NumberBook.setText(String.valueOf(books.getNumberBook()));
                Status.setText(books.getStatus());

                // đóng dialog khi nhấn vào hình ảnh back
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show(); // trình chiếu dialog

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() { // sự kiện khi click vào biểu tượng tùy chọn
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imageButton); // tạo popupMenu
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.menuEdit){  // sự kiện khi click vào nút sửa
                            Intent intent = new Intent(context, AddBook.class); // tạo intent để vào màn hình AddBook
                            // gửi giá trị đến màn hình AddBook qua intent
                            intent.putExtra("initData",true);
                            intent.putExtra("idBooks",books.getIdBooks());
                            context.startActivity(intent);

                        }else if(item.getItemId() == R.id.menuDelete){ // sự kiện khi click vào nút xóa
                            try {
                                Books.deleteList(books.getIdBooks()); // xóa sách thông qua IdBooks
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            arrayList.remove(position); // xóa sách này ra khỏi danh sách
                            notifyDataSetChanged(); // cập nhật lại dữ liệu hiển thị
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView;
    }
    public void setFilterList(ArrayList<Books> filterList){
        this.arrayList = filterList; // gán giá trị mới cho danh sách
        notifyDataSetChanged(); // cập nhật lại dữ liệu hiển thị
    }
}
