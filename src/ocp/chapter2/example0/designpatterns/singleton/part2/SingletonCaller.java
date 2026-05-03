package ocp.chapter2.example0.designpatterns.singleton.part2;

/*
 * LAZY INITIALIZATION
 */

public class SingletonCaller {
    public static void main(String[] args) {
        Abc abc1 = Abc.getInstance();
        System.out.println(abc1);
        Abc abc2 = Abc.getInstance();
        System.out.println(abc2);
        System.out.println(abc1 == abc2);
    }
}

class Abc {
    private static Abc instance;
    private Abc() {}
    public static Abc getInstance() {
        if (instance == null) {
            instance = new Abc();
        }
        return instance;
    }
}

/*
    Problem	        What happens	                Result
    Thread safety	Multiple threads create objects	❌ Multiple instances
    Reflection	    Private constructor bypassed	❌ Multiple instances
    Serialization	Object recreated from file	    ❌ Multiple instances
 */