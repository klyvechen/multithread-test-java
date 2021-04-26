package rm.project.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * java.util.concurrent.atomic底下提供了許多原子變量包：
 *     1. volatile保證了內存可見性
 *     2. CAS(compare-And-Swap)算法保證了數據的原子性
 *        CAS算法是硬件對於並發操作共享數據的支持
 *        包含了三個操作數：
 *        內存值 V
 *        預估值 A
 *        更新值 B
 *        且當V == A時, V = B, 否則什麼操作都不做
 *
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

    private volatile int serialNumber= 0;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber++;
    }


}

class AtomicDemo2 implements Runnable {

    private volatile AtomicInteger serialNumber= new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.incrementAndGet();
    }

}
