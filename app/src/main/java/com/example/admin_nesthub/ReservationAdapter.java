package com.example.admin_nesthub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private List<ReservationItem> reservationList;
    private Context context;

    public ReservationAdapter(Context context, List<ReservationItem> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reservations, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReservationItem reservation = reservationList.get(position);

        // Bind data to the views
        Glide.with(context).load(reservation.getUrl_image()).into(holder.url_image);
        holder.fullName.setText(reservation.getFullName());
        holder.number.setText(reservation.getnumber());
        holder.email.setText(reservation.getEmail());
        holder.titleItem.setText(reservation.getTitle());
        holder.locationItem.setText(reservation.getLocation());

        // Update the user information
        holder.userFullName.setText(reservation.getFullName());
        holder.userEmail.setText(reservation.getEmail());
        holder.userNumber.setText(reservation.getnumber());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView url_image;
        TextView fullName, number, email, titleItem, locationItem;
        TextView userFullName, userEmail, userNumber;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            url_image = itemView.findViewById(R.id.url_image_item);
            fullName = itemView.findViewById(R.id.fullname);
            number = itemView.findViewById(R.id.number);
            email = itemView.findViewById(R.id.email);
            titleItem = itemView.findViewById(R.id.titel_item);
            locationItem = itemView.findViewById(R.id.location_item);

            CardView callButton = itemView.findViewById(R.id.btncall);
            callButton.setOnClickListener(v -> {
                String phoneNumber = number.getText().toString();
                initiatePhoneCall(itemView.getContext(), phoneNumber);
            });

            // Add user information views
            userFullName = itemView.findViewById(R.id.fullname);
            userEmail = itemView.findViewById(R.id.email);
            userNumber = itemView.findViewById(R.id.number);
        }
    }
    private void initiatePhoneCall(Context context, String phoneNumber) {
        // Create an Intent to initiate a phone call
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));

        // Check if there is an activity that can handle the intent
        if (dialIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(dialIntent);
        } else {
            // Handle the case where no activity can handle the phone call intent
            Toast.makeText(context, "No application can handle this request.", Toast.LENGTH_SHORT).show();
        }
    }
}
