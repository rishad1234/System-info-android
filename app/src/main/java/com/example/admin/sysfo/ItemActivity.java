package com.example.admin.sysfo;

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
        if(bundle != null){
            String value = bundle.getString("item_name");
            textView.setText(value);
        }
    }
}
