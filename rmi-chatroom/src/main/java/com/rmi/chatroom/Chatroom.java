package com.rmi.chatroom;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface Chatroom extends Remote {

    void createRoom(String roomName) throws RemoteException;
    void removeRoom(String roomName) throws RemoteException;
    void sendMessage(String roomName, String userName, String message) throws RemoteException;
    String getRooms() throws RemoteException;
    ArrayList<String> getMessagesFromRoom(String roomName) throws RemoteException;
}