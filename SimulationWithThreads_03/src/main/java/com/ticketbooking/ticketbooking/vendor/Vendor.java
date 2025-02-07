package com.ticketbooking.ticketbooking.vendor;
import com.ticketbooking.ticketbooking.ticket.Ticket;
import com.ticketbooking.ticketbooking.ticketPool.TicketPool;
import java.math.BigDecimal;


public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketArrayList;


    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketArrayList) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketArrayList = ticketArrayList;
    }


    @Override
    public void run() {
        for (int i = 1; i<= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Simple Event", new BigDecimal("1000"));
            ticketArrayList.addTicket(ticket);
            try {

                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Vendor interrupted", e);
            }
        }
    }


}



