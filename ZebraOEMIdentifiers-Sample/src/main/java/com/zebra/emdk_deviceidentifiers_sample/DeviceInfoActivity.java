package com.zebra.emdk_deviceidentifiers_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zebra.oeminfowrapper.Helper_SecureInfo;
import com.zebra.oeminfowrapper.IResultCallbacks;

/**
 * Device Identifiers Sample
 *
 * Original Device Identifier Sample Code:
 *          - Darryn Campbell
 *          - https://github.com/darryncampbell/EMDK-DeviceIdentifiers-Sample
 *
 *  Wrapper Code:
 *          - Trudu Laurent
 *          - https://github.com/ltrudu/DeviceIdentifiersWrapper-Sample

 *  TODO: inherit from ZebraIdentifiersApplication and implements IZebraIdentifiersObserver
 *  TODO: or do your own call inside your Activity ;)
 *
 *  (c) Zebra 2020
 */

public class DeviceInfoActivity extends AppCompatActivity implements IZebraIdentifiersObserver {

    public static final String TAG = "DeviceOEMInfo";

    static  String status = "";
    TextView tvStatus;
    TextView tvSerialNumber;
    TextView tvIMEI;
    TextView tvBtMacAddress;
    TextView tvProductModel;
    TextView tvIdentityDeviceID;
    TextView tvWifiMacAddress;
    TextView tvWifiAPMacAddress;
    TextView tvWifiSSID;
    TextView tvEthernetMacAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvSerialNumber = (TextView) findViewById(R.id.txtSerialNumber);
        tvIMEI = (TextView) findViewById(R.id.txtImei);
        tvBtMacAddress = (TextView) findViewById(R.id.txtBtMacAddress);
        tvProductModel = (TextView) findViewById(R.id.txtProductModel);
        tvIdentityDeviceID = (TextView) findViewById(R.id.txtIdentityDeviceID);
        tvWifiMacAddress = (TextView) findViewById(R.id.txtWifiMacAddress);
        tvWifiAPMacAddress = (TextView) findViewById(R.id.txtWifiAPMacAddress);
        tvWifiSSID = (TextView) findViewById(R.id.txtWifiSSID);
        tvEthernetMacAddress = (TextView) findViewById(R.id.txtEthernetMacAddress);

        ((Button) findViewById(R.id.btRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTextViewContent(tvIMEI, "");
                updateTextViewContent(tvSerialNumber,"");
                updateTextViewContent(tvBtMacAddress,"");
                updateTextViewContent(tvProductModel,"");
                updateTextViewContent(tvIdentityDeviceID,"");
                updateTextViewContent(tvWifiMacAddress,"");
                // Wifi AP Mac Address and SSID are not bound to the device and can change
                // We will not use the ZebraIdentifierApplication, but directly retrieve them from the activity
                updateTextViewContent(tvWifiAPMacAddress,"");
                updateTextViewContent(tvWifiSSID,"");
                updateTextViewContent(tvEthernetMacAddress,"");

                ((MainApplication) DeviceInfoActivity.this.getApplication()).refresh();
                retrieveWifiAPMacAddress();
            }
        });


        ((Button)findViewById(R.id.btSoftwareInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DeviceInfoActivity.this, SoftwareInfoActivity.class);
                startActivity(startIntent);
            }
        });

        ((Button)findViewById(R.id.btWanInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DeviceInfoActivity.this, WanInfoActivity.class);
                startActivity(startIntent);
            }
        });


        ((Button)findViewById(R.id.btOSUpdateInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(DeviceInfoActivity.this, OSUpdateInfoActivity.class);
                startActivity(startIntent);
            }
        });

    }


        @Override
    protected void onResume() {
        super.onResume();
        updateTextViewContent(tvIMEI, ((ZebraIdentifiersApplication)getApplication()).getIMEI());
        updateTextViewContent(tvSerialNumber,((ZebraIdentifiersApplication)getApplication()).getSerialNumber());
        updateTextViewContent(tvBtMacAddress,((ZebraIdentifiersApplication)getApplication()).getBtMacAddress());
        updateTextViewContent(tvProductModel,((ZebraIdentifiersApplication)getApplication()).getProductModel());
        updateTextViewContent(tvIdentityDeviceID,((ZebraIdentifiersApplication)getApplication()).getIdentityDeviceID());
        updateTextViewContent(tvWifiMacAddress,((ZebraIdentifiersApplication)getApplication()).getWifiMacAddress());
        // Wifi AP Mac Address and SSID are not bound to the device and can change
        // We will not use the ZebraIdentifierApplication, but directly retrieve them from the activity
        updateTextViewContent(tvWifiAPMacAddress,"");
        updateTextViewContent(tvWifiSSID,"");
        updateTextViewContent(tvEthernetMacAddress,((ZebraIdentifiersApplication)getApplication()).getEthernetMacAddress());

        retrieveWifiAPMacAddress();
    }

    protected void addMessageToStatusText(String message)
    {
        Log.d(TAG, message);
        status += message + "\n";
        DeviceInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
            }
        });
    }

    private void updateTextViewContent(final TextView tv, final String text)
    {
        DeviceInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if(text !=null && text.isEmpty() == false)
                    tv.setText(text);
                else
                    tv.setText("Not Available");
            }
        });
    }

    @Override
    public void onSerialNumberUpdate(String serialNumber) {
        updateTextViewContent(tvSerialNumber, serialNumber);
    }

    @Override
    public void onIMEINumberUpdate(String imeiNumber) {
            updateTextViewContent(tvIMEI, imeiNumber);
     }

    @Override
    public void onBTMacAddressUpdate(String btMacAddress) { updateTextViewContent(tvBtMacAddress, btMacAddress); }

    @Override
    public void onProductModelUpdate(String productModel) {
        updateTextViewContent(tvProductModel, productModel);
    }

    @Override
    public void onIdentityDeviceIDUpdate(String identityDeviceID) {
        updateTextViewContent(tvIdentityDeviceID, identityDeviceID);
    }

    @Override
    public void onWifiMacAddressUpdate(String wifiMacAddress) {
        updateTextViewContent(tvWifiMacAddress, wifiMacAddress);
    }

    @Override
    public void onEthernetMacAddressUpdate(String ethernetMacAddress) {
            updateTextViewContent(tvEthernetMacAddress, ethernetMacAddress);
    }

    @Override
    public void onErrorMessage(String message) {
        addMessageToStatusText("Error: " + message);
    }

    @Override
    public void onDebugMessage(String message) {
        // We do not print the processing message event because it contains the whole XML
        // making the status unreadable. Instead, we print it in the logCat
        if(message.contains("Processing profile") == false)
            addMessageToStatusText("Debug: " + message);
        else if(message.contains("VERBOSE:") == false)
            Log.d(TAG, message);
    }

    private void retrieveWifiAPMacAddress()
    {
        Helper_SecureInfo.getWifiAPMacAddress(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                updateTextViewContent(tvWifiAPMacAddress, message);
                retrieveWifiSSID();
            }

            @Override
            public void onError(String message) {
                addMessageToStatusText("Error: " + message);
                retrieveWifiSSID();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText("DEBUG:" + message);
            }
        });
    }

    private void retrieveWifiSSID()
    {
        Helper_SecureInfo.getWifiSSID(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                updateTextViewContent(tvWifiSSID, message);
            }

            @Override
            public void onError(String message) {
                addMessageToStatusText("Error:" + message);
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText("DEBUG:" + message);
            }
        });
    }

}