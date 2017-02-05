package com.elsyscoursework.mymv;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Tomi on 5.2.2017 г..
 */

public class ImportExportActivity extends AppCompatActivity {

    private final int CONNECT_DEVICES = 1;
    private final int ENABLE_BLUETOOTH = 2;

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothService mService = null;

    TextView testView;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    break;
                case MESSAGE_WRITE:
                    Toast.makeText(ImportExportActivity.this, "stuffs3", Toast.LENGTH_LONG).show();
                    byte[] writeBuffer = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuffer);
                    testView.setText("Message sent!");
                    break;
                case MESSAGE_READ:
                    Toast.makeText(ImportExportActivity.this, "stuffs2", Toast.LENGTH_LONG).show();
                    byte[] readBuffer = (byte[]) msg.obj;
                    String readMessage = new String(readBuffer, 0, msg.arg1);
                    testView.setText(readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    String mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.import_export_activity);

        testView = (TextView) findViewById(R.id.testView);

        Button searchAndConnectButton = (Button) findViewById(R.id.search_and_connect_button);
        searchAndConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportExportActivity.this, ListDevicesActivity.class);
                startActivityForResult(intent, CONNECT_DEVICES);
            }
        });

        final EditText testView2 = (EditText) findViewById(R.id.testView2);

        Button test2 = (Button) findViewById(R.id.send_button);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = testView2.getText().toString();
                byte[] send = message.getBytes();
                mService.write(send);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, ENABLE_BLUETOOTH);
        } else {
            mService = new BluetoothService(ImportExportActivity.this, mHandler);
        }
    }

    @Override
    protected synchronized void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mService != null) {
            if (mService.getState() == BluetoothService.STATE_NONE) {
                mService.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            mService.stop();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONNECT_DEVICES) {
            if (resultCode == Activity.RESULT_OK) {
                String address = data.getExtras().getString(ListDevicesActivity.DEVICE_ADDRESS);
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                mService.connect(device);
            }
        } else if (requestCode == ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                mService = new BluetoothService(ImportExportActivity.this, mHandler);
            } else {
                Toast.makeText(ImportExportActivity.this, "Bluetooth was not turned on, finishing activity...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
