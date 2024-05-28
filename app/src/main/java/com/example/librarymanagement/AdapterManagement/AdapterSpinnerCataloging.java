package com.example.librarymanagement.AdapterManagement;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.librarymanagement.AddBookCataloging;
import com.example.librarymanagement.Datamanagement.BookCatalogings;
import com.example.librarymanagement.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpinnerCataloging extends ArrayAdapter<BookCatalogings> {
    public AdapterSpinnerCataloging(@NonNull Context context, int resource, @NonNull List<BookCatalogings> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected,parent,false);
        // ánh xạ textview trong layout selected
        TextView title = convertView.findViewById(R.id.itemSelected_text);

        BookCatalogings bookCatalogings = this.getItem(position); // tạo đối tượng và set giá trị cho đối tượng
        if(bookCatalogings != null){
            // trường hợp khi nhấn thêm biên mục
            if(bookCatalogings.getIdBookCataloging().equals("Thêm biên mục")){
                Intent intent = new Intent(getContext(), AddBookCataloging.class); // chuyển màn hình
                getContext().startActivity(intent);
            }else{
                title.setText(bookCatalogings.getIdBookCataloging()); // set giá trị cho textview
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner,parent,false);

        // ánh xạ
        TextView title = convertView.findViewById(R.id.itemSpinner_title);

        BookCatalogings bookCatalogings = this.getItem(position); // tạo đối tượng và set giá trị
        if(bookCatalogings != null){
            title.setText(bookCatalogings.getIdBookCataloging()); // set giá trị cho textview
        }
        return convertView;
    }
}
