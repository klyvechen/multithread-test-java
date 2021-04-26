package rm.project.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 編寫一個程序開啟三個線程，每個線程為A<B<C，每個線程將自己的ID在屏幕上打印10遍，要求輸出的結果必須按順序顯示
 * 如 ABCABCABCㄡ
 */
public class TestABCAlternate {

    public static void main(String[] args) {
        AlternateDemo ad = new AlternateDemo();
        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ad.loopA(i);
            }
        }, "A").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ad.loopB(i);
            }
        }, "B").start();

        new Thread(()->{
            for (int i = 0; i < 20; i++) {
                ad.loopC(i);
            }
        }, "C").start();
    }

}

class AlternateDemo {
    private int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     *
     * @param totalLoop 循環第幾輪的參數
     */
    public void loopA(int totalLoop) {
        lock.lock();

        try {
            // 1.先判斷這個線程標記是否為1

            if (number != 1) {
                condition1.await();
            }

            // 2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+ "\t" +i +"\t" + totalLoop);
            }

            // 3. 喚醒
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循環第幾輪的參數
     */
    public void loopB(int totalLoop) {
        lock.lock();

        try {
            // 1.先判斷這個線程標記是否為1

            if (number != 2) {
                condition2.await();
            }

            // 2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+ "\t" +i +"\t" + totalLoop);
            }

            // 3. 喚醒
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param totalLoop 循環第幾輪的參數
     */
    public void loopC(int totalLoop) {
        lock.lock();

        try {
            // 1.先判斷這個線程標記是否為1

            if (number != 3) {
                condition3.await();
            }

            // 2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+ "\t" +i +"\t" + totalLoop);
            }
            System.out.println("---------------");

            // 3. 喚醒
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
