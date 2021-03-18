package com.rmi.chatroom;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements Chatroom
{
    static ArrayList<String> content_pool = new ArrayList<String>();

    /**
     * Constructor
     */
    public Server() {}

    /**
     * Get the posts into string format
     */
    public String get(){
        return content_pool.toString();
    }

    /**
     * Add the message to the content pool
     */
    public void post(String content){
        content_pool.add(content);
    }

    public static void main( String[] args )
    {
        try{
            Server server = new Server();
            Chatroom stub = (Chatroom) UnicastRemoteObject.exportObject(server, 0);

            //Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1099);
            // System.out.println("Registry created");
            // System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            registry.bind("Hello", stub);

            System.out.println("Server is ready");
        }
        catch(Exception e){
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
