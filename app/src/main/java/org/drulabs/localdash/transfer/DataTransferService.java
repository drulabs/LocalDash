package org.drulabs.localdash.transfer;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import org.drulabs.localdash.utils.Utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class DataTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "org.drulabs.localdash.SEND_FILE";
    public static final String ACTION_SEND_DATA = "org.drulabs.localdash.SEND_DATA";

    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String DEST_IP_ADDRESS = "host";
    public static final String DEST_PORT_NUMBER = "port";
    public static final String EXTRAS_SHARE_DATA = "sharedata";

//    public static final int SERVER_ONLY = 8999;
//    public static final int CLIENT_SERVER = 8999;

    public DataTransferService(String name) {
        super(name);
    }

    public DataTransferService() {
        super("DataTransferService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(DEST_IP_ADDRESS);
            int port = intent.getExtras().getInt(DEST_PORT_NUMBER);
            Socket socket = null;

            try {
                socket = new Socket(host, port);

                Log.d("DDDDX", "Client socket - " + socket.isConnected());
                OutputStream stream = socket.getOutputStream();
                ContentResolver cr = context.getContentResolver();
                InputStream is = null;
                try {
                    is = cr.openInputStream(Uri.parse(fileUri));
                } catch (FileNotFoundException e) {
                    Log.d("DDDDX", e.toString());
                }
                Utility.copyFile(is, stream);
                Log.d("DDDDX", "Client: Data written");
            } catch (IOException e) {
                Log.e("DDDDX", e.getMessage());
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }

        } else if (intent.getAction().equals(ACTION_SEND_DATA)) {
            String host = intent.getExtras().getString(DEST_IP_ADDRESS);
            int port = intent.getExtras().getInt(DEST_PORT_NUMBER);
            Socket socket = null;
            ITransferable transferObject = (ITransferable) intent.getExtras().getSerializable
                    (EXTRAS_SHARE_DATA);
            try {
                socket = new Socket(host, port);
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                oos.writeObject(transferObject);
                oos.close();

            } catch (IOException e) {
                Log.e("DXDX", "Device: " + Build.MANUFACTURER);
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
