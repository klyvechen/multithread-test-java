package rm.project.multi_thread_p2;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多執行緒物件
 * 死鎖必要條件：
 * 1. 互斥條件：臨界資源是獨占資源, 進程互斥且排他的使用此資源
 * 2. 占有和等待：進程在請求資源時得不到滿足而等待, 不釋放已佔有資源
 * 3. 不可剝奪條件：已獲取的資源只由自己釋放
 * 4. 循環等待條件：當每個進程都在等待鏈中等待下一個進程所持有的資源
 */
class ZeroEvenOdd2 implements ZeroEvenOdd{
    private int n;
    private static AtomicBoolean flag ;
    private AtomicInteger count ;
    private Object pLock;
    private Object cLock;
    private volatile CountDownLatch start = new CountDownLatch(1);

    public ZeroEvenOdd2(int n) {
        pLock = new Object();
        cLock = new Object();
        this.count = new AtomicInteger(1);
        this.flag = new AtomicBoolean(false);
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {

        for( int i = 1;i<=n;i++) {

            synchronized(pLock) {
                printNumber.accept(0);

                while(!flag.get()){
                    start.countDown();
                    pLock.wait();

                }
                count.getAndIncrement();


                flag.set(false);
                synchronized(cLock){
                    cLock.notifyAll();
                }

            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {

        start.await();
        synchronized(cLock){
            for(int i = 1;i<=n/2;i++) {

                while(flag.get() || count.get()%2 != 0) {

                    cLock.wait();

                }
                printNumber.accept(count.get());

                flag.set(true);

                synchronized(pLock){
                    pLock.notifyAll();
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        start.await();
        int loop = (n%2 == 0)? (n/2): (n/2)+1;
        synchronized(cLock){


            for( int i = 1;i<=loop;i++) {
                while(flag.get() || count.get()%2 == 0){
                    cLock.wait();

                }
                printNumber.accept(count.get());
                flag.set(true);
                synchronized(pLock){
                    pLock.notifyAll();
                }
            }
        }
    }

}