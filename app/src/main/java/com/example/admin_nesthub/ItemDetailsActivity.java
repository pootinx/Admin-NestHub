package com.example.admin_nesthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextInputEditText availabilityEditText = findViewById(R.id.availability);
        availabilityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        TextInputEditText availabilityEditText = findViewById(R.id.availability);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ItemDetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle the selected date (year, month, dayOfMonth)
                        String selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);

                        // Ensure availabilityEditText is initialized before using setText
                        if (availabilityEditText != null) {
                            availabilityEditText.setText(selectedDate);
                        }
                    }
                },
                // Set the initial date (optional)
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();

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

}