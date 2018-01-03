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

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.util.Log;

/**
 * This class echoes a string called from JavaScript.
 */
public class CDVSecurityProvider extends CordovaPlugin {

    private static String LOG_TAG =  CDVSecurityProvider.class.getSimpleName();

    public static final String EVENTNAME_ERROR = "event name null or empty.";

    private Context getApplicationContext() {
        return this.cordova.getActivity().getApplicationContext();
    }

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
        Log.d(LOG_TAG, "CDVSecurityProvider Action Call" + action);
        if( action.equals("makeAsyncUpdateSecurityProvider")) {

            final String eventName = args.getString(0);

            try {
                ProviderInstaller.installIfNeeded(this.getApplicationContext());
                Log.d(LOG_TAG, "ProviderInstaller Update Success");
            } catch (GooglePlayServicesRepairableException e) {

                // Indicates that Google Play services is out of date, disabled, etc.

                // Prompt the user to install/update/enable Google Play services.
                GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), this.getApplicationContext());

                // Notify the SyncManager that a soft error occurred.
                // syncResult.stats.numIOExceptions++;

                Log.d(LOG_TAG, "ProviderInstaller failed: GooglePlayServicesRepairableException");
                return false;

            } catch (GooglePlayServicesNotAvailableException e) {
                // Indicates a non-recoverable error; the ProviderInstaller is not able
                // to install an up-to-date Provider.

                // Notify the SyncManager that a hard error occurred.
                // syncResult.stats.numAuthExceptions++;

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
