package com.e.dpkartavya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email,login_password;
    private Button login_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_click = findViewById(R.id.login_click);
        /* To make Box At time of Typing */
        login_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_email.setBackgroundResource(R.drawable.edit_text_design_off);
                login_password.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });

        login_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_password.setBackgroundResource(R.drawable.edit_text_design_off);
                login_email.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });
        login_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,DashActivity.class));
            }
        });
    }
}