package org.bsc.cordova;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;


import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class CDVSecurityProvider extends CordovaPlugin {

    private static String TAG =  CDVSecurityProvider.class.getSimpleName();

    public static final String EVENTNAME_ERROR = "event name null or empty.";

    final java.util.Map<String,BroadcastReceiver> receiverMap =
                    new java.util.HashMap<String,BroadcastReceiver>(10);


    /**
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return
     * @throws JSONException
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if( action.equals("makeAsyncUpdateSecurityProvider")) {

            final String eventName = args.getString(0);

            try {
                ProviderInstaller.installIfNeeded(this.cordova.getActivity().getApplicationContext());
                Log.d(LOG_TAG, "ProviderInstaller Update Success");
            } catch (GooglePlayServicesRepairableException e) {

                // Indicates that Google Play services is out of date, disabled, etc.

                // Prompt the user to install/update/enable Google Play services.
                GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), getContext());

                // Notify the SyncManager that a soft error occurred.
                syncResult.stats.numIOExceptions++;

                Log.d(LOG_TAG, "ProviderInstaller failed: GooglePlayServicesRepairableException");
                return false;

            } catch (GooglePlayServicesNotAvailableException e) {
                // Indicates a non-recoverable error; the ProviderInstaller is not able
                // to install an up-to-date Provider.

                // Notify the SyncManager that a hard error occurred.
                syncResult.stats.numAuthExceptions++;

                Log.d(LOG_TAG, "ProviderInstaller failed: GooglePlayServicesNotAvailableException");
                return false;
            }

            // If this is reached, you know that the provider was already up-to-date,
            // or was successfully updated.


            callbackContext.success();
            return true;
        }
        return false;
    }

}
