package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
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
    Button back1;
    StringBuffer buf;
    ArrayList<String> bid,bauthor,btitle,bdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_);
        openHelper = new DatabaseHelper(this);
        dbh = new DatabaseHelper(getApplicationContext());
        back1 = (Button)findViewById(R.id.back);

        bid = new ArrayList<>();
        bauthor = new ArrayList<>();
        btitle = new ArrayList<>();
        bdate = new ArrayList<>();
        buf = new StringBuffer();

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Noti_Activity.this,homeactivity.class);
                startActivity(home);
            }
        });
        try {
            cursor = dbh.readData("sowmya");

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            }
            else {
                buf.append("ID\tTitle\tAuthor\tDueDate\n");
                while (cursor.moveToNext()) {
                    buf.append(cursor.getString(0) + "\t");
                    buf.append(cursor.getString(1) + "\t");
                    buf.append(cursor.getString(2) + "\t");
                    buf.append(cursor.getString(3) + "\t");
                    buf.append("\n");
                }
                Builder builder = new Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Summary");
                builder.setMessage(buf);
                builder.show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}