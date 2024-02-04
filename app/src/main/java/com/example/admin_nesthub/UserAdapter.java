package com.example.admin_nesthub;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin_nesthub.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private static final String TAG = "UserAdapter";

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // FirebaseRecyclerAdapter handles this
    }

    @Override
    public int getItemCount() {
        // FirebaseRecyclerAdapter handles this
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView fullname, email, number;
        CardView userCard; // Change type to CardView

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);


            fullname = itemView.findViewById(R.id.fullname);
            email = itemView.findViewById(R.id.email);
            number = itemView.findViewById(R.id.number);
            userCard = itemView.findViewById(R.id.btndelete); // Change to the correct ID for your CardView
        }

        public void bind(UserModel user) {
            fullname.setText(user.getfullName());
            email.setText(user.getEmail());
            number.setText(user.getNumber());

            userCard.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle("Delete User");
                builder.setMessage("Are you sure you want to delete this user?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    // Call a method to delete the user from Realtime Database
                    deleteFromRealtimeDatabase(user.getUserId());
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    // User clicked "No", do nothing
                });
                builder.show();
            });
        }

        private void deleteFromRealtimeDatabase(String userId) {
            DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(userId);


            usersReference.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Handle successful deletion
                        Toast.makeText(itemView.getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e(TAG, "Error deleting user", e);
                        Toast.makeText(itemView.getContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
