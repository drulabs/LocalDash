package org.drulabs.localdash.model;

import android.os.Build;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Authored by KaushalD on 8/26/2016.
 */
public class DeviceDTO implements Serializable {

    private String deviceName = Build.MODEL;
    private String osVersion = Build.VERSION.RELEASE;
    private String playerName = Build.MANUFACTURER;
    private String ip;
    private int port;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    @Override
    public String toString() {
        String stringRep = (new Gson()).toJson(this);
        return stringRep;
    }

    public static DeviceDTO fromJSON(String jsonRep) {
        Gson gson = new Gson();
        DeviceDTO deviceDTO = gson.fromJson(jsonRep, DeviceDTO.class);
        return deviceDTO;
    }
}
