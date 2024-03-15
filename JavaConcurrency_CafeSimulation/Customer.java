import java.util.concurrent.Semaphore;
import java.util.Random;
public class Customer implements Runnable{
    private String name;
    private String order;
    private boolean isWaiting;
    private Semaphore tableSemaphore;
    int customerId, arrivalTime, orderType;
    boolean seated;
    public Customer(String name, Semaphore tableSemaphore) {
        this.name = name;
        this.order = generateRandomOrder();
        this.isWaiting = true;
        this.tableSemaphore = tableSemaphore;
    }
    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public void enterCafe() {
        System.out.println(name + " enters the cafe.");
    }

    public void makeOrder(Barista barista) {
        barista.takeOrder(this);
    }

    public void waitForTable() {
        try {
            // Acquire the semaphore to simulate waiting for a table
            tableSemaphore.acquire();
            System.out.println(name + " is waiting for a table.");
            Thread.sleep(1000); // Adjust the sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Release the semaphore to indicate that the table is occupied
            tableSemaphore.release();
        }
    }

    public void drink() {
        System.out.println(name + " is enjoying their drink.");
    }
    @Override
    public void run(){
        enterCafe();
        waitForTable();

        // Simulate ordering from the barista
        Barista barista = new Barista("Barista",tableSemaphore);
        makeOrder(barista);

        // Simulate the barista preparing the drink
        barista.prepareDrink();

        // Simulate the customer drinking
        drink();

        // Customer leaves the cafe
        System.out.println(name + " leaves the cafe.");
    }
    private String getCurrentTime() {
        // Return a string representing the current time (you can use any function for this)
        return "HH:mm:ss";
    }

    private String generateRandomOrder() {
        // Simulate different drink orders based on your specified probabilities
        double rand = Math.random();
        if (rand < 0.7) {
            return "Cappuccino";
        } else if (rand < 0.9) {
            return "Espresso";
        } else {
            return "Juice";
        }
    }
}
