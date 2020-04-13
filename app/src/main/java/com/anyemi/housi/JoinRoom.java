package com.anyemi.housi;

public class JoinRoom {
    /**
     * status : sucess
     * msg : Joined Succesfuly
     * user_id : null
     * game_id :
     */

    private String status;
    private String msg;
    private Object user_id;
    private String game_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }
}
