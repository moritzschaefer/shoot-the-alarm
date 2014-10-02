
package za.co.neilson.alarm.bluetooth;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import za.co.neilson.alarm.alert.AlarmAlertActivity;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 *
 */
public class BluetoothClass  {

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private boolean mScanning;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 4000;
    private AlarmAlertActivity parent;

    public BluetoothClass(AlarmAlertActivity parent) {
        mHandler = new Handler();
        mScanning = false;
        this.parent = parent;

        final BluetoothManager bluetoothManager =
            (BluetoothManager) parent.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            //Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            //finish();
            return;
        }
        scanAndConnectLeDevice();
    }

    public void scanAndConnectLeDevice() {
        if(mScanning)
            return;

        // Stops scanning after a pre-defined scan period.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }, SCAN_PERIOD);

        mScanning = true;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
        new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                parent.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO add important stuff here
                        final Intent intent = new Intent(parent, DeviceControlActivity.class);
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
                        intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                        if (mScanning) {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                        }
                        stopAlarm();
                        parent.startActivity(intent);
                    }
                });
            }
        };

    public void stopAlarm() {
        parent.bluetoothQuit();
    }
}
