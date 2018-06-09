package com.example.sadhanaravoori.firebasevconnect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class SendMailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        final Button send = (Button) this.findViewById(R.id.button1);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String fromEmail="sadhana.ravoori@gmail.com";

                String fromPassword="$adhan@1997";

                String toEmails="sadhana.ravoori@gmail.com";
                List toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));

                String emailSubject="V-Connect";
                String emailBody="Thank you for rgistering for the event";

                new SendMailTask(SendMailActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
            }
        });
    }
}