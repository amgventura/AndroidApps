package com.example.mapsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends AppCompatActivity {

    Button b1,b2,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    //TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        //getSupportActionBar().hide();

        //tView = findViewById(R.id.textView2);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        //b4 = findViewById(R.id.button4);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = findViewById(R.id.listView);

        if (BA.isEnabled()) {
            b1.setText("Turn off Bluetooth");
            b1.setBackgroundColor(Color.parseColor("#fd5e53"));
            //tView.setBackgroundColor(Color.parseColor("#21bf73"));
        }

    }
    public void on(View v){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            //Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
            //b1.setText("Turn Off Bluetooth");
        } /*else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }*/

        if (BA.isEnabled()) {
            BA.disable();
            Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
            b1.setText("Turn on Bluetooth");
            b1.setBackgroundColor(Color.parseColor("#21bf73"));
            //tView.setBackgroundColor(Color.parseColor("#fd5e53"));
        }

    }

    /*
    public void off(View v){
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }
     */

    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }


    public void list(View v){
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for(BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);
    }

    private void BTenabled(){
        Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
        b1.setBackgroundColor(Color.parseColor("#fd5e53"));
        //tView.setBackgroundColor(Color.parseColor("#21bf73"));
        b1.setText("Turn off Bluetooth");
    }

    private void userCancelled(){
        //b1.setText("Turn on Bluetooth");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                BTenabled();
            } else if (resultCode == RESULT_CANCELED) {
                userCancelled();
            }

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Bluetooth.this, Dashboard.class));
    }
}