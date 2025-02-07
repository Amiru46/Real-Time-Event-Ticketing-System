package com.ticketbooking.ticketbooking.ticketPool;

import com.ticketbooking.ticketbooking.ticket.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {

    private final int maxTicketCapacity;
    private final List<Ticket> ticketArrayList;
    private int ticketsSold = 0;
    private final int totalTickets;
    private boolean simulationComplete = false;


    public TicketPool(int maxTicketCapacity, int totalTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketArrayList = Collections.synchronizedList(new ArrayList<>());
        this.totalTickets = totalTickets;
    }


    public synchronized void addTicket(Ticket ticket) {

        while (ticketArrayList.size() >= maxTicketCapacity) {
            try {
                System.out.println("Vendor waiting, ticket pool full: " + Thread.currentThread().getName());

                wait();
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                throw new RuntimeException("Vendor interrupted while waiting", e);
            }
        }

        ticketArrayList.add(ticket);
        System.out.println("Ticket added by " + Thread.currentThread().getName() +
                ". Current pool size: " + ticketArrayList.size());
        notifyAll(); // Notify customers waiting for tickets
    }

    public synchronized Ticket buyTicket() {
        try {
            while (true) {
                wait(); // May throw InterruptedException
            }
            // Ticket purchasing logic
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted while waiting for ticket. Exiting...");
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new RuntimeException("Customer interrupted while waiting for ticket", e); // Exit or handle the interruption
        }
    }


/*
    public synchronized Ticket buyTicket() {

        while (ticketArrayList.isEmpty()) {
            try {
                System.out.println("Customer waiting, no tickets available: " + Thread.currentThread().getName());

                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Customer interrupted while waiting", e);
            }
        }
        Ticket ticket = ticketArrayList.remove(0);
        System.out.println("Ticket bought by " + Thread.currentThread().getName() +
                ". Current pool size: " + ticketArrayList.size());

        notifyAll();
        return ticket;
    }
*/
}


