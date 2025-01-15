package com.zebra.emdk_deviceidentifiers_sample;

public interface IZebraIdentifiersObserver {
    void onErrorMessage(String message);
    void onDebugMessage(String message);

    /*
    Called when the serial number has been retrieved for the first time
    This has to be implemented in any activity/service that needs to use the serial number
     */
    void onSerialNumberUpdate(String serialNumber);

    void onIMEINumberUpdate(String imeiNumber);

    void onBTMacAddressUpdate(String btMacAddress);

    void onProductModelUpdate(String productModel);

    void onIdentityDeviceIDUpdate(String identityDeviceID);

    void onWifiMacAddressUpdate(String wifiMacAddress);

    void onEthernetMacAddressUpdate(String ethernetMacAddress);

}
