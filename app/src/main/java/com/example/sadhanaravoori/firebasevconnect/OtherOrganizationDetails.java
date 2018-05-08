package com.example.sadhanaravoori.firebasevconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OtherOrganizationDetails extends AppCompatActivity {

    DatabaseReference theReference;

    WebCrawledDetails obj=new WebCrawledDetails();

    private ListView volList;
    private ArrayList<WebCrawledDetails> volunteers=new ArrayList<WebCrawledDetails>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_organization_details);

        volList=(ListView)findViewById(R.id.listView1);

        //final EventDetails e=(EventDetails) getIntent().getSerializableExtra("EventDetails");

        theReference= FirebaseDatabase.getInstance().getReference().child("Organizations");
        Log.e("DisplayVolStuff",theReference.toString());

        final ArrayAdapter<WebCrawledDetails> arrayAdapter=new ArrayAdapter<WebCrawledDetails>(this, android.R.layout.simple_list_item_2,android.R.id.text1,volunteers){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText("Name: "+volunteers.get(position).getName());

                text2.setText("Address: "+volunteers.get(position).getAddress() );

                return view;
            }
        };

        volList.setAdapter(arrayAdapter);

        theReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> AllOrg = dataSnapshot.getChildren();
                String a="",b;
                for (DataSnapshot var1 : AllOrg) {
                    WebCrawledDetails obj=new WebCrawledDetails();
                    if(var1.getKey().equals("0")) {
                        a = var1.getValue().toString();

                        Log.e("fireChild", "Name " + a);
                    }
                    else{
                        b = var1.getValue().toString();

                        Log.e("fireChild", "Address " + b);
                        obj.setName(a);
                        obj.setAddress(b);
                        volunteers.add(obj);
                        arrayAdapter.notifyDataSetChanged();
                    }



                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}