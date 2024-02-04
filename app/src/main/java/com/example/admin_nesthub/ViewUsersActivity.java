package com.example.admin_nesthub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_nesthub.models.UserModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.facebook.shimmer.ShimmerFrameLayout;

public class ViewUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<UserModel, UserAdapter.UserViewHolder> userAdapter;
    private DatabaseReference usersReference;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        mShimmerViewContainer = findViewById(R.id.shimmerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.useritems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersReference = FirebaseDatabase.getInstance().getReference().child("UserInfo");

        fetchDataAndUpdateAdapter();
    }

    private void fetchDataAndUpdateAdapter() {
        // Start shimmer
        mShimmerViewContainer.startShimmer();
        mShimmerViewContainer.setVisibility(View.VISIBLE);

        Query query = usersReference.orderByKey();

        FirebaseRecyclerOptions<UserModel> options =
                new FirebaseRecyclerOptions.Builder<UserModel>()
                        .setQuery(query, UserModel.class)
                        .build();

        userAdapter = new FirebaseRecyclerAdapter<UserModel, UserAdapter.UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position, @NonNull UserModel model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
                return new UserAdapter.UserViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                // Stop shimmer and hide when data is fetched
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        };

        recyclerView.setAdapter(userAdapter);
        userAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userAdapter != null) {
            userAdapter.stopListening();
        }
    }
}
