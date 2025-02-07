package CLI;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int totalTickets;
    private int ticketReleaseRate;

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.ticketPool = ticketPool;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Event Name", new BigDecimal("1000"));
            ticketPool.addTicket(ticket);
            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
