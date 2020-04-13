package com.anyemi.housi;

public class CreateRoom {
    /**
     * status : sucess
     * Room_id : null
     * user_id : 89
     */

    private String status;
    private Object Room_id;
    private int user_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRoom_id() {
        return Room_id;
    }

    public void setRoom_id(Object Room_id) {
        this.Room_id = Room_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
