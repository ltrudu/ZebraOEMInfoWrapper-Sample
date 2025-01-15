package com.zebra.emdk_deviceidentifiers_sample;

/**
 * - You can call yourself the DIHelper.getSerialNumber and DIHelper.getIMEINumber inside
 * your activity or application.
 * - Or you can inherit from ZebraIdentifiersApplication in your own code
 * to get Serial Number and EMEI Number, and implements
 * the IZebraIdentifiersObserver interface in your Activity.
 */
public class MainApplication extends ZebraIdentifiersApplication {
    
}
