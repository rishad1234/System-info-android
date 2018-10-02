package com.example.admin.sysfo;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

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
            } else if(value.equals("CPU")){
                cpuInfo(textView);
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
        data += "Free storage           : " + (getAvailableMemorySize()/(1024*1024)) + "MB" + "\n";
        view.setText(data);
    }

//    public static long getTotalMemorySize(){
//        File path = Environment.getDataDirectory();
//        StatFs stat = new StatFs(path.getPath());
//        long blockSize = stat.getBlockSizeLong();
//        long totalBlocks = stat.getBlockCountLong();
//        return totalBlocks * blockSize;
//    }

    public static long getAvailableMemorySize(){
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlock = stat.getAvailableBlocksLong();
        return availableBlock * blockSize;
    }


    public void cpuInfo(TextView view){
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

}
