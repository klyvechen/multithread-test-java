package rm.project.multi_thread_p1;


/**
 * 多執行緒物件
 * 死鎖必要條件：
 * 1. 互斥條件：臨界資源是獨占資源, 進程互斥且排他的使用此資源
 * 2. 占有和等待：進程在請求資源時得不到滿足而等待, 不釋放已佔有資源
 * 3. 不可剝奪條件：已獲取的資源只由自己釋放
 * 4. 循環等待條件：當每個進程都在等待鏈中等待下一個進程所持有的資源
 */
public class FooBar1 implements FooBar {
    private int n;

    private Integer j = 0;

    public FooBar1(int n) {
        this.n = n;
    }

    Object fooWait = new Object();
    Object barWait = new Object();

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            synchronized(fooWait) {
                while ((j % 2) == 1) {
                    fooWait.wait();
                }
            }
            printFoo.run();
            addJ();

            synchronized(barWait) {
                barWait.notify();
            }
            synchronized(fooWait) {
                if (i == n-1) {
                    break;
                }
                while ((j % 2) == 1) {
                    fooWait.wait();
                }
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printBar.run() outputs "bar". Do not change or remove this line.
            synchronized(barWait) {
                while ((j % 2) == 0) {
                    barWait.wait();
                }
            }
            printBar.run();
            addJ();
            synchronized(fooWait) {
                fooWait.notify();
            }
            synchronized(barWait) {
                if (i == n-1) {
                    break;
                }
                while ((j % 2) == 0) {
                    barWait.wait();
                }
            }
        }
    }

    private void addJ() {
        synchronized(j) {
            j++;
        }
    }
}