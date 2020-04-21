package com.anyemi.housi;

public class RandomNumberGenerator {

    /**
     * status : success
     * msg : Succesfuly Sent
     * game_id : 74
     * numbers : [36,5,28,52,35,72,49,90,22,6,61,56,64,82,16,2,47,42,29,85,67,89,10,70,26,57,43,62,79,11,88,17,50,55,45,46,18,59,27,76,41,12,21,83,14,86,69,3,65,60,1,48,54,84,38,13,75,25,24,37,66,32,8,58,77,51,63,68,44,30,87,40,71,74,39,19,78,15,4,31,9,33,20,34,81,23,73,7,53,80]
     */

    private String status;
    private String msg;
    private String game_id;
    private String numbers;

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

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
