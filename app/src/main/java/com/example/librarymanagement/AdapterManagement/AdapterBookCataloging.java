package com.example.librarymanagement.AdapterManagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.librarymanagement.AddBookCataloging;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.R;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdapterBookCataloging extends BaseAdapter {
    Context context;
    ArrayList<BookCatalogings> arrayList;
    LayoutInflater layoutInflater;

    public AdapterBookCataloging(Context context,ArrayList<BookCatalogings> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
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
        // ánh xạ
        TextView textView = convertView.findViewById(R.id.itemBookSummary_textView);
        ImageButton imageButton = convertView.findViewById(R.id.itemBookSummary_imageButton);

        BookCatalogings bookCatalogings = arrayList.get(position); // tạo đối tượng
        textView.setText(bookCatalogings.getIdBookCataloging()); // set giá trị cho textview
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog và set layout
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_supcataloging);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                // ánh xạ
                ImageButton back = dialog.findViewById(R.id.dialogSupCataloging_back);
                TextView IdBookCataloging = dialog.findViewById(R.id.dialogSupCataloging_idBookCataloging);
                TextView Heading = dialog.findViewById(R.id.dialogSupCataloging_heading);
                TextView Author = dialog.findViewById(R.id.dialogSupCataloging_author);
                TextView ISBN = dialog.findViewById(R.id.dialogSupCataloging_ISBN);
                TextView Publishing = dialog.findViewById(R.id.dialogSupCataloging_publishing);
                TextView Genre = dialog.findViewById(R.id.dialogSupCataloging_genre);

                // set các giá trị cho các thành phần
                IdBookCataloging.setText(bookCatalogings.getIdBookCataloging());
                Heading.setText(bookCatalogings.getHeading());
                Author.setText(bookCatalogings.getAuthor());
                ISBN.setText(bookCatalogings.getISBN());
                Publishing.setText(bookCatalogings.getPublishing());
                Genre.setText(bookCatalogings.getGenre());

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,imageButton); // tạo popupMenu
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) { // sự kiện khi click vào nút sửa
                        if (item.getItemId() == R.id.menuEdit){
                            Intent intent = new Intent(context, AddBookCataloging.class); // tạo intent để vào màn hình AddBook
                            // gửi giá trị đến màn hình AddBook qua intent
                            intent.putExtra("initData",true);
                            intent.putExtra("IdBookCataloging",bookCatalogings.getIdBookCataloging());
                            context.startActivity(intent);
                        }else if(item.getItemId() == R.id.menuDelete){ // sự kiện khi click vào nút xóa
                            try {
                                BookCatalogings.deleteList(bookCatalogings.getIdBookCataloging()); // xóa biên mục sách này thông qua IdBookCataloging
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
    public void setFilterList(ArrayList<BookCatalogings> filterList){
        this.arrayList = filterList; // gán giá trị mới cho danh sách
        notifyDataSetChanged(); // cập nhật lại dữ liệu hiển thị
    }
}
