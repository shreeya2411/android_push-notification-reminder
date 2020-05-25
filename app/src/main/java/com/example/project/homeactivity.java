package com.example.project;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class homeactivity extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    DatabaseHelper dbh = new DatabaseHelper(this);
    EditText title,author;
    Button submit,summary;


    //Scheduling the notification for delay amount
    public void scheduleNotification(Context context, long delay, int notificationId) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"default")
                .setContentTitle("Due Date Reminder")
                .setContentText("Your book(s) is due today")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Notification notification = builder.build();

        Intent intent = new Intent(context, Noti_Activity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        openHelper = new DatabaseHelper(this);
        submit = (Button)findViewById(R.id.button_checkout);
        summary = (Button)findViewById(R.id.summary);
        title = (EditText) findViewById(R.id.edittext_title);
        author = (EditText) findViewById(R.id.edittext_author);

        //Check out function
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbh.getWritableDatabase();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 15);
                String secs = dateFormat.format(c.getTime());
                long sc =  c.getTimeInMillis();
                //sc = sc; //for verfiying purpose
                String b_title = title.getText().toString().trim();
                String b_author = author.getText().toString().trim();
                long res = dbh.addBook(db,b_title,b_author,secs);
                scheduleNotification(getApplicationContext(),5000,1);
                if(res != -1) {
                    Toast.makeText(getApplicationContext(), "Checked out successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Moving to Noti_activity file which displays the summary from books table
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeactivity.this, Noti_Activity.class);
                startActivity(intent);
            }
        });

    }

}
