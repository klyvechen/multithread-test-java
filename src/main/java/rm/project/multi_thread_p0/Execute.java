package rm.project.multi_thread_p0;


class Thread0 extends Thread {

    private final String name;

    private Foo foo;

    private final Runnable r;

    public Thread0(Foo foo, Runnable r) {
        this.name = "first";
        this.foo = foo;
        this.r = r;
    }

    public void run() {
        try {
            foo.first(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalMonitorStateException e2) {
            e2.printStackTrace();
        }

    }
}

class Thread1 extends Thread {

    private final String name;

    private Foo foo;

    private final Runnable r;

    public Thread1(Foo foo, Runnable r) {
        this.name = "second";
        this.foo = foo;
        this.r = r;
    }

    public void run() {
        try {
            foo.second(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Thread2 extends Thread {

    private final String name;

    private Foo foo;

    private final Runnable r;

    public Thread2(Foo foo, Runnable r) {
        this.name = "third";
        this.foo = foo;
        this.r = r;
    }

    public void run() {
        try {
            foo.third(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


public class Execute implements Runnable{

    private String name;

    public Execute(String name) {
        this.name = name;
    }

    public void run() {
        System.out.print(this.name);
    }

    public static void main(String[] args) {


        Runnable r1 = new Execute("first");
        Runnable r2 = new Execute("second");
        Runnable r3 = new Execute("third");

        Foo foo = new Foo();

        Thread0 thread1 = new Thread0(foo, r1);
        Thread1 thread2 = new Thread1(foo, r2);
        Thread2 thread3 = new Thread2(foo, r3);

        thread1.start();
        thread2.start();
        thread3.start();
    }

}
