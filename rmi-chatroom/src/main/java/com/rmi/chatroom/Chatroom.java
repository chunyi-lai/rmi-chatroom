package com.rmi.chatroom;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chatroom extends Remote {
    String get() throws RemoteException;
    void post(String post) throws RemoteException;
}