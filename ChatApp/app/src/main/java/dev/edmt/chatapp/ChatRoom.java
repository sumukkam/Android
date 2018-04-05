package dev.edmt.chatapp;

/**
 * Created by Sushanth on 11/27/17.
 */

public class ChatRoom {

    private String chatRoomFirebaseID;
    private User user1;
    private User user2;


    public ChatRoom(String chatRoomFirebaseID, User user1, User user2) {
        this.setChatRoomFirebaseID(chatRoomFirebaseID);
        this.setUser1(user1);
        this.setUser2(user2);
    }




    public String getChatRoomFirebaseID() {
        return chatRoomFirebaseID;
    }
    public void setChatRoomFirebaseID(String chatRoomFirebaseID) {
        this.chatRoomFirebaseID = chatRoomFirebaseID;
    }

    public User getUser1() {
        return user1;
    }
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }
    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
