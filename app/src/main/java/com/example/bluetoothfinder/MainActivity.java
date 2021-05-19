package com.example.bluetoothfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    ListView listView;

    BluetoothAdapter bluetoothAdapter;

    public void find(View view){

        textView.setText("Searching...");
        button.setEnabled(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.statusTextView);
        button = findViewById(R.id.searchButton);
        listView = findViewById(R.id.listView);


    }
}