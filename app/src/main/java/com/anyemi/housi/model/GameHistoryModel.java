package com.anyemi.housi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameHistoryModel {
    /**
     * history : [{"totoalamount":"80.00","game_id":"48","nooftickets":"2","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"32.00"},{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"48.00"}]},{"totoalamount":"45.00","game_id":"49","nooftickets":"1","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"45.00"}]},{"totoalamount":"34.00","game_id":"50","nooftickets":"1","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"34.00"}]},{"totoalamount":"23.00","game_id":"51","nooftickets":"1","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"23.00"}]},{"totoalamount":"58.00","game_id":"60","nooftickets":"2","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"34.00"},{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"24.00"}]},{"totoalamount":"26.00","game_id":"62","nooftickets":"1","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"26.00"}]},{"totoalamount":"32.00","game_id":"67","nooftickets":"1","payment_type":"C","tickets":[{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"32.00"}]}]
     * total_records : 7
     */

    private int total_records;
    private List<HistoryBean> history;

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public static class HistoryBean {
        /**
         * totoalamount : 80.00
         * game_id : 48
         * nooftickets : 2
         * payment_type : C
         * tickets : [{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"32.00"},{"1st":"0","1st_amount":"0.00","top_line":"0","top_amount":"0.00","middle_line":"0","middle_amount":"0.00","last_line":"0","last_amount":"0.00","housie":"0","housie_amount":"0.00","amount":"48.00"}]
         */

        private String totoalamount;
        private String game_id;
        private String nooftickets;
        private String payment_type;
        private List<TicketsBean> tickets;

        public String getTotoalamount() {
            return totoalamount;
        }

        public void setTotoalamount(String totoalamount) {
            this.totoalamount = totoalamount;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getNooftickets() {
            return nooftickets;
        }

        public void setNooftickets(String nooftickets) {
            this.nooftickets = nooftickets;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public List<TicketsBean> getTickets() {
            return tickets;
        }

        public void setTickets(List<TicketsBean> tickets) {
            this.tickets = tickets;
        }

        public static class TicketsBean {
            /**
             * 1st : 0
             * 1st_amount : 0.00
             * top_line : 0
             * top_amount : 0.00
             * middle_line : 0
             * middle_amount : 0.00
             * last_line : 0
             * last_amount : 0.00
             * housie : 0
             * housie_amount : 0.00
             * amount : 32.00
             */

            @SerializedName("1st")
            private String _$1st;
            @SerializedName("1st_amount")
            private String _$1st_amount;
            private String top_line;
            private String top_amount;
            private String middle_line;
            private String middle_amount;
            private String last_line;
            private String last_amount;
            private String housie;
            private String housie_amount;
            private String amount;

            public String get_$1st() {
                return _$1st;
            }

            public void set_$1st(String _$1st) {
                this._$1st = _$1st;
            }

            public String get_$1st_amount() {
                return _$1st_amount;
            }

            public void set_$1st_amount(String _$1st_amount) {
                this._$1st_amount = _$1st_amount;
            }

            public String getTop_line() {
                return top_line;
            }

            public void setTop_line(String top_line) {
                this.top_line = top_line;
            }

            public String getTop_amount() {
                return top_amount;
            }

            public void setTop_amount(String top_amount) {
                this.top_amount = top_amount;
            }

            public String getMiddle_line() {
                return middle_line;
            }

            public void setMiddle_line(String middle_line) {
                this.middle_line = middle_line;
            }

            public String getMiddle_amount() {
                return middle_amount;
            }

            public void setMiddle_amount(String middle_amount) {
                this.middle_amount = middle_amount;
            }

            public String getLast_line() {
                return last_line;
            }

            public void setLast_line(String last_line) {
                this.last_line = last_line;
            }

            public String getLast_amount() {
                return last_amount;
            }

            public void setLast_amount(String last_amount) {
                this.last_amount = last_amount;
            }

            public String getHousie() {
                return housie;
            }

            public void setHousie(String housie) {
                this.housie = housie;
            }

            public String getHousie_amount() {
                return housie_amount;
            }

            public void setHousie_amount(String housie_amount) {
                this.housie_amount = housie_amount;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }
    }
}
