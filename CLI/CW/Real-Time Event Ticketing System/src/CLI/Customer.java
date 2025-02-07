package CLI;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int quantity;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPool.buyTicket();
            System.out.println("Ticket: " + ticket + " bought by customer " + Thread.currentThread().getName());
            try {
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
