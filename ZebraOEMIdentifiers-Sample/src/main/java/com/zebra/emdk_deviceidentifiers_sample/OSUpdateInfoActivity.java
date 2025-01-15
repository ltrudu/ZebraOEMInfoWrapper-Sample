package com.zebra.emdk_deviceidentifiers_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zebra.oeminfowrapper.Helper_OSUpdateInfo;
import com.zebra.oeminfowrapper.Helper_SoftwareInfo;
import com.zebra.oeminfowrapper.IResultCallbacks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class OSUpdateInfoActivity extends AppCompatActivity {
    public static final String TAG = "DeviceOEMInfo";

    static String status = "";
    TextView tvStatus;
    TextView tvOSUpdateStatus = null;
    TextView tvOSUpdateDetail = null;
    TextView tvOSUpdateTimeStamp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osupdate_info);

        tvStatus = findViewById(R.id.tv_status);
        tvOSUpdateStatus = findViewById(R.id.tvOSUpdateStatus);
        tvOSUpdateDetail = findViewById(R.id.tvOSUpdateDetail);
        tvOSUpdateTimeStamp = findViewById(R.id.tvOSUpdateTimeStamp);

        ((Button)findViewById(R.id.btRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTextViewContent(tvOSUpdateStatus,"");
                updateTextViewContent(tvOSUpdateDetail,"");
                updateTextViewContent(tvOSUpdateTimeStamp,"");
                Helper_OSUpdateInfo.resetCachedValues();
                updatetvOSUpdateStatus();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updatetvOSUpdateStatus();
    }

    protected void addMessageToStatusText(String message)
    {
        Log.d(TAG, message);
        if(message.contains("VERBOSE"))
            return;
        status += message + "\n";
        OSUpdateInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
            }
        });
    }

    private void updateTextViewContent(final TextView tv, final String text)
    {
        OSUpdateInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if(text !=null && text.isEmpty() == false)
                    tv.setText(text);
                else
                    tv.setText("Not Available");
            }
        });
    }

    private void updatetvOSUpdateStatus()
    {
        addMessageToStatusText("Retrieving OSUpdateStatus");
        Helper_OSUpdateInfo.getStatus(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("OSUpdateStatus retrieved with success: " + message);
                OSUpdateInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvOSUpdateStatus.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvOSUpdateDetail();
            }

            @Override
            public void onError(String message) {
                tvOSUpdateStatus.setText("Error");
                addMessageToStatusText("Error retrieving OSUpdateStatus: " + message);
                updatetvOSUpdateDetail();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }


    private void updatetvOSUpdateDetail()
    {
        addMessageToStatusText("Retrieving OSUpdateDetail");
        Helper_OSUpdateInfo.getDetail(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("OSUpdateDetail retrieved with success: " + message);
                OSUpdateInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvOSUpdateDetail.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvOSUpdateTimeStamp();
            }

            @Override
            public void onError(String message) {
                tvOSUpdateDetail.setText("Error");
                addMessageToStatusText("Error retrieving OSUpdateDetail: " + message);
                updatetvOSUpdateTimeStamp();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }
    private void updatetvOSUpdateTimeStamp()
    {
        addMessageToStatusText("Retrieving OSUpdateTimeStamp");
        Helper_OSUpdateInfo.getTimeStamp(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("OSUpdateTimeStamp retrieved with success: " + message);
                OSUpdateInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(message.isEmpty())
                        {
                            tvOSUpdateTimeStamp.setText("Not Available");
                        }
                        else {
                            long timestamp = Long.valueOf(message);
                            Date date = new Date(timestamp);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY HH::mm::ss", Locale.getDefault());
                            String formattedDate = formatter.format(date);
                            tvOSUpdateTimeStamp.setText(formattedDate);
                        }
                    }
                });
            }

            @Override
            public void onError(String message) {
                tvOSUpdateTimeStamp.setText("Error");
                addMessageToStatusText("Error retrieving OSUpdateTimeStamp: " + message);
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }
}