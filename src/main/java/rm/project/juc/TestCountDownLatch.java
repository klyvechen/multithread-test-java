package rm.project.juc;

import java.util.concurrent.CountDownLatch;

/**
 *
 * CountDownLatch: 閉鎖，在完成某些運算式，只有在其他所有線程的運算全部完成，當前運算才繼續執行
 */
public class TestCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
//            ld.run();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("耗費的時間為:" + (end - start));
    }

}

class LatchDemo implements Runnable {

    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
        latch.countDown();
    }
}
