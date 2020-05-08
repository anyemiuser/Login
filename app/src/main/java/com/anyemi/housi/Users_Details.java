package com.anyemi.housi;

import java.util.List;

public class Users_Details {

    /**
     * status : success
     * msg : Succesfully Sent
     * players : [{"user_id":"15","name":"xxxxxx"},{"user_id":"12","name":"yyyyyyyyyyyy"},{"user_id":"20","name":""}]
     */

    private String status;
    private String msg;
    private List<PlayersBean> players;

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

    public List<PlayersBean> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayersBean> players) {
        this.players = players;
    }

    public static class PlayersBean {
        /**
         * user_id : 15
         * name : xxxxxx
         */

        private String user_id;
        private String name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
