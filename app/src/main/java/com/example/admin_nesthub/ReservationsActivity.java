package com.example.admin_nesthub;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private List<ReservationItem> reservationList;
    private DatabaseReference reservationsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.reservations_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reservationList = new ArrayList<>();
        reservationAdapter = new ReservationAdapter(this, reservationList);
        recyclerView.setAdapter(reservationAdapter);

        // Get reference to the reservations node in the Realtime Database
        // Get reference to the root node in the Realtime Database
        reservationsRef = FirebaseDatabase.getInstance().getReference();

// Fetch reservations from the Realtime Database
        fetchReservations();

    }

    private void fetchReservations() {
        reservationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reservationList.clear();

                // Iterate over all users
                for (DataSnapshot userSnapshot : dataSnapshot.child("UserInfo").getChildren()) {
                    // Iterate over reservations for each user
                    for (DataSnapshot reservationSnapshot : userSnapshot.child("reservations").getChildren()) {
                        ReservationItem reservation = reservationSnapshot.getValue(ReservationItem.class);
                        reservationList.add(reservation);

                        // Add logging
                        Log.d("ReservationsActivity", "Received reservation: " + reservation);
                    }
                }

                reservationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ReservationsActivity", "Error fetching reservations", databaseError.toException());
            }
        });
    }

}
