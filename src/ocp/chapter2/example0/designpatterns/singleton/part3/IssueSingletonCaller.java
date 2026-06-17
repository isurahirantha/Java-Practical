package ocp.chapter2.example0.designpatterns.singleton.part3;

/*
 * LAZY INITIALIZATION
 * issue - with two thread, this create two instances. violate singleton principle.
 */

public class IssueSingletonCaller {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                Abc abc1 = Abc.getInstance();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Abc abc1 = Abc.getInstance();
            }
        });

        t1.start();
        t2.start();
    }
}

class Abc{

    private static Abc instance;

    private Abc() {
        System.out.println("Abc instance created");
    }

    public static Abc getInstance(){
        if(instance == null){
            instance = new Abc();
        }
        return instance;
    }

}
