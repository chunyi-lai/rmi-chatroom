package com.rmi.chatroom;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Client() {}

    public static void main(String[] args){
        String host = (args.length < 1) ? null : args[0];
        try{
            // System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Chatroom stub = (Chatroom) registry.lookup("Hello");

            stub.post("hello");
            stub.post("123");

            String response = stub.get();
            System.out.println("response: " + response);
        }
        catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}