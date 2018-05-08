package com.example.sadhanaravoori.firebasevconnect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MailSender extends Activity implements Runnable{

    /** Called when the activity is first created. */
    DeleteThisBecause obj=new DeleteThisBecause();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_sender);


        final Button send = (Button) this.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    //obj.func();
                    GMailSender sender = new GMailSender("sadhana.ravoori@gmail.com", "$adhan@1997");
                    Log.e("SendMail","lol1");
                    sender.sendMail("This is Subject", "This is Body", "sadhana.ravoori@gmail.com", "sadhanarv97@gmail.com");
                    Log.e("SendMail","lol");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

            }
        });
    }

    @Override
    public void run() {
        Log.e("details","yes");

    }
}