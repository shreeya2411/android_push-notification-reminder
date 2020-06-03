package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Noti_Activity extends AppCompatActivity {
    DatabaseHelper dbh;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;
    Button back1,summary;
    StringBuffer buf;
    int num =1;
    ArrayList<String> bid,bauthor,btitle,bdate;
    String uName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_);
        openHelper = new DatabaseHelper(this);
        dbh = new DatabaseHelper(getApplicationContext());
        back1 = (Button)findViewById(R.id.back);
        summary = (Button)findViewById(R.id.summary);

        bid = new ArrayList<>();
        bauthor = new ArrayList<>();
        btitle = new ArrayList<>();
        bdate = new ArrayList<>();
        buf = new StringBuffer();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Noti_Activity.this,homeactivity.class);
                startActivity(home);
            }
        });
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            uName = extras.getString("USER_NAME");
            Toast.makeText(getApplicationContext(), uName, Toast.LENGTH_SHORT).show();
        }


        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    cursor = dbh.readData(uName);

                    if (cursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        while (cursor.moveToNext()) {
                            buf.append("S.NO : "+ num + "\n");
                            buf.append("TITLE : "+cursor.getString(1) + "\n");
                            buf.append("AUTHOR : "+cursor.getString(2) + "\n");
                            buf.append("DUE DATE : "+cursor.getString(3) + "\n");
                            //buf.append(cursor.getString(4) + "\n");
                            buf.append("\n");
                            num=num+1;
                        }


                        //Builder builder = new Builder(getApplicationContext());
                        builder.setCancelable(true);
                        builder.setTitle("Summary");
                        builder.setMessage(buf);
                        builder.show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
}