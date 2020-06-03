package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText ET_username, ET_password;
    Button B_signin;
    TextView TV_signup,check;
    DatabaseHelper dbh;
    SQLiteDatabase datab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ET_username = (EditText)findViewById(R.id.edittext_username);
        ET_password = (EditText)findViewById(R.id.edittext_password);
        B_signin = (Button) findViewById(R.id.button_signin);
        TV_signup = (TextView) findViewById(R.id.textview_signup);




        TV_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent signupintent = new Intent(MainActivity.this ,signup.class );
                startActivity(signupintent);

            }
        });
        B_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = ET_username.getText().toString().trim();
                String pass = ET_password.getText().toString().trim();

                dbh = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase datab = dbh.getReadableDatabase();

                int chck = dbh.validatelogin(datab, uname, pass);
                if (chck == 10)
                    Toast.makeText(getApplicationContext(), "no user", Toast.LENGTH_SHORT).show();
                else {

                    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                    Intent movetohome;

                    movetohome = new Intent(MainActivity.this, homeactivity.class);
                    movetohome.putExtra("USER_NAME", uname);
                    startActivity(movetohome);
                }
            }
        });


    }
}