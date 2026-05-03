package ocp.chapter2.example0.designpatterns.singleton.part3;

public class FixSingletonCaller {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                Abcd abcd1 = Abcd.getInstance();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Abcd abcd1 = Abcd.getInstance();
            }
        });
        t1.start();
        t2.start();
    }
}

class Abcd{
    private static Abcd instance;
    private Abcd() {
        System.out.println("Abcd instance created");
    }
    public static synchronized Abcd getInstance(){
        if(instance == null){
            instance = new Abcd();
        }
        return instance;
    }
}
/*
* This is a thread-safe lazy Singleton using synchronized.
* It ensures a single instance but suffers from performance overhead
* due to synchronization on every call
* and is still vulnerable to reflection and serialization issues.
* */
