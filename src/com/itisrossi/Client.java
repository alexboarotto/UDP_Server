package com.itisrossi;

import java.util.Scanner;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class Client {

    public final static int port = 8080;
    public static Scanner input = new Scanner(System.in);
    public static String user = "User";

    public static void main(String[] args) throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getLocalHost();
        DatagramSocket client = new DatagramSocket();

        System.out.print("Inserisci Username: ");
        user = input.nextLine();

        Thread receive = new Thread(() -> {
            while (!Thread.interrupted()){
                //Receive Message
                byte[] responseBuffer = new byte[512];
                DatagramPacket message = new DatagramPacket(responseBuffer, responseBuffer.length);

                try{
                    client.receive(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(new String(message.getData()));
            }
        });

        Thread send = new Thread(() -> {
            while (!Thread.interrupted()){

                //Send Message
                String message = input.nextLine();
                String p = (user + ": " + message);

                byte[] buffer = p.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                try{
                    client.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        send.start();
        receive.start();
    }
}
