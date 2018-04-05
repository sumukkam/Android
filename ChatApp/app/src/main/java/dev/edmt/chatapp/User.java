package dev.edmt.chatapp;

/**
 * Created by Sushanth on 11/27/17.
 */

public class User {
    private String firebaseID;
    private String email;
    private String [] chatRoomIDs;


    public User(String firebaseID, String email, String [] chatRoomIDs) {
        this.setFirebaseID(firebaseID);
        this.setEmail(email);
        this.setChatRoomIDs(chatRoomIDs);
    }


    public String getFirebaseID() {
        return firebaseID;
    }
    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getChatRoomIDs() {
        return chatRoomIDs;
    }
    public void setChatRoomIDs(String[] chatRoomIDs) {
        this.chatRoomIDs = chatRoomIDs;
    }
}
