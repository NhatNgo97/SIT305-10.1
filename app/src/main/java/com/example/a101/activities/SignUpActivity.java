package com.example.a101.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.a101.R;
import com.example.a101.db.DatabaseHelper;
import com.example.a101.model.User;

import java.io.FileNotFoundException;

public class SignUpActivity extends AppCompatActivity {
    ImageButton addImageButton;
    DatabaseHelper db;
    Intent intent;
    EditText fullnameInput,usernameInput, passwordInput, confirmPasswordInput, phonenumberInput;
    String fullname, username ,password, cPassword, phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addImageButton = findViewById(R.id.addImageButton);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        });
        db = new DatabaseHelper(this);
        Button createAccountButton = findViewById(R.id.createAccountButton);
        fullnameInput = findViewById(R.id.fullNameTextEdit);
        usernameInput = findViewById(R.id.signUpUserNameTextEdit);
        passwordInput = findViewById(R.id.signUpPasswordTextEdit);
        confirmPasswordInput = findViewById(R.id.signUpCpasswordTextEdit);
        phonenumberInput = findViewById(R.id.signUpPhoneNumberTextEdit);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname = fullnameInput.getText().toString();
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                cPassword = confirmPasswordInput.getText().toString();
                phonenumber = phonenumberInput.getText().toString();
                if (password.equals(cPassword))
                {
                    User user = new User(fullname,username, password,phonenumber);
                    long result = db.insertUser(user);
                    if (result > 0)
                    {
                        Toast.makeText(SignUpActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                addImageButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Toast.makeText(SignUpActivity.this, "File not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(SignUpActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}