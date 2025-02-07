package CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> ticketQueue;
    private int maximumCapacity;

    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        this.ticketQueue.add(ticket);
        notify();
        System.out.println("Ticket added by " + Thread.currentThread().getName() + ". Current size: " + ticketQueue.size());
    }

    public synchronized Ticket buyTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        Ticket ticket = ticketQueue.poll();
        notifyAll();
        System.out.println("Ticket bought by " + Thread.currentThread().getName() + ". Current size: " + ticketQueue.size() + ". Ticket: " + ticket);
        return ticket;
    }
}
