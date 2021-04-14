package rm.project.multi_thread_p1;


class Thread0 extends Thread {

    private final String name;

    private FooBar FooBar;

    private final Runnable r;

    public Thread0(FooBar FooBar, Runnable r) {
        this.name = "first";
        this.FooBar = FooBar;
        this.r = r;
    }

    public void run() {
        try {
            FooBar.foo(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalMonitorStateException e2) {
            e2.printStackTrace();
        }

    }
}

class Thread1 extends Thread {

    private final String name;

    private FooBar FooBar;

    private final Runnable r;

    public Thread1(FooBar FooBar, Runnable r) {
        this.name = "second";
        this.FooBar = FooBar;
        this.r = r;
    }

    public void run() {
        try {
            FooBar.bar(r);
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


        Runnable r1 = new Execute("foo");
        Runnable r2 = new Execute("bar");

        FooBar FooBar = new FooBar2(Integer.parseInt(args[0]));

        Thread0 thread1 = new Thread0(FooBar, r1);
        Thread1 thread2 = new Thread1(FooBar, r2);

        thread1.start();
        thread2.start();
    }

}
