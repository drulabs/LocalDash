package org.drulabs.localdash;

import android.app.Application;

import org.drulabs.localdash.transfer.ConnectionListener;
import org.drulabs.localdash.utils.ConnectionUtils;

/**
 * Authored by KaushalD on 9/1/2016.
 */
public class AppController extends Application {

    private ConnectionListener connListener;
    private int myPort;

    private boolean isConnectionListenerRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        myPort = ConnectionUtils.getPort(getApplicationContext());
        connListener = new ConnectionListener(getApplicationContext(), myPort);
    }

    public void stopConnectionListener() {
        if (!isConnectionListenerRunning) {
            return;
        }
        if (connListener != null) {
            connListener.tearDown();
            connListener = null;
        }
        isConnectionListenerRunning = false;
    }

    public void startConnectionListener() {
        if (isConnectionListenerRunning) {
            return;
        }
        if (connListener == null) {
            connListener = new ConnectionListener(getApplicationContext(), myPort);
        }
        if (!connListener.isAlive()) {
            connListener.interrupt();
            connListener.tearDown();
            connListener = null;
        }
        connListener = new ConnectionListener(getApplicationContext(), myPort);
        connListener.start();
        isConnectionListenerRunning = true;
    }

    public void startConnectionListener(int port) {
        myPort = port;
        startConnectionListener();
    }

    public void restartConnectionListenerWith(int port) {
        stopConnectionListener();
        startConnectionListener(port);
    }

    public boolean isConnListenerRunning() {
        return isConnectionListenerRunning;
    }

    public int getPort(){
        return myPort;
    }
}
