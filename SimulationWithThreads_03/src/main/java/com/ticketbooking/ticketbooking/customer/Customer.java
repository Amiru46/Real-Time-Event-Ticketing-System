package com.ticketbooking.ticketbooking.customer;
import com.ticketbooking.ticketbooking.ticket.Ticket;
import com.ticketbooking.ticketbooking.ticketPool.TicketPool;


public class Customer implements Runnable {

    private final TicketPool ticketArrayList;

    private final int customerRetrievalRate;

    private final int quantity;


    public Customer(TicketPool ticketArrayList, int customerRetrievalRate, int quantity) {
        this.ticketArrayList = ticketArrayList;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }


    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            // Attempt to buy a ticket from the pool
            Ticket ticket = ticketArrayList.buyTicket();
            try {
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Customer interrupted", e);
            }
        }
    }


}

