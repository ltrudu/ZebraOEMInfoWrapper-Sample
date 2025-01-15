package com.zebra.emdk_deviceidentifiers_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zebra.oeminfowrapper.Helper_SoftwareInfo;
import com.zebra.oeminfowrapper.Helper_WanInfo;
import com.zebra.oeminfowrapper.IResultCallbacks;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SoftwareInfoActivity extends AppCompatActivity {
    public static final String TAG = "DeviceOEMInfo";

    static String status = "";
    TextView tvStatus;
    TextView tvOs_Delta_Support_Version;
    TextView tvBuild_Fingerprint;
    TextView tvBuild_Base_Fingerprint;
    TextView tvBuild_Baseline;
    TextView tvBuild_Version_Security_Patch;
    TextView tvBuild_Security_Critical;
    TextView tvDevice_Patch_Version;
    TextView tvSys_Cfe_Patch_Version;
    TextView tvCrypto_Type;
    TextView tvZDM_Version;
    TextView tvMX_Version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_info);

        tvStatus = findViewById(R.id.tv_status);
        tvOs_Delta_Support_Version = findViewById(R.id.tvOs_Delta_Support_Version);
        tvBuild_Fingerprint = findViewById(R.id.tvBuild_Fingerprint);
        tvBuild_Base_Fingerprint = findViewById(R.id.tvBuild_Base_Fingerprint);
        tvBuild_Baseline = findViewById(R.id.tvBuild_Baseline);
        tvBuild_Version_Security_Patch = findViewById(R.id.tvBuild_Version_Security_Patch);
        tvBuild_Security_Critical = findViewById(R.id.tvBuild_Security_Critical);
        tvDevice_Patch_Version = findViewById(R.id.tvDevice_Patch_Version);
        tvSys_Cfe_Patch_Version = findViewById(R.id.tvSys_Cfe_Patch_Version);
        tvCrypto_Type = findViewById(R.id.tvCrypto_Type);
        tvZDM_Version = findViewById(R.id.tvZDM_Version);
        tvMX_Version = findViewById(R.id.tvMX_Version);

        ((Button)findViewById(R.id.btRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTextViewContent(tvOs_Delta_Support_Version,"");
                updateTextViewContent(tvBuild_Fingerprint,"");
                updateTextViewContent(tvBuild_Base_Fingerprint,"");
                updateTextViewContent(tvBuild_Baseline,"");
                updateTextViewContent(tvBuild_Version_Security_Patch,"");
                updateTextViewContent(tvBuild_Security_Critical,"");
                updateTextViewContent(tvDevice_Patch_Version,"");
                updateTextViewContent(tvSys_Cfe_Patch_Version,"");
                updateTextViewContent(tvCrypto_Type,"");
                updateTextViewContent(tvZDM_Version,"");
                updateTextViewContent(tvMX_Version,"");
                Helper_SoftwareInfo.resetCachedValues();
                updateOs_Delta_Support_Version();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateOs_Delta_Support_Version();
    }

    protected void addMessageToStatusText(String message)
    {
        Log.d(TAG, message);
        if(message.contains("VERBOSE"))
            return;
        status += message + "\n";
        SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
            }
        });
    }

    private void updateTextViewContent(final TextView tv, final String text)
    {
        SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if(text !=null && text.isEmpty() == false)
                    tv.setText(text);
                else
                    tv.setText("Not Available");
            }
        });
    }

    private void updateOs_Delta_Support_Version()
    {
        addMessageToStatusText("Retrieving Os_Delta_Support_Version");
        Helper_SoftwareInfo.getOs_Delta_Support_Version(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Os_Delta_Support_Version retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvOs_Delta_Support_Version.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateBuild_Fingerprint();
            }

            @Override
            public void onError(String message) {
                tvOs_Delta_Support_Version.setText("Error");
                addMessageToStatusText("Error retrieving Os_Delta_Support_Version: " + message);
                updateBuild_Fingerprint();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateBuild_Fingerprint()
    {
        addMessageToStatusText("Retrieving Build_Fingerprint");
        Helper_SoftwareInfo.getBuild_Fingerprint(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Build_Fingerprint retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuild_Fingerprint.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateBuild_Base_Fingerprint();
            }

            @Override
            public void onError(String message) {
                tvBuild_Fingerprint.setText("Error");
                addMessageToStatusText("Error retrieving Build_Fingerprint: " + message);
                updateBuild_Base_Fingerprint();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateBuild_Base_Fingerprint()
    {
        addMessageToStatusText("Retrieving Build_Base_Fingerprint");
        Helper_SoftwareInfo.getBuild_Base_Fingerprint(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Build_Fingerprint retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuild_Base_Fingerprint.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateBuild_Baseline();
            }

            @Override
            public void onError(String message) {
                tvBuild_Base_Fingerprint.setText("Error");
                addMessageToStatusText("Error retrieving Build_Base_Fingerprint: " + message);
                updateBuild_Baseline();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateBuild_Baseline()
    {
        addMessageToStatusText("Retrieving Build_Baseline");
        Helper_SoftwareInfo.getBuild_Baseline(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Build_Baseline retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuild_Baseline.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateBuild_Version_Security_Patch();
            }

            @Override
            public void onError(String message) {
                tvBuild_Baseline.setText("Error");
                addMessageToStatusText("Error retrieving Build_Baseline: " + message);
                updateBuild_Version_Security_Patch();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateBuild_Version_Security_Patch()
    {
        addMessageToStatusText("Retrieving Build_Version_Security_Patch");
        Helper_SoftwareInfo.getBuild_Version_Security_Patch(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Build_Version_Security_Patch retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuild_Version_Security_Patch.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateBuild_Security_Critical();
            }

            @Override
            public void onError(String message) {
                tvBuild_Version_Security_Patch.setText("Error");
                addMessageToStatusText("Error retrieving Build_Version_Security_Patch: " + message);
                updateBuild_Security_Critical();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateBuild_Security_Critical()
    {
        addMessageToStatusText("Retrieving Build_Security_Critical");
        Helper_SoftwareInfo.getBuild_Security_Critical(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Build_Security_Critical retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuild_Security_Critical.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateDevice_Patch_Version();
            }

            @Override
            public void onError(String message) {
                tvBuild_Security_Critical.setText("Error");
                addMessageToStatusText("Error retrieving Build_Security_Critical: " + message);
                updateDevice_Patch_Version();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateDevice_Patch_Version()
    {
        addMessageToStatusText("Retrieving Device_Patch_Version");
        Helper_SoftwareInfo.getDevice_Patch_Version(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Device_Patch_Version retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDevice_Patch_Version.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateSys_Cfe_Patch_Version();
            }

            @Override
            public void onError(String message) {
                tvDevice_Patch_Version.setText("Error");
                addMessageToStatusText("Error retrieving Device_Patch_Version: " + message);
                updateSys_Cfe_Patch_Version();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateSys_Cfe_Patch_Version()
    {
        addMessageToStatusText("Retrieving Sys_Cfe_Patch_Version");
        Helper_SoftwareInfo.getSys_Cfe_Patch_Version(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Sys_Cfe_Patch_Version retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSys_Cfe_Patch_Version.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateCrypto_Type();
            }

            @Override
            public void onError(String message) {
                tvSys_Cfe_Patch_Version.setText("Error");
                addMessageToStatusText("Error retrieving Sys_Cfe_Patch_Version: " + message);
                updateCrypto_Type();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateCrypto_Type()
    {
        addMessageToStatusText("Retrieving Crypto_Type");
        Helper_SoftwareInfo.getCrypto_Type(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Crypto_Type retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCrypto_Type.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateZDM_Version();
            }

            @Override
            public void onError(String message) {
                tvCrypto_Type.setText("Error");
                addMessageToStatusText("Error retrieving Crypto_Type: " + message);
                updateZDM_Version();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateZDM_Version()
    {
        addMessageToStatusText("Retrieving ZDM_Version");
        Helper_SoftwareInfo.getZDM_Version(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("ZDM_Version retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvZDM_Version.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updateMX_Version();
            }

            @Override
            public void onError(String message) {
                tvZDM_Version.setText("Error");
                addMessageToStatusText("Error retrieving ZDM_Version: " + message);
                updateMX_Version();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updateMX_Version()
    {
        addMessageToStatusText("Retrieving MX_Version");
        Helper_SoftwareInfo.getMX_Version(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("MX_Version retrieved with success: " + message);
                SoftwareInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMX_Version.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
            }

            @Override
            public void onError(String message) {
                tvMX_Version.setText("Error");
                addMessageToStatusText("Error retrieving ZDM_Version: " + message);
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }
}