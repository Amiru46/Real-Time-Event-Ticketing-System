package com.ticketbooking.ticketbooking.controller;
import com.ticketbooking.ticketbooking.customer.Customer;
import com.ticketbooking.ticketbooking.entity.SystemConfiguration;
import com.ticketbooking.ticketbooking.repository.SystemConfigurationRepository;
import com.ticketbooking.ticketbooking.ticketPool.TicketPool;
import com.ticketbooking.ticketbooking.vendor.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api/semiApp")
@CrossOrigin(origins = "http://localhost:3000")
public class ConfigurationController {

    @Autowired

    private SystemConfigurationRepository systemConfigurationRepository;


    private final List<Thread> runningThreads = new ArrayList<>();

    private volatile boolean programmeRunning = false;

    @PutMapping("/start")

    public String startProgramme(
            @RequestParam int maxTicketCapacity,
            @RequestParam int totalNoOfTickets,
            @RequestParam int ticketReleaseRate,
            @RequestParam int customerRetrievalRate) {


        SystemConfiguration systemConfiguration = new SystemConfiguration(
                maxTicketCapacity,
                totalNoOfTickets,
                ticketReleaseRate,
                customerRetrievalRate
        );
        systemConfigurationRepository.save(systemConfiguration);


        TicketPool ticketPool = new TicketPool(maxTicketCapacity, totalNoOfTickets);


        programmeRunning = true;


        for (int i = 1; i <= 2; i++) {
            Vendor vendor = new Vendor(totalNoOfTickets, ticketReleaseRate, ticketPool) {
                @Override
                public void run() {
                    // Keep the thread running until programmeRunning is set to false
                    while (programmeRunning) {
                        super.run();
                    }
                }
            };

            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            runningThreads.add(vendorThread);
            vendorThread.start();
        }


        for (int i = 1; i <= 2; i++) {
            Customer customer = new Customer(ticketPool, customerRetrievalRate, 2) {
                @Override
                public void run() {
                    while (programmeRunning) {
                        super.run();
                    }
                }
            };

            Thread customerThread = new Thread(customer, "Customer-" + i);
            runningThreads.add(customerThread);
            customerThread.start();
        }


        return "Programme started with\n" + "Maximum Tickets: " + maxTicketCapacity + "\n" +
                "Total Number of Tickets: " + totalNoOfTickets + "\n" +
                "Ticket Release Rate: " + ticketReleaseRate + "\n" +
                "Customer Retrieval Rate: " + customerRetrievalRate;
    }

    @PostMapping("/stop")
    public String stopProgramme() {
        programmeRunning = false;
        for (int i=0;i<runningThreads.size();i++) {
            Thread thread = runningThreads.get(i);
            thread.interrupt();
        }

        for (int i=0;i<runningThreads.size();i++) {
            Thread thread = runningThreads.get(i);
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Simulation interrupted while stopping.";
            }
        }
        runningThreads.clear();
        return "Simulation has been stopped.";
    }

}
