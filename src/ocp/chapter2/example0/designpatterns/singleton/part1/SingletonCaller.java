package ocp.chapter2.example0.designpatterns.singleton.part1;

/*
 * EAGER INITIALIZATION
 */

public class SingletonCaller {
    public static void main(String[] args) {
        Abc abc1 = Abc.getInstance();
        Abc abc2 = Abc.getInstance();
        System.out.println(abc1==abc2);
    }
}

class Abc {
    //Eager initialization creates the object even if it’s never used, which can waste memory and resources.
    public static Abc instance = new Abc();

    private Abc() {}

    public static Abc getInstance() {
        return instance;
    }
}

/*
This Singleton implementation has several drawbacks:
the instance is public so it can be modified,
it is vulnerable to reflection and serialization attacks,
it uses eager initialization which may waste resources,
and it introduces global state making testing and maintenance harder.
*/