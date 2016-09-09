package org.drulabs.localdash;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

/**
 * Authored by KaushalD on 9/6/2016.
 */
public class TempClass {

    NsdManager.RegistrationListener mRegistrationListener = null;
    NsdManager mNsdManager;

    public void registerService(Context context, int port) {
        NsdServiceInfo serviceInfo  = new NsdServiceInfo();
        serviceInfo.setServiceName("NsdChat");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(port);

        mNsdManager = (NsdManager) context.getSystemService
                (Context.NSD_SERVICE);

        mNsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                mRegistrationListener);
    }
}
