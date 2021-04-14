package rm.project.multi_thread_p0;


/**
 * 多執行緒物件
 * 死鎖必要條件：
 * 1. 互斥條件：臨界資源是獨占資源, 進程互斥且排他的使用此資源
 * 2. 占有和等待：進程在請求資源時得不到滿足而等待, 不釋放已佔有資源
 * 3. 不可剝奪條件：已獲取的資源只由自己釋放
 * 4. 循環等待條件：當每個進程都在等待鏈中等待下一個進程所持有的資源
 */
public class Foo {

    public Foo() {

    }

    Integer i = 0;
    Object lock = new Object();

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        beforeExecute(0);
        printFirst.run();
        afterExecute();

    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        beforeExecute(1);
        printSecond.run();
        afterExecute();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        beforeExecute(2);
        printThird.run();
    }

    private void beforeExecute(int waitNum) throws InterruptedException {
        while (i < waitNum) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }

    private void afterExecute() throws InterruptedException {
        synchronized (i) {
            i++;
            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }
}