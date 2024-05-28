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

import com.example.librarymanagement.AddBookSummary;
import com.example.librarymanagement.Datamanagement.BookSummarys;
import com.example.librarymanagement.Datamanagement.Books;
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
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_supsumary);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                ImageButton back = dialog.findViewById(R.id.dialogSupSummary_back);
                TextView IdBookSummary = dialog.findViewById(R.id.dialogSupSummary_idBookSummary);
                TextView MainContent = dialog.findViewById(R.id.dialogSupSummary_mainContent);
                TextView Meaning = dialog.findViewById(R.id.dialogSupSummary_meaning);
                TextView CommunicationGoals = dialog.findViewById(R.id.dialogSupSummary_communicationGoals);

                IdBookSummary.setText(bookSummarys.getIdBookSummary());
                MainContent.setText(bookSummarys.getMainContent());
                Meaning.setText(bookSummarys.getMeaning());
                CommunicationGoals.setText(bookSummarys.getCommunicationGoals());

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
                PopupMenu popupMenu = new PopupMenu(context, imageButton);
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuEdit){
                            Intent intent = new Intent(context, AddBookSummary.class);
                            intent.putExtra("initData",true);
                            intent.putExtra("IdBookSummary",bookSummarys.getIdBookSummary());
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
    public void setFilterList(ArrayList<BookSummarys> filterList){
        this.arrayList = filterList;
        notifyDataSetChanged();
    }
}
