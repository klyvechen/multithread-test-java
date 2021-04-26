package rm.project.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 生產者和消費者案例 Synchronized方法
 *
 * 1. 一個有等待一個沒有
 * 2. 那兩個生產者 兩個消費者(可能會產生 虛假喚醒)，synchronized總是使用在while中
 */
public class TestProducerAndConsumer {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);
//
        new Thread(new FutureTask<>(producer), "生產者A").start();
        new Thread(new FutureTask<>(consumer), "消費者B").start();

        new Thread(new FutureTask<>(producer), "生產者C").start();
        new Thread(new FutureTask<>(consumer), "消費者D").start();
//        new Thread(producer, "生產者A").start();
//        new Thread(consumer, "消費者B").start();
    }


}


// 店員要維護商品
// 等待喚醒機制
class Clerk {
    private int productNum = 0;

    public synchronized void get() { //循環次數還剩下兩次
        while (productNum >= 1) {
            System.out.println("產品已滿");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
//        } else { //else 去掉可以解決死鎖問題
//            System.out.println(Thread.currentThread().getName() + ":" + ++productNum);
//            this.notifyAll();
        }
        System.out.println(Thread.currentThread().getName() + ":" + ++productNum);
        this.notifyAll();
    }

    public synchronized void sale() { //循環次數還剩下一次
        while (productNum <= 0) { //為了避免虛假喚醒問題，應該總是使用在while循環中才行
            System.out.println("缺貨中");
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
//        } else { //else 去掉可以解決死鎖問題
//            System.out.println(Thread.currentThread().getName() + ":" + --productNum);
//            this.notifyAll();
        }
        System.out.println(Thread.currentThread().getName() + ":" + --productNum);
        this.notifyAll();
    }
}

//class Producer implements Runnable {
//    private Clerk clerk;
//
//    public Producer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
////                Thread.sleep(200);
//            } catch (Exception e) {
//            }
//            clerk.get();
//        }
//    }
//}
//
//class Consumer implements Runnable {
//    private Clerk clerk;
//
//    public Consumer(Clerk clerk) {
//        this.clerk = clerk;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 20; i++) {
//            try {
////                Thread.sleep(200);
//            } catch (Exception e) {
//            }
//            clerk.sale();
//        }
//    }
//}
//
class Producer implements Callable<Void> {
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 0; i < 20; i++) {
            Thread.sleep(200);
            clerk.get();
        }
        return null;
    }
}

class Consumer implements Callable<Void> {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 0; i < 20; i++) {
//            Thread.sleep(200);
            clerk.sale();
        }
        return null;
    }
}