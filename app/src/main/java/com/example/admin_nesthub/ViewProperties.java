package com.example.admin_nesthub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nesthub.models.HouseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import com.facebook.shimmer.ShimmerFrameLayout;


public class ViewProperties extends AppCompatActivity implements HouseAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private HouseAdapter houseAdapter;
    private List<HouseModel> houseList;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_properties);
        mShimmerViewContainer = findViewById(R.id.shimmerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.exploreitems);
        houseList = new ArrayList<>();
        houseAdapter = new HouseAdapter(this, houseList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter to your RecyclerView
        recyclerView.setAdapter(houseAdapter);


        // Set the item click listener
        houseAdapter.setOnItemClickListener(new HouseAdapter.OnItemClickListener() {


            @Override
            public void onItemClick(HouseModel house) {
                // Handle item click, e.g., start ItemDetailsActivity
                Intent intent = new Intent(ViewProperties.this, ItemDetailsActivity.class);
                intent.putExtra("house", String.valueOf(house));
                startActivity(intent);
            }
        });

        // Set the delete button click listener
        houseAdapter.setOnDeleteClickListener(this);

        // Fetch data and update the adapter
        fetchDataAndUpdateAdapter();

    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fetchDataAndUpdateAdapter() {
        // Fetch data from Firestore
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        CollectionReference nestsCollection = ff.collection("Nests");

        nestsCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                // Clear existing data
                houseList.clear();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    HouseModel house = document.toObject(HouseModel.class);
                    house.setDocumentId(document.getId()); // Set the document ID
                    houseList.add(house);
                }

                // Notify the adapter about the changes
                houseAdapter.notifyDataSetChanged();
            } else {
                Log.e("ViewProperties", "Error fetching data from Firestore", task.getException());
            }
        });
    }

    // Override the onDeleteClick method to handle delete button clicks
    @Override
    public void onDeleteClick(String documentId) {
        // Call your Firestore delete method here
        deleteItemFromFirestore(documentId);
    }

    // Implement the method to delete an item from Firestore
    private void deleteItemFromFirestore(String documentId) {
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        CollectionReference nestsCollection = ff.collection("Nests");

        nestsCollection.document(documentId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Item deleted successfully, you might want to update the adapter
                            fetchDataAndUpdateAdapter();
                        } else {
                            Log.e("ViewProperties", "Error deleting item from Firestore", task.getException());
                        }
                    }
                });
    }
}
