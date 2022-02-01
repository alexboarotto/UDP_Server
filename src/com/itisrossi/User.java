package com.itisrossi;

import java.net.InetAddress;

public class User {
    InetAddress ip;
    int port;

    public User(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
