package com.itisrossi;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread{

    DatagramSocket socket;
    final static int port = 8080;

    ArrayList<User> users;

    final int BUFFER_SIZE = 512;

    public Server() throws SocketException {
        socket = new DatagramSocket(port);
        users = new ArrayList<User>();
    }

    @Override
    public void run(){
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);
        boolean newClient = true;

        while (!Thread.interrupted()){

            newClient = true;

            try {
                socket.receive(p);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!users.isEmpty()){
                for(User user: users){
                    if(p.getAddress().equals(user.getIp()) && p.getPort() == user.getPort()){
                        newClient = false;
                    }
                }
            }


            if(newClient) users.add(new User(p.getAddress(), p.getPort()));


            for(User user: users){
                DatagramPacket response = new DatagramPacket(p.getData(), p.getLength(), user.getIp(), user.getPort());
                System.out.println("sending response to " + user.getIp() + "..." + user.getPort());

                try {
                    socket.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        socket.close();
    }
}
