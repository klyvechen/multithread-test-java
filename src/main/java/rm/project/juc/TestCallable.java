package rm.project.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *
 * 1. 創建持行線程的方式三：實現Callable接口。相較於實現Runnable接口，方法可以有返回值，並且可以拋出異常
 *
 * 2. Callable方式，需要FutureTask實現類的支持，用於接收運算結果
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadDemoC td = new ThreadDemoC();
        //1. 執行Callable，需要FutureTask實現類的支持，用於接收運算結果
        FutureTask<Long> result = new FutureTask<>(td);
        new Thread(result).start();

        //接收運算結果
        try {
            System.out.println(result.get());
            System.out.println("----------------------------------------------------");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

//class ThreadDemoR implements Runnable {
//    @Override
//    public void run() {
//
//    }
//}

class ThreadDemoC implements Callable<Long> {
    @Override
    public Long call() throws Exception {
        Long sum = 0L;
        for (int i = 0; i < 100000000; i++) {
            sum += i;
        }
        return sum;
    }
}