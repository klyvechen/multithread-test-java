package rm.project.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. 用於解決多線程安全問題的方式
 *
 * synchronized
 * 1) 同步代碼快
 * 2) 同步方法
 *
 * jdk1.5後
 * 3) 同步鎖
 * 注意: 是一個顯示鎖(), 需要通過lock()方法上鎖，必須通過unlock()方法進行釋放 unlock通常放在finally，確保鎖會被釋放
 */
public class TestLock {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket,"1號窗口").start();
        new Thread(ticket,"2號窗口").start();
        new Thread(ticket,"3號窗口").start();

    }
}

class Ticket implements Runnable {
    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            while (tick > 0) {
                try {
                    Thread.sleep(200);
                    System.out.println(Thread.currentThread().getName() + "完成售票，餘票為:" + --tick);
                } catch (InterruptedException e) {

                } finally {

                }
            }
            lock.unlock();
        }
    }
}

//class Ticket implements Runnable {
//    private int tick = 100;
//
//    @Override
//    public void run() {
//        while (tick > 0) {
//            try {
//                Thread.sleep(200);
//                System.out.println(Thread.currentThread().getName()+"完成售票，餘票為:" + --tick);
//            } catch (InterruptedException e) {
//
//            }
//        }
//    }
//}
