*Please be aware that this library / application / sample is provided as a community project without any guarantee of support*
=========================================================

[![](https://jitpack.io/v/ltrudu/ZebraOEMInfoWrapper.svg)](https://jitpack.io/#ltrudu/ZebraOEMInfoWrapper)
[![](https://jitpack.io/v/ltrudu/ZebraOEMInfoWrapper/month.svg)](https://jitpack.io/#ltrudu/ZebraOEMInfoWrapper)


# ZebraOEMInfoWrapper

## Easy access to Zebra's devices OEM information !!!

Forget about StageNow, EMDK, certificates, application signature... complexity....

Just get the OEM Infos of your Zebra device in one method call (see at the end of this document).

Have fun with Zebra's devices :)

## Before implementing the API

Please, take the time to read the OEM Info documentation to see what information you can get.

https://techdocs.zebra.com/oeminfo/consume/

## Change Log !!! 

## 1.1 : getURIValue Error Fix
Changed method getURIValue from RetrieveOEMInfoTask to be compliant with Zebra's documentation

## 1.0 : First release

See sample App for a quick implementation of the library.

## Sample Repository
https://github.com/ltrudu/ZebraOEMInfoWrapper-Sample

Look for "TODO: MANDATORY FOR ZebraOEMInfoWrapper" section of this Readme to find what you need to add to your AndroidManifest.xml to use this wrapper.

## Important !!
```text
        Due to usage of the EMDK and the need to register the application, it is strongly advised to call the methods in your application class
	Check https://github.com/ltrudu/ZebraOEMInfoWrapper-Sample implementation.
	It's a basic implementation using static members.
	Feel free to remove statics and replace them with a better code in terms of architecture.
	The goal was to pass the idea that theses  number should be retrieved only once, and the best place for it is the Application class.
	Note that a mechanism is available to wait for the EMDK in case it would not be available (the classic use case is when your app respond to the BOOT_COMPLETED event that occurs way before the EMDK finishes its initialization)
``` 

## Description

A wrapper to easily retrieve the Zebra's OEM Information of an Android 11+ Zebra device.

How to access these informations on Zebra devices running Android 11

Android 10 limited access to device identifiers for all apps running on the platform regardless of their target API level.  As explained in the docs for [Android 10 privacy changes](https://developer.android.com/about/versions/10/privacy/changes) this includes the serial number, IMEI and some other identifiable information.

Android 11 and 13 removed more access to these information for security reasons.

Some of them are not available on classic Android OS (Wan information for example, Ethernet Mac Address).

**Zebra mobile computers running Android 11 are able to access all these information** however applications need to be **explicitly granted the ability** to do so and use a proprietary API.

To access to this API, you must first register your application using the AccessMgr MX's CSP.

You can do it using StageNow, more details here: https://github.com/darryncampbell/EMDK-DeviceIdentifiers-Sample

Or you can use this wrapper that will automatically register your application if it is necessary.

## Implementation
To use this helper on Zebra Android devices running Android 11 or higher, first declare a new permission in your AndroidManifest.xml

```xml
<uses-permission android:name="com.zebra.provider.READ"/>
<uses-permission android:name="com.symbol.emdk.permission.EMDK" />
<uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"
```

Then add a query element to retrive the data

```xml
    <queries>
        <provider android:authorities="oem_info" />
        <package android:name="com.symbol.emdk.emdkservice" />
    	<package android:name="com.zebra.zebracontentprovider"/>
    </queries>
```

Then add the uses-library element to your application 
```xml
        <uses-library android:name="com.symbol.emdk" />
```

Sample AdroidManifest.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.zebra.emdk_deviceidentifiers_sample">
    <!--> TODO: Add these permissions to your manifest </-->
    <uses-permission android:name="com.zebra.provider.READ"/>
    <uses-permission android:name="com.symbol.emdk.permission.EMDK" />
    <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE"

    <!--> TODO: Add query element to your manifest </-->
    <queries>
        <provider android:authorities="oem_info" />
        <package android:name="com.symbol.emdk.emdkservice" />
    	<package android:name="com.zebra.zebracontentprovider"/>
    </queries>
        
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
	<!--> TODO: Add the uses-library EMDK to your manifest </-->
        <uses-library android:name="com.symbol.emdk" />
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
 </manifest>
```
Update your project build.graddle file to add jitpack repository
```text
        maven { url 'https://jitpack.io' }        
```
Sample project build.gradle
```text
        buildscript {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:7.2.1'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven { url 'https://jitpack.io' }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}       
```

Finally, add ZebraOEMInfoWrapper dependency to your application build.graddle file:
```text
        implementation 'com.github.ltrudu:ZebraOEMInfoWrapper:+'        
```

Sample application build.graddle:
```text
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.github.ltrudu:ZebraOEMInfoWrapper:+'
}
```

## The information are separated into 4 classes:

- Helper_SecureInfo : Provides access to sensitive information like IMEI, Serial Number, Bluetooth Mac Address, Wifi Mac Address,...
- Helper_OSUpdateInfo : Provides access to information on the status of the current OS update process
- Helper_SoftwareInfo : Provides information on the versions of the softwares installed on the device like OS Baseline, OS Fingerprint, OS Patch version, ZDM Version, MX Version,...
- Helper_WanInfo : Provides information on the Wan config of the current device like Eid, Telephony Sim Operator, CarrierName, IccId, etc...

The full list of available values can be found on the Zebra website:
https://techdocs.zebra.com/oeminfo/consume/

Only the values marked as "Varies by Android version" are not implemented.

Feel free to add them and do a pull request on this code if you need them :)

Now you can use the following snippet codes to retrieve OEM information.

## Code Snippets

Snippet code to use to retrieve the Serial Number of the device:
```java
     private void getSerialNumber(Context context)
     {
         Helper_SecureInfo.getSerialNumber(context, new IDIResultCallbacks() {
             @Override
             public void onSuccess(String message) {
                 // The message contains the serial number
                 String mySerialNumber = message;
             }

             @Override
             public void onError(String message) {
                // An error occurred
             }

             @Override
             public void onDebugStatus(String message) {
                // You can use this method to get verbose information
                // about what's happening behind the curtain             
             }
         });
     }
```


Snippet code to use to retrieve the IMEI of the device:
```java
    private void getIMEINumber(Context context)
    {
        Helper_SecureInfo.getIMEINumber(context, new IDIResultCallbacks() {
            @Override
            public void onSuccess(String message) {
                // We've got an EMEI number
                String myIMEI = message;
            }

            @Override
            public void onError(String message) {
                // An error occurred
            }

            @Override
            public void onDebugStatus(String message) {
                // You can use this method to get verbose information
                // about what's happening behind the curtain
            }
        });
    }
```

Snippet code to use to retrieve the MX Version installed on the device:
```java
    private void getMXVersion(Context context)
    {
        Helper_SoftwareInfo.getMX_Version(context, new IDIResultCallbacks() {
            @Override
            public void onSuccess(String message) {
		// We've got the mx version
                String myMXVersion = message;
            }

            @Override
            public void onError(String message) {
                // An error occurred
            }

            @Override
            public void onDebugStatus(String message) {
                // You can use this method to get verbose information
                // about what's happening behind the curtain
            }
        });
    }
```


Snippet code to use to retrieve the the OS Build Version Security Patch installed on the device:
```java
    private void getBuildVersionSecurityPatch(Context context)
    {
        Helper_SoftwareInfo.getBuild_Version_Security_Patch(context, new IDIResultCallbacks() {
            @Override
            public void onSuccess(String message) {
				// We've got the build version security patch
                String myBuildVersionSecurityPatch = message;
            }

            @Override
            public void onError(String message) {
                // An error occurred
            }

            @Override
            public void onDebugStatus(String message) {
                // You can use this method to get verbose information
                // about what's happening behind the curtain
            }
        });
    }
```

As the previous methods are asynchronous, if you need more than one information, it is strongly recommended to call the next request inside the onSuccess or onError of the first request. 

See implementation of ZebraIdentifiersApplication inside the ZebraOEMInfoWrapper-Sample for a basic chain implementation.

Sample code if you need to get the serial number and the IMEI number of a device:
```java
     private void getDevicesIdentifiers(Context context)
     {
        // We first ask for the SerialNumber
         DIHelper.getSerialNumber(context, new IDIResultCallbacks() {
             @Override
             public void onSuccess(String message) {
                 // The message contains the serial number
                 String mySerialNumber = message;
                 // We've got the serial number, now we can ask for the IMEINumber
                 DIHelper.getIMEINumber(context, new IDIResultCallbacks() {
                    @Override
                    public void onSuccess(String message) {
                        // We've got an EMEI number
                        String myIMEI = message;
                    }

                    @Override
                    public void onError(String message) {
                        // An error occurred
                    }

                    @Override
                    public void onDebugStatus(String message) {
                        // You can use this method to get verbose information
                        // about what's happening behind the curtain
                    }
                });
             }

             @Override
             public void onError(String message) {
                // An error occured
                // Do something here with the error message
                // We had an error with the Serial Number, but it
                // doesn't prevent us from calling the getIMEINumber method
                DIHelper.getIMEINumber(context, new IDIResultCallbacks() {
                    @Override
                    public void onSuccess(String message) {
                        // We've got an EMEI number
                        String myIMEI = message;
                    }

                    @Override
                    public void onError(String message) {
                        // An error occurred
                    }

                    @Override
                    public void onDebugStatus(String message) {
                        // You can use this method to get verbose information
                        // about what's happening behind the curtain                    }
                });
             }

             @Override
             public void onDebugStatus(String message) {
                // You can use this method to get verbose information
                // about what's happening behind the curtain             
             }
         });
     }
```

All the methods from all the helpers implement the same mechanism.

You can just copy paste the current snippet and change the helper class and the method call to get the information you need.
