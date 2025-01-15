package com.zebra.emdk_deviceidentifiers_sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.zebra.oeminfowrapper.Helper_SecureInfo;
import com.zebra.oeminfowrapper.IResultCallbacks;

public class ZebraIdentifiersApplication extends Application {
    private String sIMEI = null;
    private String sSerialNumber = null;
    private String sBtMacAddress = null;
    private String sProductModel = null;
    private String sIdentityDeviceID = null;
    private String sWIFIMacAddress = null;
    private String sEthernetMacAddress = null;


    private IZebraIdentifiersObserver iZebraIdentifiersObserver = null;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if(activity != null && activity instanceof IZebraIdentifiersObserver)
                    iZebraIdentifiersObserver = (IZebraIdentifiersObserver)activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if(iZebraIdentifiersObserver == activity)
                    iZebraIdentifiersObserver = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if(iZebraIdentifiersObserver == activity)
                    iZebraIdentifiersObserver = null;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(iZebraIdentifiersObserver == activity)
                    iZebraIdentifiersObserver = null;
            }
        });
        retrieveSerialNumber();
    }

    public void refresh()
    {
        sIMEI = null;
        sSerialNumber = null;
        sBtMacAddress = null;
        sProductModel = null;
        sIdentityDeviceID = null;
        sWIFIMacAddress = null;
        sEthernetMacAddress = null;
        Helper_SecureInfo.resetCachedValues();
        retrieveSerialNumber();
    }

    public String getIMEI()
    {
        return sIMEI;
    }

    public String getSerialNumber()
    {
        return sSerialNumber;
    }

    public String getBtMacAddress() { return sBtMacAddress; }

    public String getProductModel () { return sProductModel; }

    public String getIdentityDeviceID () { return sIdentityDeviceID; }

    public String getWifiMacAddress () { return sWIFIMacAddress; }

    public String getEthernetMacAddress () { return sEthernetMacAddress; }

    private void retrieveSerialNumber()
    {
        Helper_SecureInfo.getSerialNumber(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                sSerialNumber = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onSerialNumberUpdate(message);
                // We got the serial number, let's try the IMEI number
                retrieveIMEINumber();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                // We had an error, but we like to play, so we try the IMEI Number
                sSerialNumber = "Error";
                retrieveIMEINumber();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveIMEINumber()
    {
        Helper_SecureInfo.getIMEINumber(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got an IMEI number, let's update the text view
                sIMEI = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onIMEINumberUpdate(message);
                retrieveBTMacAddress();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sIMEI = "Error";
                retrieveBTMacAddress();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveBTMacAddress()
    {
        Helper_SecureInfo.getBtMacAddress(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got the bt mac address, let's update the text view
                sBtMacAddress = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onBTMacAddressUpdate(message);
                retrieveProductModel();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sBtMacAddress = "Error";
                retrieveProductModel();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveProductModel()
    {
        Helper_SecureInfo.getProductModel(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got the bt mac address, let's update the text view
                sProductModel = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onProductModelUpdate(message);
                retrieveIdentityDeviceID();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sProductModel = "Error";
                retrieveIdentityDeviceID();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveIdentityDeviceID()
    {
        Helper_SecureInfo.getIdentityDeviceID(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got the bt mac address, let's update the text view
                sIdentityDeviceID = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onIdentityDeviceIDUpdate(message);
                retrieveWifiMacAddress();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sIdentityDeviceID = "Error";
                retrieveWifiMacAddress();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveWifiMacAddress()
    {
        Helper_SecureInfo.getWifiMacAddress(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got the bt mac address, let's update the text view
                sWIFIMacAddress = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onWifiMacAddressUpdate(message);
                retrieveEthernetMacAddress();
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sWIFIMacAddress = "Error";
                retrieveEthernetMacAddress();
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

    private void retrieveEthernetMacAddress()
    {
        Helper_SecureInfo.getEthernetMacAddress(this, new IResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got the bt mac address, let's update the text view
                sEthernetMacAddress = message;
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onEthernetMacAddressUpdate(message);
            }

            @Override
            public void onError(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onErrorMessage(message);
                sEthernetMacAddress = "Error";
            }

            @Override
            public void onDebugStatus(String message) {
                if(iZebraIdentifiersObserver != null)
                    iZebraIdentifiersObserver.onDebugMessage(message);
            }
        });
    }

}
