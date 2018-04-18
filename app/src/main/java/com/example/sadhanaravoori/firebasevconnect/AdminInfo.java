package com.example.sadhanaravoori.firebasevconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import android.widget.Toast;

public class AdminInfo extends AppCompatActivity {

    EditText name,phone,role;
    //RadioButton male,female,others;

    private DatabaseReference fire;

    Button next;
    RadioGroup gender;

    RadioButton g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);

        name=(EditText)findViewById(R.id.nameoforganization);
        phone=(EditText)findViewById(R.id.phone);
        role=(EditText)findViewById(R.id.roleofadmin);
        next=(Button)findViewById(R.id.next);
        gender = (RadioGroup)findViewById(R.id.gender);
        gender.clearCheck();

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                g = (RadioButton) group.findViewById(checkedId);
                if(null!=g && checkedId > -1){
                    Toast.makeText(getApplicationContext(), g.getText(), Toast.LENGTH_SHORT).show();
                }
                }

            });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putString("Name",name.getText().toString());
                bundle.putString("Phone",phone.getText().toString());
                bundle.putString("Role",role.getText().toString());
                bundle.putString("Gender",g.getText().toString());

                Intent intent = new Intent(getApplicationContext(), OrganizationDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}