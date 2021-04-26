package rm.project.juc;


import java.util.Random;
import java.util.concurrent.*;

/**
 *
 * 一、線程池：提供了一個線程隊列，隊列中保存著所有等待狀態的線程，避免了創建銷毀開銷，提高了響應速度
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html
 *
 * 二、線程池的體系耶夠：
 *  java.util.concurrent.Executor: 負責線程池，使用與調度的街口
 *      |-- ExecutorService: 子接口：線程池的主要接口。
 *        |-- ThreadPoolExecutor 實現類
 *        |-- ScheduledExecutorService 子接口：負責線程的調度
 *          |-- ScheduledThreadPoolExecutor：繼承ThreadPoolExecutor 實現ScheduledExecutorService
 *
 * 三、工具類
 *  ExecutorService newFixedThreadPool():創建固定大小的線程池
 *  ExecutorService newCacheThreadPool():緩存線程池，線程池的數量不固定，可以根據需求自動更改數量
 *  ExecutorService newSingleThreadPool():線程池中只有一個線程
 *  ScheduledExecutorService newScheduledThreadPool():創建固定大小的線程池，可以延遲或是定時的執行任務
 *
 *
 *  Executors為線程池的工廠方法
 */
public class TestSchduleThreadPool {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);


        for (int i = 0; i < 5; i++) {
            Future<Integer> result = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);

            System.out.println(result.get());
        }

        pool.shutdown();

    }
}
