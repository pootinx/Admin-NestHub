package com.example.admin_nesthub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nesthub.models.HouseModel;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private Context context;
    private List<HouseModel> houseList;
    public OnItemClickListener onItemClickListener;
    public OnDeleteClickListener onDeleteClickListener;

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(HouseModel house);
    }

    // Interface for delete button click events
    public interface OnDeleteClickListener {
        void onDeleteClick(String documentId);
    }

    // Constructor to initialize the adapter
    public HouseAdapter(Context context, List<HouseModel> houseList) {
        this.context = context;
        this.houseList = houseList;
    }

    // Setter for item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Setter for delete button click listener
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    // Inflates the item view and returns a ViewHolder
    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardhome_verticall, parent, false);
        return new HouseViewHolder(view);
    }

    // Binds data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        // Set data to views in the ViewHolder
        holder.bind(houseList.get(position));

        // Set OnClickListener for the item
        holder.cardclick.setOnClickListener(v -> {
            // Check if the listener is set and call onItemClick
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(houseList.get(position));
            }
        });





        // Set OnClickListener for the delete button
        holder.btndelete.setOnClickListener(v -> {
            // Call a method to delete the item from Firestore
            if (onDeleteClickListener != null) {
                String documentId = houseList.get(position).getDocumentId();
                if (documentId != null) { // Ensure documentId is not null
                    onDeleteClickListener.onDeleteClick(documentId);
                } else {
                    Log.e("HouseAdapter", "Document ID is null");
                }
            }
        });
    }

    // Returns the total number of items in the data set
    @Override
    public int getItemCount() {
        return houseList.size();
    }

    // ViewHolder class to hold references to views
    public static class HouseViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView, locationTextView, priceTextView;
        View cardclick;
        CardView btndelete;

        // Constructor to initialize views
        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagecard);
            titleTextView = itemView.findViewById(R.id.title);
            locationTextView = itemView.findViewById(R.id.location);
            priceTextView = itemView.findViewById(R.id.price);
            cardclick = itemView.findViewById(R.id.cardclick);
            btndelete = itemView.findViewById(R.id.btndelete);
        }

        // Bind data to views
        public void bind(HouseModel house) {
            titleTextView.setText(house.getTitle());
            locationTextView.setText(house.getLocation());
            priceTextView.setText(house.getPrice());

            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(house.getUrl_image())
                    .into(imageView);
        }
    }
}
