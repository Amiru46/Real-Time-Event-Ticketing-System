package CLI;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int totalTickets = 0;
        int ticketReleaseRate = 0;
        int customerRetrivalRate = 0;
        int maxTicketCapacity = 0;

        String fileName = "Config.json";
        Configuration config = new Configuration();

        File configFile = new File(fileName);
        if (configFile.exists()) {
            try {
                config = Configuration.loadFromJson(fileName);
                totalTickets = config.getTotalTickets();
                ticketReleaseRate = config.getTicketReleaseRate();
                customerRetrivalRate = config.getCustomerRetrivalRate();
                maxTicketCapacity = config.getMaxTicketCapacity();
                System.out.println("Configuration loaded from file.");
            } catch (IOException e) {
                System.out.println("Error loading configuration file: " + e.getMessage());
            }
        } else {
            Scanner input = new Scanner(System.in);

            System.out.println("Configuration setup");

            totalTickets = getValidInput(input, "Enter total ticket count: ");
            ticketReleaseRate = getValidInput(input, "Enter ticket release rate (seconds): ");
            customerRetrivalRate = getValidInput(input, "Enter customer retrieval rate: ");
            maxTicketCapacity = getValidInput(input, "Enter max ticket capacity: ");

            config.setTotalTickets(totalTickets);
            config.setTicketReleaseRate(ticketReleaseRate);
            config.setCustomerRetrivalRate(customerRetrivalRate);
            config.setMaxTicketCapacity(maxTicketCapacity);

            try {
                config.saveToJson(fileName);
                System.out.println("Configuration saved to file.");
            } catch (IOException e) {
                System.out.println("Error saving configuration: " + e.getMessage());
            }
        }

        System.out.println("Configuration process is done.");

        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Main Menu.");
                System.out.println("1. Start ticketing system.");
                System.out.println("2. Exit.");
                System.out.print("Select option (1 or 2): ");
                int option = input.nextInt();

                if (option == 1) {
                    System.out.print("Enter Vendor Count: ");
                    int vendorCount = input.nextInt();
                    System.out.print("Enter Customer Count: ");
                    int customerCount = input.nextInt();

                    TicketPool ticketPool = new TicketPool(maxTicketCapacity);

                    Vendor[] vendors = new Vendor[vendorCount];
                    for (int i = 0; i < vendors.length; i++) {
                        vendors[i] = new Vendor(totalTickets, ticketReleaseRate, ticketPool);
                        Thread vendorThread = new Thread(vendors[i], "Vendor ID-" + i);
                        vendorThread.start();
                    }

                    Customer[] customers = new Customer[customerCount];
                    for (int i = 0; i < customers.length; i++) {
                        customers[i] = new Customer(ticketPool, customerRetrivalRate, totalTickets);
                        Thread customerThread = new Thread(customers[i], "Customer ID-" + i);
                        customerThread.start();
                    }
                } else if (option == 2) {
                    break;
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine();
            }
        }
    }

    private static int getValidInput(Scanner input, String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = input.nextInt();
                if (value <= 0) {
                    throw new InputMismatchException("Value must be greater than 0.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine();
            }
        }
        return value;
    }
}
