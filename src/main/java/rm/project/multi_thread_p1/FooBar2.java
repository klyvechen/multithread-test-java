package rm.project.multi_thread_p1;


/**
 * 多執行緒物件
 * 死鎖必要條件：
 * 1. 互斥條件：臨界資源是獨占資源, 進程互斥且排他的使用此資源
 * 2. 占有和等待：進程在請求資源時得不到滿足而等待, 不釋放已佔有資源
 * 3. 不可剝奪條件：已獲取的資源只由自己釋放
 * 4. 循環等待條件：當每個進程都在等待鏈中等待下一個進程所持有的資源
 */
public class FooBar2 implements FooBar {
    private int n;

    private int j = 0;

    public FooBar2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            while (j % 2 == 1) {
                Thread.yield();
            }
            printFoo.run();
            j++;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printBar.run() outputs "bar". Do not change or remove this line.
            while (j % 2 == 0) {
                Thread.yield();
            }
            printBar.run();
            j++;
        }
    }
}