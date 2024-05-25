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

import com.example.librarymanagement.AddBookCataloging;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.R;

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
        TextView textView = convertView.findViewById(R.id.itemBookSummary_textView);
        ImageButton imageButton = convertView.findViewById(R.id.itemBookSummary_imageButton);

        BookCatalogings bookCatalogings = arrayList.get(position);
        textView.setText(bookCatalogings.getIdBookCataloging());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuEdit){
                            Intent intent = new Intent(context, AddBookCataloging.class);
                            intent.putExtra("initData",true);
                            context.startActivity(intent);
                        }else if(item.getItemId() == R.id.menuDelete){

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
