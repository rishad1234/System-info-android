package com.example.admin.sysfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    String[] menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_item);
        menu = getResources().getStringArray(R.array.items);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.this,
                R.layout.item_view,
                R.id.menu_item,
                menu
                );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = menu[position];
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra("item_name", value);
                startActivity(intent);
            }
        });

    }
}
