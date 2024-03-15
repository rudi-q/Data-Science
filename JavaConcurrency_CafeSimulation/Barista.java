import java.util.concurrent.Semaphore;
public class Barista implements Runnable{

    private String name;
    private boolean isSleeping;
    private String currentOrder;
    private Semaphore machineSemaphore;

    String current_order;
    String machine_using;

    // Statistics
    private static int totalCappuccinosSold = 0;
    private static int totalEspressosSold = 0;
    private static int totalJuicesSold = 0;
    private static int totalSales = 0;

    Barista(String name, Semaphore machineSemaphore){
        this.name = name;
        this.isSleeping = true;
        this.machineSemaphore = machineSemaphore;
    }

    public void wakeUp(){
        System.out.println(name + " is awake.");
        isSleeping = false;
    }
    public void takeOrder(Customer customer) {
        currentOrder = customer.getOrder();
        System.out.println(name + " took an order from " + customer.getName() + ": " + currentOrder);
    }
    public void prepareDrink() {
        try {
            // Acquire the semaphore to simulate using the machine
            machineSemaphore.acquire();

            // Simulate time to prepare the drink
            System.out.println(name + " is preparing " + currentOrder);
            Thread.sleep(1000); // Adjust the sleep time as needed

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Release the semaphore to indicate that the machine is available
            machineSemaphore.release();
        }
    }
    @Override
    public void run() {
        while (true) {
            if (isSleeping) {
                // Barista is sleeping, wait to be awakened
                try {
                    Thread.sleep(500); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // Barista is awake, check for orders and prepare drinks
                if (currentOrder != null) {
                    prepareDrink();
                    currentOrder = null;
                }
            }
        }
    }
    private String getCurrentTime() {
        // Return a string representing the current time (you can use any function for this)
        return "HH:mm:ss";
    }
    // Getters for statistics
    public static int getTotalCappuccinosSold() {
        return totalCappuccinosSold;
    }

    public static int getTotalEspressosSold() {
        return totalEspressosSold;
    }

    public static int getTotalJuicesSold() {
        return totalJuicesSold;
    }

    public static int getTotalSales() {
        return totalSales;
    }

}
