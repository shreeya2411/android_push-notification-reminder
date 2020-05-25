package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    EditText ET_username, ET_password,ET_conf_password;
    Button B_signup;
    TextView TV_signin;
    SQLiteOpenHelper db;
    DatabaseHelper dbh;
    SQLiteDatabase datab;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ET_username = (EditText)findViewById(R.id.edittext_username);
        ET_password = (EditText)findViewById(R.id.edittext_password);
        ET_conf_password = (EditText)findViewById(R.id.edittext_conf_password);
        B_signup = (Button)findViewById(R.id.button_signup);
        TV_signin = (TextView)findViewById(R.id.textview_signin);
        TV_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signinintent = new Intent(signup.this, MainActivity.class);
                startActivity(signinintent);
            }
        });
        B_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String name = ET_username.getText().toString().trim();
                String pass = ET_password.getText().toString().trim();
                String cpass = ET_conf_password.getText().toString().trim();
                if(cpass.equals(pass))
                {
                    dbh = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase datab = dbh.getWritableDatabase();
                    dbh.addusers(datab,name,pass);
                    Toast.makeText(getApplicationContext(), "REGISTERED", Toast.LENGTH_SHORT).show();
                    Intent newaccintent = new Intent(signup.this, MainActivity.class);
                    startActivity(newaccintent);
                }
                else
                    Toast.makeText(getApplicationContext(), "password mismatch", Toast.LENGTH_SHORT).show();
            }

        });


    }

}
