package com.example.pawsapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

     DrawerLayout drawerLayout;
     ActionBarDrawerToggle toggle;

    EditText preferredDateInput;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ImageButton hamburgerButton = findViewById(R.id.hamburger_button);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        hamburgerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_services) {
                    Toast.makeText(MainActivity.this, "Services clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_appointment) {
                    Toast.makeText(MainActivity.this, "Appointment clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_about_us) {
                    Toast.makeText(MainActivity.this, "About Us clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_search) {
                    Toast.makeText(MainActivity.this, "Search clicked", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        preferredDateInput = findViewById(R.id.preferred_date_input);
        calendar = Calendar.getInstance();

        preferredDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        preferredDateInput.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(Calendar.MONTH, 2);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = preferredDateInput.getText().toString();
                if (selectedDate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                Calendar currentCalendar = Calendar.getInstance();
                Calendar maxCalendar = Calendar.getInstance();
                maxCalendar.add(Calendar.MONTH, 2);

                if (selectedCalendar.before(currentCalendar)) {
                    Toast.makeText(MainActivity.this, "Selected date cannot be in the past", Toast.LENGTH_SHORT).show();
                } else if (selectedCalendar.after(maxCalendar)) {
                    Toast.makeText(MainActivity.this, "Selected date cannot be more than 2 months in the future", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Date is valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
