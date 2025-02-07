package com.ticketbooking.ticketbooking.ticket;

import java.math.BigDecimal;

public class Ticket {

    private int ticket_ID;

    private String event_Name;

    private BigDecimal ticket_price;


    public Ticket(int ticket_ID, String event_Name, BigDecimal ticket_price) {
        this.ticket_ID = ticket_ID;
        this.event_Name = event_Name;
        this.ticket_price = ticket_price;
    }

    public int getTicket_ID() {
        return ticket_ID;
    }
    public void setTicket_ID(int ticket_ID) {
        this.ticket_ID = ticket_ID;
    }
    public String getEvent_Name() {
        return event_Name;
    }
    public void setEvent_Name(String event_Name) {
        this.event_Name = event_Name;
    }
    public BigDecimal getTicket_price() {
        return ticket_price;
    }
    public void setTicket_price(BigDecimal ticket_price) {
        this.ticket_price = ticket_price;
    }

    @Override
    public String toString(){
        return "ticket_ID ="+getTicket_ID()
                +",event_Name ="+getEvent_Name()
                +",ticket_price ="+getTicket_price();
    }
}