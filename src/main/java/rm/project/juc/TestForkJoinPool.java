package rm.project.juc;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

    //1775
    public static void main(String[] args) {
        Instant start1 = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 1000000000L);

        Instant start2 = Instant.now();
        long sum = pool.invoke(task);
        Instant end = Instant.now();
        System.out.println("Sum: " + sum);
        System.out.println("所耗費時間為" + Duration.between(start1, end).toMillis());
        System.out.println("所耗費時間為" + Duration.between(start2, end).toMillis());

    }


    //1011
    @Test
    public void test1() {
        Instant start = Instant.now();
        long sum = 0L;

        for (long i = 0; i < 1000000000L; i++) {
            sum +=i;
        }
        System.out.println("Sum: " + sum);

        Instant end = Instant.now();

        System.out.println("所耗費時間為" + Duration.between(start, end).toMillis());
    }

    //435
    @Test
    public void test2() {
        Instant start = Instant.now();
        Long sum = LongStream.range(0L, 1000000000L)
                .parallel()
                .reduce(0L, Long::sum);
        System.out.println("Sum: " + sum);

        Instant end = Instant.now();

        System.out.println("所耗費時間為" + Duration.between(start, end).toMillis());
    }

}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = -6789765896578965L;

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L; //臨界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length < THRESHOLD) {
            long sum = 0L;

            for (long i = start; i < end; i++) {
                sum +=i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle, end);
            right.fork();
            return left.compute() + right.compute();
        }
    }


}