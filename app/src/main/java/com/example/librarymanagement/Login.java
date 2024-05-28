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
    // khởi tạo các biến
    private TextInputEditText User,Passwords;
    private Button changePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init(); //hàm khởi tạo giá trị
        onClickChangePage(); // hàm xử lí login
    }

    private void onClickChangePage() {
        changePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy ra dữ liệu ở các ô nhập
                String user = User.getText().toString();
                String passwords = Passwords.getText().toString();
                Logins logins = new Logins();
                try {
                    logins = Logins.getuserlist(user,passwords); // lâý dữ liệu ở SQL
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // so sánh giữa dữ liệu ô nhập và dữ liệu SQL
                if(logins.getUser().equals(user) && logins.getPass().equals(passwords)){
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    // Thông báo tài khoản mật khẩu không chính xác
                    Toast.makeText(Login.this, "Tài khoản mật khẩu của bạn không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Init() {
        // ánh xạ view
        User = findViewById(R.id.login_user);
        Passwords = findViewById(R.id.login_password);
        changePage = findViewById(R.id.login_button);
    }
}