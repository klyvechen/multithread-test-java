package rm.project.multi_thread_p2;


/**
 * 多執行緒物件
 * 死鎖必要條件：
 * 1. 互斥條件：臨界資源是獨占資源, 進程互斥且排他的使用此資源
 * 2. 占有和等待：進程在請求資源時得不到滿足而等待, 不釋放已佔有資源
 * 3. 不可剝奪條件：已獲取的資源只由自己釋放
 * 4. 循環等待條件：當每個進程都在等待鏈中等待下一個進程所持有的資源
 */
interface ZeroEvenOdd {

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException;

    public void even(IntConsumer printNumber) throws InterruptedException;

    public void odd(IntConsumer printNumber) throws InterruptedException;

}