package com.rmi.chatroom;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements Chatroom
{
    // static ArrayList<String> content_pool = new ArrayList<String>();
    public Map<String, ArrayList<String>> roomMessages; 

    /**
     * Constructor
     */
    public Server() {
        this.roomMessages = new HashMap<>();
    }

    /**
     * Create a new room with a unique room name
     * @param roomName Name of the room
     */
    public void createRoom(String roomName) {
        if(!this.roomMessages.containsKey(roomName)){
            this.roomMessages.put(roomName, new ArrayList<String>());
        }
    }

    /**
     * Remove an existing room
     * @param roomName Name of the room
     */
    public void removeRoom(String roomName) {
        if(this.roomMessages.containsKey(roomName)){
            this.roomMessages.remove(roomName);
        }
    }

    /**
     * Send a message to a specific room
     * @param roomName Name of the room to store the message for
     * @param userName Name of the user who sent the message
     * @param message The actual Message
     */
    public void sendMessage(String roomName, String userName, String message) {
        if(this.roomMessages.containsKey(roomName)){
            this.roomMessages.get(roomName).add(userName + " sent:: " + message);
        }
    }

    /**
     * Get all the available rooms from the registry
     * @return All room names as a hash set
     */
    public String getRooms(){
        return this.roomMessages.keySet().toString();
    }

    /**
     * Get all the messages from a specific room
     * @param roomName Name of the room
     * @return All rooms as an array list of strings
     */
    public ArrayList<String> getMessagesFromRoom(String roomName){
        if(this.roomMessages.containsKey(roomName))
            return this.roomMessages.get(roomName);
        else
            return new ArrayList<>();
    }

    public static void main( String[] args )
    {
        try{
            Server server = new Server();
            Chatroom stub = (Chatroom) UnicastRemoteObject.exportObject(server, 0);

            //Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(5099);
            registry.rebind("chatroom", stub);

            System.out.println("Server is ready");
        }
        catch(Exception e){
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
