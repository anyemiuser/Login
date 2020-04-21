package com.anyemi.housi.model;

import java.util.List;

public class TicketsModel {


    private List<Integer> nos;
    private List<Integer> ticket_id;
    private List<List<List<Integer>>> ticket_nos;

    public List<Integer> getNos() {
        return nos;
    }

    public void setNos(List<Integer> nos) {
        this.nos = nos;
    }

    public List<Integer> getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(List<Integer> ticket_id) {
        this.ticket_id = ticket_id;
    }

    public List<List<List<Integer>>> getTicket_nos() {
        return ticket_nos;
    }

    public void setTicket_nos(List<List<List<Integer>>> ticket_nos) {
        this.ticket_nos = ticket_nos;
    }
}
