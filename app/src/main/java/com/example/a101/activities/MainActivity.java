package com.example.a101.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a101.R;
import com.example.a101.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    EditText userNameTextEdit, passwordTextEdit;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameTextEdit = findViewById(R.id.usernameTextEdit);
        passwordTextEdit = findViewById(R.id.passwordTextEdit);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userNameTextEdit.getText().toString();
                String password = passwordTextEdit.getText().toString();
                boolean user =  db.fetchUser(username,password);
                if (user)
                {
                    Intent intent1 = new Intent(getApplicationContext(), HomeActivity.class);
                    intent1.putExtra("username",username);
                    startActivity(intent1);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Your username or password must be wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}