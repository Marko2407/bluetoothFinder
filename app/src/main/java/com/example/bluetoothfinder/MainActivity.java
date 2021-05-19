package com.example.bluetoothfinder;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    ListView listView;
    ArrayList<String> bluetoothDevice = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    BluetoothAdapter bluetoothAdapter;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Action", action);

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                textView.setText("Done...");
                button.setEnabled(true);

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi = Integer.toString(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                Log.i("Device Found", "Name: " + name + "Address: " + address + "RSSI: " + rssi);

                if (!addresses.contains(address)) {
                    addresses.add(address);
                    String deviceString = "";
                    if (name == null || name.equals("")) {
                        deviceString = address + " - RSSI " + rssi + "dBm";
                    } else {
                        deviceString = name + " - RSSI " + rssi + "dBm";
                    }
                    bluetoothDevice.add(deviceString);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public void find(View view) {

        textView.setText("Searching...");
        button.setEnabled(false);
        bluetoothDevice.clear();
        addresses.clear();
        bluetoothAdapter.startDiscovery();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        textView = findViewById(R.id.statusTextView);
        button = findViewById(R.id.searchButton);
        listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bluetoothDevice);

        listView.setAdapter(arrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, intentFilter);


    }
}


