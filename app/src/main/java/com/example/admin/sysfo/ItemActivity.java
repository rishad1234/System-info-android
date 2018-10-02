package com.example.admin.sysfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        textView = findViewById(R.id.individual_item);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String value = bundle.getString("item_name");
            if (value.equals("System")) {
                addSystemFeatures(textView);
            }
        }
    }

    public void addSystemFeatures(TextView view) {
        String data = "Manufacturer : " + Build.MANUFACTURER + "\n";
        data += "Device Type    : " + Build.TYPE + "\n";
        data += "Device Name  : " + Build.MODEL + "\n";
        data += "Brand               : " + Build.BRAND + "\n";
        data += "Board               : " + Build.BOARD + "\n";
        data += "Product            : " + Build.PRODUCT + "\n";
        data += "Hardware         : " + Build.HARDWARE + "\n";
        data += "Bootloader       : " + Build.BOOTLOADER + "\n";
        data += "Build ID             : " + Build.DISPLAY + "\n";
        data += "Host                  : " + Build.HOST + "\n";
        data += "Fingerprint        : " + Build.FINGERPRINT + "\n";
        data += "Device ID           : " + Build.ID + "\n";
        view.setText(data);
    }
}
