import java.util.concurrent.Semaphore;
import java.util.Random;

public class Cafe implements Runnable {

    private Semaphore tableSemaphore = new Semaphore(5);
    private Barista barista1 = new Barista("Barista 1", tableSemaphore);
    private Barista barista2 = new Barista("Barista 2", tableSemaphore);
    private Barista barista3 = new Barista("Barista 3", tableSemaphore);

    public void init() {
        // Initialize any necessary components or parameters here
    }
    private static boolean simulationRunning = true;
    // Flag to control simulation termination

    public void stopSimulation() {
        simulationRunning = false;
    }

    public static void main(String[] args) {
        Cafe cafe = new Cafe();
        cafe.init();
        cafe.run();
    }

    public void run() {

        // Create and start barista threads
        Thread baristaThread1 = new Thread(barista1);
        Thread baristaThread2 = new Thread(barista2);
        Thread baristaThread3 = new Thread(barista3);


        baristaThread1.start();
        baristaThread2.start();
        baristaThread3.start();

        // Simulate customer arrivals
        for (int i = 1; i <= 20; i++) {
            Customer customer = new Customer("Customer " + i, tableSemaphore);
            Thread customerThread = new Thread(customer);
            customerThread.start();

            // Introduce a delay between customer arrivals
            try {
                Thread.sleep(new Random().nextInt(3) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all barista and customer threads to finish
        try {
            baristaThread1.join();
            baristaThread2.join();
            baristaThread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Report end-of-day statistics
        System.out.println("End of the day statistics:");
        System.out.println("Total number of Cappuccinos sold: " + Barista.getTotalCappuccinosSold());
        System.out.println("Total number of Espressos sold: " + Barista.getTotalEspressosSold());
        System.out.println("Total number of Juices sold: " + Barista.getTotalJuicesSold());
        System.out.println("Total sales: RM" + Barista.getTotalSales());

        // Stop the simulation by setting the flag
        stopSimulation();
    }
}