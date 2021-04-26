package rm.project.juc;

/**
 * 一 volatile 關鍵字：當多個線程進行操作共享數據時，可以保證內存中的數據可見，
 *                  相較於synchronized是一種叫輕量級的同步策略
 * 注意：
 * 1. volatile 不具被互斥性
 * 2. volatile 不能保證變量的原子性
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while (true) {
            if (td.isFlag()) {
                System.out.println("----------");
                break;
            }
        }
    }
}


 class ThreadDemo implements Runnable {
    private boolean flag = false;

    public void run() {
        try {
            Thread.sleep(200);
            flag = true;
            System.out.println("flag: " + flag);
        } catch (Exception e) {

        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = false;
    }
 }