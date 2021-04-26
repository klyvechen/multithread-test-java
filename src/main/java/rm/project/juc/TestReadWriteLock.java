package rm.project.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 讀寫鎖算是一種樂觀鎖
 * 1. RaadWriteLock: 讀寫鎖
 * 寫寫互斥 / 讀寫互斥 / 讀讀
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReadWriteLock.html
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();
        new Thread(()->{
            demo.set((int) (Math.random() * 101));
        }, "Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                demo.get();
            }, "Read:").start();
        }


    }
}

class ReadWriteLockDemo {

    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.readLock().unlock();
        }

    }

    public void set(int number) {
        lock.writeLock().lock();
        try {
            this.number = number;
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.writeLock().unlock();
        }
    }

}