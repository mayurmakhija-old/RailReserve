package com.android.railreserve;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class TicketsBooked extends AppCompatActivity {
    ArrayList<String> entryList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_booked);


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String UID = currentFirebaseUser.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("entries").child(UID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectEntries((Map<String, Object>) dataSnapshot.getValue());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TicketsBooked.this, android.R.layout.simple_list_item_1,entryList);
                ListView ticketsView = (ListView) findViewById(R.id.tickets_booked);
                ticketsView.setAdapter(arrayAdapter);
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                String source = map.get("source").toString();
//                String dest = map.get("destination").toString();
//                long day =(long)map.get("day");
//                long month =(long)map.get("month");
//                long year =(long)map.get("year");
//
//                Log.v("Data","Source: " + source);
//                Log.v("Data","Destination: " + dest);
//                Log.v("Data","Date: " + day + "/" + (month+1) + "/" + year);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void collectEntries(Map<String,Object> users) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            long day,month,year;
            day = (Long) singleUser.get("day");
            month = (Long) singleUser.get("month");
            year = (Long) singleUser.get("year");
            String date = "Date: " + day + "/" + (month+1) + "/" + year;
            String source = "Source: " + singleUser.get("source").toString();
            String dest = "Destination: " + singleUser.get("destination").toString();
            entryList.add(source + "\n" + dest + "\n" + date);
//            dateList.add(date);
//            sourceList.add(singleUser.get("source").toString());
//            destList.add(singleUser.get("destination").toString());
            Log.v("Data","Source: " + singleUser.get("source").toString());
            Log.v("Data","Destination: " + singleUser.get("destination").toString());
            Log.v("Data","Date: " + date);
        }
    }
}
