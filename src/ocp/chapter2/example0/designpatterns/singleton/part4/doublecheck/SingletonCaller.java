package ocp.chapter2.example0.designpatterns.singleton.part4.doublecheck;

public class SingletonCaller {
    public static void main(String[] args) {
        // Create a new thread (t2) that will try to get the Singleton instance
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Abc abc1 = Abc.getInstance(); // Calls the Singleton accessor
            }
        });

        // Create another thread (t3) that will also try to get the Singleton instance
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                Abc abc2 = Abc.getInstance(); // Calls the Singleton accessor
            }
        });

        // Start both threads. They run concurrently and both attempt to get the Singleton.
        t2.start();
        t3.start();
    }
}

class Abc {
    // Holds the single instance of Abc.
    // 'volatile' ensures visibility across threads (no stale cached copies).
    private static volatile Abc instance;

    // Private constructor prevents direct instantiation from outside.
    // Prints a message when the instance is created (helps demonstrate that only one is made).
    private Abc() {
        System.out.println("Abc instance created");
    }

    // Public accessor method to get the Singleton instance.
    // Implements "double-checked locking" to ensure thread-safe lazy initialization.
    public static Abc getInstance() {
        // First check: if instance is not yet created
        if (instance == null) {
            // Synchronize on the class object to ensure only one thread can enter at a time
            synchronized (Abc.class) {
                // Second check: still null? (important because multiple threads may have passed the first check)
                if (instance == null) {
                    // Create the single instance
                    instance = new Abc();
                }
            }
        }
        // Return the single shared instance
        return instance;
    }
}
