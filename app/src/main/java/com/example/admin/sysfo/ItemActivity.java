package com.example.admin.sysfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL10;

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
            } else if (value.equals("CPU")) {
                cpuInfo(textView);
            } else if (value.equals("Display")) {
                displayInfo(textView);
            } else if (value.equals("Network")) {
                networkInfo(textView);
            } else if(value.equals("Battery")){
                batteryInfo(textView);
            }
        }
    }

    public void addSystemFeatures(TextView view) {

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
        Long installed_memory = mi.totalMem / 1048576L;

        String data = "Manufacturer       : " + Build.MANUFACTURER + "\n";
        data += "Device Type          : " + Build.TYPE + "\n";
        data += "Device Name        : " + Build.MODEL + "\n";
        data += "Brand                     : " + Build.BRAND + "\n";
        data += "Board                     : " + Build.BOARD + "\n";
        data += "Product                  : " + Build.PRODUCT + "\n";
        data += "Hardware               : " + Build.HARDWARE + "\n";
        data += "Bootloader             : " + Build.BOOTLOADER + "\n";
//        data += "Build ID             : " + Build.DISPLAY + "\n";
//        data += "Host                  : " + Build.HOST + "\n";
//        data += "Fingerprint        : " + Build.FINGERPRINT + "\n";
        data += "Device ID                : " + Build.ID + "\n";
        data += "Available memory : " + availableMegs + "MB" + "\n";
        data += "Installed memory  : " + installed_memory + "MB" + "\n";
        data += "Free storage           : " + (getAvailableMemorySize() / (1024 * 1024)) + "MB" + "\n";
        view.setText(data);
    }

//    public static long getTotalMemorySize(){
//        File path = Environment.getDataDirectory();
//        StatFs stat = new StatFs(path.getPath());
//        long blockSize = stat.getBlockSizeLong();
//        long totalBlocks = stat.getBlockCountLong();
//        return totalBlocks * blockSize;
//    }

    public static long getAvailableMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlock = stat.getAvailableBlocksLong();
        return availableBlock * blockSize;
    }


    public void cpuInfo(TextView view) {
        String data = "Cores        : " + getNumCores() + "\n";
        data += "Threads        : " + getNumCores() * 2 + "\n";
        data += "Info        : " + getInfo() + "\n";
        view.setText(data);
    }

    public static int getNumCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    private String getInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n").append(Build.CPU_ABI2).append("");
        if (new File("/proc/cpuinfo").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    sb.append(aLine + "\n");
                }

                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }

    public void displayInfo(TextView view) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        String hdr = "No";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            hdr = display.getHdrCapabilities().getSupportedHdrTypes().length > 0 ? "Yes" : "No";
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int) (metrics.density * 160f);

        String data = "resolution: " + size.x + " X " + size.y + "\n";
        data += "Display ID : " + display.getDisplayId() + "\n";
        data += "Display Name : " + display.getName() + "\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data += "HDR : " + hdr + "\n";
        }
        data += "Refresh rate : " + Math.round(display.getRefreshRate()) + "\n";
        data += "Density : " + densityDpi + "ppi" + "\n";

        view.setText(data);
    }



    public void networkInfo(TextView view) {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String data = "NetWork Operator: " + phoneType() + "\n";
        if(phoneType().equals("GSM")){
            data += "Network Operator : " + manager.getNetworkOperatorName() + "\n";
        }
        data += "Network countryISO : " + manager.getNetworkCountryIso() + "\n";
        data += "Sim countryISO : " + manager.getSimCountryIso() + "\n";
        data += "Sim Operator : " + manager.getSimOperator() + "\n";
        data += "Sim Operator name : " + manager.getSimOperatorName() + "\n";
        data += "Network roaming : " + manager.isNetworkRoaming() + "\n";
        data += "Icc Card : " + manager.hasIccCard() + "\n";
        view.setText(data);
    }

    public String phoneType(){
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        switch(manager.getPhoneType()){
            case TelephonyManager.PHONE_TYPE_CDMA :
                return "CDMA";
            case TelephonyManager.PHONE_TYPE_GSM :
                return "GSM";
            case TelephonyManager.PHONE_TYPE_SIP :
                return "STP";
            default:
                return "None";
        }
    }

    public void batteryInfo(TextView view){
        BatteryManager manager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batteryLevel = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int capacity = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        long avgCurrent = manager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        long currentNow = manager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);

        String data = "Battery Level : " + batteryLevel + "%\n";
        data += "Plug in : " + isPlugedIn() + "\n";
        data += "Battery Health : " + capacity + "%\n";
        data += "Current average : " + (avgCurrent / 1000) + "\n";
        data += "Current now : " + (currentNow / 1000) + "\n";

        view.setText(data);
    }

    public boolean isPlugedIn(){
        BatteryManager manager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int plugedIn = manager.getIntProperty(BatteryManager.BATTERY_PLUGGED_AC);

        return plugedIn == 0 ? true : false;
    }

}
