package com.android.railreserve;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        startActivityForResult(AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setProviders(
                AuthUI.GOOGLE_PROVIDER
        )
        .build(), 1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        List<String> placesArray = new ArrayList<String>();
        placesArray.add("Mumbai");
        placesArray.add("Pune");
        placesArray.add("Delhi");
        placesArray.add("Goa");
        placesArray.add("Vellore");
        placesArray.add("Bangalore");
        placesArray.add("Kolkata");
        placesArray.add("Chennai");
        placesArray.add("Chandigarh");
        placesArray.add("Lucknow");
        placesArray.add("Kanpur");
        placesArray.add("Munnar");
        placesArray.add("Coorg");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, placesArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sSpinner = (Spinner) findViewById(R.id.source_spinner);
        sSpinner.setAdapter(adapter);
        final Spinner dSpinner = (Spinner) findViewById(R.id.dest_spinner);
        dSpinner.setAdapter(adapter);
        Button getTickets = (Button) findViewById(R.id.get_tickets_button);
        Button bookTicket = (Button) findViewById(R.id.book_ticket_button);
        getTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String sourceSelected = sSpinner.getSelectedItem().toString();
                String destSelected = dSpinner.getSelectedItem().toString();
                String UID = currentFirebaseUser.getUid();
                User entry = new User(sourceSelected,destSelected,dayOfMonth,monthOfYear,year);
                mDatabase.child("entries").child(UID).push().setValue(entry);
            }
        };

        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSelected = sSpinner.getSelectedItem().toString();
                String dSelected = dSpinner.getSelectedItem().toString();
                if(sSelected.equals(dSelected))
                {
                    Toast.makeText(MainActivity.this,"Source and destination cannot be same", Toast.LENGTH_LONG).show();
                }
                else {
                    DatePickerDialog mDate = new DatePickerDialog(MainActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    mDate.show();
                }
            }
        });
        getTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TicketsBooked.class);
                startActivity(intent);
            }
        });
    }

}