package com.example.librarymanagement.AdapterManagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.librarymanagement.AddBookSummary;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.R;

import java.sql.SQLException;
import java.util.ArrayList;


public class AdapterBookSummary extends BaseAdapter {
    Context context;
    ArrayList<BookSummarys> arrayList;
    LayoutInflater layoutInflater;
    public AdapterBookSummary(Context context,ArrayList<BookSummarys> arrayList){
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
        TextView textView = convertView.findViewById(R.id.itemBookSummary_textView);
        ImageButton imageButton = convertView.findViewById(R.id.itemBookSummary_imageButton);

        BookSummarys bookSummarys = arrayList.get(position);

        textView.setText(bookSummarys.getIdBookSummary());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuEdit){
                            Intent intent = new Intent(context, AddBookSummary.class);
                            intent.putExtra("initData",true);
                            context.startActivity(intent);

                        }else if(item.getItemId() == R.id.menuDelete){
                            try {
                                BookSummarys.deleteList(bookSummarys.getIdBookSummary());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            arrayList.remove(position);
                            notifyDataSetChanged();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return convertView;
    }
}
