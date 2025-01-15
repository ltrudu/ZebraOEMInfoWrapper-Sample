package com.zebra.emdk_deviceidentifiers_sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zebra.oeminfowrapper.Helper_SoftwareInfo;
import com.zebra.oeminfowrapper.Helper_WanInfo;
import com.zebra.oeminfowrapper.IResultCallbacks;

import androidx.appcompat.app.AppCompatActivity;

public class WanInfoActivity extends AppCompatActivity {
    public static final String TAG = "DeviceOEMInfo";

    static String status = "";
    TextView tvStatus;

    TextView tvAllESIMProfilesInfo1;
    TextView tvAllESIMProfilesInfo2;
    TextView tvCarrierName1;
    TextView tvCarrierName2;
    TextView tvEid;
    TextView tvEid1;
    TextView tvEid2;
    TextView tvIccid1;
    TextView tvIccid2;
    TextView tvImei;
    TextView tvImei1;
    TextView tvImei2;
    TextView tvImsi1;
    TextView tvImsi2;
    TextView tvPreferredAPN;
    TextView tvSimState1;
    TextView tvSimState2;
    TextView tvTelephony_Sim_Operator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wan_info);

        tvStatus = findViewById(R.id.tv_status);
        tvAllESIMProfilesInfo1 = findViewById(R.id.tvAllESIMProfilesInfo1);
        tvAllESIMProfilesInfo2 = findViewById(R.id.tvAllESIMProfilesInfo2);
        tvCarrierName1 = findViewById(R.id.tvCarrierName1);
        tvCarrierName2 = findViewById(R.id.tvCarrierName2);
        tvEid = findViewById( R.id.tvEid);
        tvEid1 = findViewById(R.id.tvEid1);
        tvEid2 = findViewById(R.id.tvEid2);
        tvIccid1 = findViewById(R.id.tvIccid1);
        tvIccid2 = findViewById(R.id.tvIccid2);
        tvImei = findViewById( R.id.tvImei);
        tvImei1 = findViewById(R.id.tvImei1);
        tvImei2 = findViewById(R.id.tvImei2);
        tvImsi1 = findViewById(R.id.tvImsi1);
        tvImsi2 = findViewById(R.id.tvImsi2);
        tvPreferredAPN = findViewById(R.id.tvPreferredAPN);
        tvSimState1 = findViewById(R.id.tvSimState1);
        tvSimState2 = findViewById(R.id.tvSimState2);
        tvTelephony_Sim_Operator = findViewById(R.id.tvTelephony_Sim_Operator);

        ((Button)findViewById(R.id.btRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTextViewContent(tvAllESIMProfilesInfo1,"");
                updateTextViewContent(tvAllESIMProfilesInfo2,"");
                updateTextViewContent(tvCarrierName1,"");
                updateTextViewContent(tvCarrierName2,"");
                updateTextViewContent(tvEid,"");
                updateTextViewContent(tvEid1,"");
                updateTextViewContent(tvEid2,"");
                updateTextViewContent(tvIccid1,"");
                updateTextViewContent(tvIccid2,"");
                updateTextViewContent(tvImei,"");
                updateTextViewContent(tvImei1,"");
                updateTextViewContent(tvImei2,"");
                updateTextViewContent(tvImsi1,"");
                updateTextViewContent(tvImsi2,"");
                updateTextViewContent(tvPreferredAPN,"");
                updateTextViewContent(tvSimState1,"");
                updateTextViewContent(tvSimState2,"");
                updateTextViewContent(tvTelephony_Sim_Operator,"");
                Helper_WanInfo.resetCachedValues();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updatetvAllESIMProfilesInfo1();
                    }
                }).start();            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                updatetvAllESIMProfilesInfo1();
            }
        }).start();
    }

    protected void addMessageToStatusText(String message)
    {
        Log.d(TAG, message);
        if(message.contains("VERBOSE"))
            return;
        status += message + "\n";
        WanInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
            }
        });
    }


    private void updateTextViewContent(final TextView tv, final String text)
    {
        WanInfoActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if(text !=null && text.isEmpty() == false)
                    tv.setText(text);
                else
                    tv.setText("Not Available");
            }
        });
    }

    private void updatetvAllESIMProfilesInfo1()
    {
        addMessageToStatusText("Retrieving AllESIMProfilesInfo1");
        Helper_WanInfo.getAllESIMProfilesInfo1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("AllESIMProfilesInfo1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvAllESIMProfilesInfo1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvAllESIMProfilesInfo2();
            }

            @Override
            public void onError(String message) {
                tvAllESIMProfilesInfo1.setText("Error");
                addMessageToStatusText("Error retrieving AllESIMProfilesInfo1: " + message);
                updatetvAllESIMProfilesInfo2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvAllESIMProfilesInfo2()
    {
        addMessageToStatusText("Retrieving AllESIMProfilesInfo2");
        Helper_WanInfo.getAllESIMProfilesInfo2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("AllESIMProfilesInfo2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvAllESIMProfilesInfo2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvCarrierName1();
            }

            @Override
            public void onError(String message) {
                tvAllESIMProfilesInfo2.setText("Error");
                addMessageToStatusText("Error retrieving AllESIMProfilesInfo2: " + message);
                updatetvCarrierName1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });

    }

    private void updatetvCarrierName1()
    {
        addMessageToStatusText("Retrieving CarrierName1");
        Helper_WanInfo.getCarrierName1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("CarrierName1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCarrierName1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvCarrierName2();
            }

            @Override
            public void onError(String message) {
                tvCarrierName1.setText("Error");
                addMessageToStatusText("Error retrieving CarrierName1: " + message);
                updatetvCarrierName2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvCarrierName2()
    {
        addMessageToStatusText("Retrieving CarrierName2");
        Helper_WanInfo.getCarrierName2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("CarrierName2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCarrierName2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvEid();
            }

            @Override
            public void onError(String message) {
                tvCarrierName2.setText("Error");
                addMessageToStatusText("Error retrieving CarrierName2: " + message);
                updatetvEid();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvEid()
    {
        addMessageToStatusText("Retrieving Eid");
        Helper_WanInfo.getEid(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Eid retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvEid.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvEid1();
            }

            @Override
            public void onError(String message) {
                tvEid.setText("Error");
                addMessageToStatusText("Error retrieving Eid: " + message);
                updatetvEid1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvEid1()
    {
        addMessageToStatusText("Retrieving Eid1");
        Helper_WanInfo.getEid1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Eid1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvEid1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvEid2();
            }

            @Override
            public void onError(String message) {
                tvEid1.setText("Error");
                addMessageToStatusText("Error retrieving Eid1: " + message);
                updatetvEid2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvEid2()
    {
        addMessageToStatusText("Retrieving Eid2");
        Helper_WanInfo.getEid2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Eid2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvEid2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvIccid1();
            }

            @Override
            public void onError(String message) {
                tvEid2.setText("Error");
                addMessageToStatusText("Error retrieving Eid2: " + message);
                updatetvIccid1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }


    private void updatetvIccid1()
    {
        addMessageToStatusText("Retrieving Iccid1");
        Helper_WanInfo.getIccid1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Iccid1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvIccid1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvIccid2();
            }

            @Override
            public void onError(String message) {
                tvIccid1.setText("Error");
                addMessageToStatusText("Error retrieving Iccid1: " + message);
                updatetvIccid2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvIccid2()
    {
        addMessageToStatusText("Retrieving Iccid2");
        Helper_WanInfo.getIccid2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Iccid2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvIccid2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvImei();
            }

            @Override
            public void onError(String message) {
                tvIccid2.setText("Error");
                addMessageToStatusText("Error retrieving Iccid2: " + message);
                updatetvImei();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvImei()
    {
        addMessageToStatusText("Retrieving Imei");
        Helper_WanInfo.getImei(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Imei retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvImei.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvImei1();
            }

            @Override
            public void onError(String message) {
                tvImei.setText("Error");
                addMessageToStatusText("Error retrieving Imei: " + message);
                updatetvImei1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvImei1()
    {
        addMessageToStatusText("Retrieving Imei1");
        Helper_WanInfo.getImei1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Imei1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvImei1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvImei2();
            }

            @Override
            public void onError(String message) {
                tvImei1.setText("Error");
                addMessageToStatusText("Error retrieving Imei1: " + message);
                updatetvImei2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvImei2()
    {
        addMessageToStatusText("Retrieving Imei2");
        Helper_WanInfo.getImei2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Imei2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvImei2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvImsi1();
            }

            @Override
            public void onError(String message) {
                tvImei2.setText("Error");
                addMessageToStatusText("Error retrieving Imei2: " + message);
                updatetvImsi1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

   private void updatetvImsi1()
    {
        addMessageToStatusText("Retrieving Imsi1");
        Helper_WanInfo.getImsi1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Imsi1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvImsi1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvImsi2();
            }

            @Override
            public void onError(String message) {
                tvImsi1.setText("Error");
                addMessageToStatusText("Error retrieving Imsi1: " + message);
                updatetvImsi2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvImsi2()
    {
        addMessageToStatusText("Retrieving Imsi2");
        Helper_WanInfo.getImsi2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Imsi2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvImsi2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvPreferredAPN();
            }

            @Override
            public void onError(String message) {
                tvImsi2.setText("Error");
                addMessageToStatusText("Error retrieving Imsi2: " + message);
                updatetvPreferredAPN();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvPreferredAPN()
    {
        addMessageToStatusText("Retrieving PreferredAPN");
        Helper_WanInfo.getPreferredAPN(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("PreferredAPN retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPreferredAPN.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvSimState1();
            }

            @Override
            public void onError(String message) {
                tvPreferredAPN.setText("Error");
                addMessageToStatusText("Error retrieving PreferredAPN: " + message);
                updatetvSimState1();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvSimState1()
    {
        addMessageToStatusText("Retrieving SimState1");
        Helper_WanInfo.getSimState1(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("SimState1 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSimState1.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvSimState2();
            }

            @Override
            public void onError(String message) {
                tvSimState1.setText("Error");
                addMessageToStatusText("Error retrieving SimState1: " + message);
                updatetvSimState2();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvSimState2()
    {
        addMessageToStatusText("Retrieving SimState2");
        Helper_WanInfo.getSimState2(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("SimState2 retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSimState2.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
                updatetvTelephony_Sim_Operator();
            }

            @Override
            public void onError(String message) {
                tvSimState2.setText("Error");
                addMessageToStatusText("Error retrieving SimState2: " + message);
                updatetvTelephony_Sim_Operator();
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }

    private void updatetvTelephony_Sim_Operator()
    {
        addMessageToStatusText("Retrieving Telephony_Sim_Operator");
        Helper_WanInfo.getTelephony_Sim_Operator(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                addMessageToStatusText("Telephony_Sim_Operator retrieved with success: " + message);
                WanInfoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTelephony_Sim_Operator.setText(message.isEmpty() == false ? message : "Not Available");
                    }
                });
            }

            @Override
            public void onError(String message) {
                tvTelephony_Sim_Operator.setText("Error");
                addMessageToStatusText("Error retrieving Telephony_Sim_Operator: " + message);
            }

            @Override
            public void onDebugStatus(String message) {
                addMessageToStatusText(message);
            }
        });
    }
}