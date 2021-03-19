package com.rmi.chatroom;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;

public class Client {

    private String currentRoom;
    private String userName;
    // public Registry registry;
    // public Chatroom stub;

    public Client() throws Exception{    
        this.userName = null;
        this.currentRoom = null;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentRoom(){
        return this.currentRoom;
    }

    public String getUserName(){
        return this.userName;
    }

    public String input(){
        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();

        return userInput;
    }

    public static void main(String[] args){
        try{
            // Registry registry = LocateRegistry.getRegistry("localhost", 5099);
            // Chatroom stub = (Chatroom) registry.lookup("chatroom");

            // stub.post("hello");
            // stub.post("123");
            // String response = stub.get();
            // System.out.println("response: " + response);
            Client client = new Client();
            Registry registry = LocateRegistry.getRegistry("localhost", 5099);
            Chatroom stub = (Chatroom) registry.lookup("chatroom");
            System.out.println("===============================");
            System.out.println("| Welcome to the RMI Chatroom |");
            System.out.println("===============================");
            // System.out.println("You can choose to create a new chatroom or entering an existing room");
            System.out.println("Use keyboard interruption to exit (Ctrl-c)");

            while(true){
                String existingRooms = stub.getRooms();
                // Set<String> existingRooms = new HashSet<>();
                if(client.getCurrentRoom() == null){
                    System.out.println("Existing rooms: " + existingRooms.toString());
                    System.out.print("Would you like to enter an existing " +
                    "room or create a new room?(E for enter/C for create): ");
                    String userInput = client.input();

                    if (userInput.compareTo("E") == 0 || userInput.compareTo("e") == 0){
                        System.out.print("Please enter the name of the room to enter: ");
                        String roomName = client.input();
                        if(!existingRooms.contains(roomName)){
                            System.out.println("The room you entered does not exist");
                        }
                        else{
                            client.setCurrentRoom(roomName);
                        }
                    }
                    else if(userInput.compareTo("C") == 0 || userInput.compareTo("c") == 0){
                        System.out.print("Please enter a name for the new chatroom: ");
                        String roomName = client.input();
                        if(existingRooms.contains(roomName)){
                            System.out.println("Room name already in use, room creation unsucessful");
                        }
                        else{
                            stub.createRoom(roomName);
                            System.out.println("Room " + roomName + " created successfully" );
                        }
                    }
                }
                else{
                    System.out.println("You are currently in the room - " + client.getCurrentRoom());
                    System.out.println("---------------------------------------------------");
                    System.out.println("| To see all the messages in this room, enter M/m |");
                    System.out.println("| To exit the current room, enter E/e             |");
                    System.out.println("| To send a message, enter the message directly   |");
                    System.out.println("---------------------------------------------------");
                    System.out.print("Your input: ");
                    String userInput = client.input();
                    if (userInput.compareTo("M") == 0 || userInput.compareTo("m") == 0){
                        ArrayList<String> messages = stub.getMessagesFromRoom(client.getCurrentRoom());
                        System.out.println("All Messages:-------------------|");
                        for(String message : messages){
                            System.out.println("| " + message);
                        }
                        System.out.println("--------------------------------|");
                        System.out.println("");
                    }
                    else if(userInput.compareTo("E") == 0 || userInput.compareTo("e") == 0){
                        System.out.println("Exiting room - " + client.getCurrentRoom());
                        client.setCurrentRoom(null);
                    }
                    else if(userInput.compareTo("") != 0){
                        stub.sendMessage(client.getCurrentRoom(), "Test User", userInput);
                        System.out.println("Message sent");
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}