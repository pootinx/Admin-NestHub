package com.example.admin_nesthub;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.nesthub.models.HouseModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddProperty extends AppCompatActivity {

    static final int PICK_IMAGE_REQUEST = 1;
    Uri uriPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);


        MaterialButton addButton = findViewById(R.id.btnadd);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPropertyClick(v);
            }
        });

        ImageView imageadd = findViewById(R.id.imageselected);
        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open image picker
                openGal();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        AutoCompleteTextView propertyTypeAutoComplete = findViewById(R.id.autoCompletePropertyType);
        ArrayAdapter<CharSequence> propertyTypeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.property_types,
                android.R.layout.simple_dropdown_item_1line
        );
        propertyTypeAutoComplete.setAdapter(propertyTypeAdapter);

        propertyTypeAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPropertyType = (String) parent.getItemAtPosition(position);
                // You can use the selectedPropertyType as needed
            }
        });

        AutoCompleteTextView durationAutoComplete = findViewById(R.id.autoCompleteDuration);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.duration,
                android.R.layout.simple_dropdown_item_1line
        );
        durationAutoComplete.setAdapter(durationAdapter);

        durationAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDuration = (String) parent.getItemAtPosition(position);
                // You can use the selectedDuration as needed
            }
        });

        AutoCompleteTextView availableAutoComplete = findViewById(R.id.autoCompleteAvailable);
        ArrayAdapter<CharSequence> availableAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.available,
                android.R.layout.simple_dropdown_item_1line
        );
        availableAutoComplete.setAdapter(availableAdapter);

        availableAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAvailability = (String) parent.getItemAtPosition(position);
                // You can use the selectedAvailability as needed
            }
        });
    }

    public void showPropertyTypeDropdown(View view) {
        AutoCompleteTextView propertyTypeAutoComplete = findViewById(R.id.autoCompletePropertyType);
        propertyTypeAutoComplete.showDropDown();
    }

    public void showDurationDropdown(View view) {
        AutoCompleteTextView durationAutoComplete = findViewById(R.id.autoCompleteDuration);
        durationAutoComplete.showDropDown();
    }

    public void showAvailabilityDropdown(View view) {
        AutoCompleteTextView availableAutoComplete = findViewById(R.id.autoCompleteAvailable);
        availableAutoComplete.showDropDown();
    }

    private void openGal() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriPic = data.getData();
            ImageView pic = findViewById(R.id.imageselected);
            pic.setImageURI(uriPic);
            pic.setVisibility(View.VISIBLE);
        }
    }

    private void saveDataToFirestore(String imageUrl) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        TextInputEditText titleEditText = findViewById(R.id.title);
        TextInputEditText availabilityEditText = findViewById(R.id.availability);
        TextInputEditText descriptionEditText = findViewById(R.id.descriptio);
        TextInputEditText locationEditText = findViewById(R.id.location);
        TextInputEditText priceEditText = findViewById(R.id.price);

        AutoCompleteTextView propertyTypeAutoComplete = findViewById(R.id.autoCompletePropertyType);
        AutoCompleteTextView durationAutoComplete = findViewById(R.id.autoCompleteDuration);
        AutoCompleteTextView availableAutoComplete = findViewById(R.id.autoCompleteAvailable);

        String selectedPropertyType = propertyTypeAutoComplete.getText().toString();
        String selectedDuration = durationAutoComplete.getText().toString();
        String selectedAvailability = availableAutoComplete.getText().toString();

        HouseModel houseModel = new HouseModel(
                titleEditText.getText().toString(),
                availabilityEditText.getText().toString(),
                selectedPropertyType,
                "USD",  // Set currency as needed
                descriptionEditText.getText().toString(),
                selectedDuration,
                locationEditText.getText().toString(),
                imageUrl,
                priceEditText.getText().toString(),
                true  // Set availability as needed
        );

        firestore.collection("Nests")
                .add(houseModel)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Property added successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields or navigate to another screen as needed
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to add property", Toast.LENGTH_SHORT).show();
                });


    }





    public void onAddPropertyClick(View view) {
        if (uriPic != null) {
            // Upload image to Firebase Storage
            // Replace "images/" with your desired storage path
            String storagePath = "images/" + System.currentTimeMillis() + "." + getFileExtension(uriPic);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(storagePath);

            storageReference.putFile(uriPic)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully, get its download URL
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Save other data to Firestore along with the image URL
                            saveDataToFirestore(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
