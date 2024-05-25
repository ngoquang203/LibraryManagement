package com.example.librarymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.librarymanagement.Datamanagement.Logins;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class Login extends AppCompatActivity {

    private TextInputEditText User,Passwords;
    private Button changePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        onClickChangePage();
    }

    private void onClickChangePage() {
        changePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = User.getText().toString();
                String passwords = Passwords.getText().toString();
                Logins logins = new Logins();
                try {
                    logins = Logins.getuserlist(user,passwords);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(logins.getUser().equals(user) && logins.getPass().equals(passwords)){
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Tài khoản mật khẩu của bạn không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Init() {
        User = findViewById(R.id.login_user);
        Passwords = findViewById(R.id.login_password);
        changePage = findViewById(R.id.login_button);
    }
}