package rm.project.multi_thread_p2;


class Thread0 extends Thread {


    private ZeroEvenOdd zeroEvenOdd;

    private final IntConsumer intConsumer;

    public Thread0(ZeroEvenOdd zeroEvenOdd, IntConsumer intConsumer) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.intConsumer = intConsumer;
    }

    public void run() {
        try {
            zeroEvenOdd.zero(intConsumer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalMonitorStateException e2) {
            e2.printStackTrace();
        }

    }
}

class Thread1 extends Thread {


    private ZeroEvenOdd zeroEvenOdd;

    private final IntConsumer intConsumer;

    public Thread1(ZeroEvenOdd zeroEvenOdd, IntConsumer intConsumer) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.intConsumer = intConsumer;
    }

    public void run() {
        try {
            zeroEvenOdd.odd(intConsumer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class Thread2 extends Thread {


    private ZeroEvenOdd zeroEvenOdd;

    private final IntConsumer intConsumer;

    public Thread2(ZeroEvenOdd zeroEvenOdd, IntConsumer intConsumer) {
        this.zeroEvenOdd = zeroEvenOdd;
        this.intConsumer = intConsumer;
    }

    public void run() {
        try {
            zeroEvenOdd.even(intConsumer);
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


        int i = Integer.parseInt(args[0]);

        IntConsumer intConsumer0 = new IntConsumer(0);
        IntConsumer intConsumer1 = new IntConsumer(1);
        IntConsumer intConsumer2 = new IntConsumer(2);

        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd1(i);

        Thread0 thread0 = new Thread0(zeroEvenOdd, intConsumer0);
        Thread1 thread1 = new Thread1(zeroEvenOdd, intConsumer1);
        Thread2 thread2 = new Thread2(zeroEvenOdd, intConsumer2);

        thread0.start();
        thread1.start();
        thread2.start();

        System.out.println();

        ZeroEvenOdd zeroEvenOdd2 = new ZeroEvenOdd2(i);

        Thread0 thread3 = new Thread0(zeroEvenOdd2, intConsumer0);
        Thread1 thread4 = new Thread1(zeroEvenOdd2, intConsumer1);
        Thread2 thread5 = new Thread2(zeroEvenOdd2, intConsumer2);

        thread3.start();
        thread4.start();
        thread5.start();
    }

}
