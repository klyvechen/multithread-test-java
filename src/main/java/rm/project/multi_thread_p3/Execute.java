package rm.project.multi_thread_p3;


import rm.project.NotifyingThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Execute implements Runnable {

    private String name;

    public Execute(String name) {
        this.name = name;
    }

    public void run() {
        System.out.print(this.name);
    }

    public static void main(String[] args) {

        SynContainer container = new SynContainer1(10);

        SynContainer container2 = new SynContainer2(10);

        List<NotifyingThread> threadList = new ArrayList<>();
        threadList.add(new NotifyingThread(new Producer(container)));
        threadList.add(new NotifyingThread(new Consumer(container)));
        threadList.add(new NotifyingThread(new Producer(container)));
        threadList.add(new NotifyingThread(new Consumer(container)));

        for (NotifyingThread thread : threadList) {
            thread.addListener((t)->{
                t.endTime = new Date().getTime();
                System.out.println(t.threadName + " spend time " + (t.endTime - t.startTime));
            });
        }

        for (NotifyingThread thread : threadList) {
            thread.start();
        }

    }

}

class Producer implements Runnable {

    private final SynContainer container;


    public Producer(SynContainer container) {
        this.container = container;
    }

    public void run() {
        for (int i = 1; i <= 1000; i++) {
            Chicken c = new Chicken(i);
            this.container.push(c);
            System.out.println(Thread.currentThread().getName() + ": 賣雞的：做了 " + c.id + "的雞, 快來囉");
        }
    }
}

class Consumer implements Runnable {

    private final SynContainer container;

    public Consumer(SynContainer container) {
        this.container = container;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            Chicken c = this.container.pop();
            System.out.println(Thread.currentThread().getName() + ": 餓鬼：我要吃 " + c.id + "雞囉");
        }
    }

}

class Chicken {

    public Chicken(int i) {
        this.id = i;
    }

    public int id;

}

interface SynContainer {
    void push(Chicken c);
    public Chicken pop();
}

class SynContainer1 implements SynContainer {


    private final int maxNum;

    private final Chicken[] chickens;

    private final Chicken fake = new Chicken(-1);

    private volatile int count = 0;

    private Object maxLock = new Object();
    private Object minLock = new Object();

    public SynContainer1(int maxNum) {
        this.maxNum = maxNum;
        this.chickens = new Chicken[maxNum];
    }


    public void push(Chicken c) {
        if (maxNum == count) {
            synchronized (maxLock) {
                try {
                    maxLock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chickens[count] = c;
        count++;
        synchronized (minLock) {
            minLock.notifyAll();
        }
    }

    public Chicken pop() {
        Chicken c;
        if (count == 0) {
            synchronized (minLock) {
                try {
                    minLock.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        c = chickens[count - 1];
        count--;
        synchronized (maxLock) {
            maxLock.notifyAll();
        }
        return c;
    }

}

class SynContainer2 implements SynContainer {


    private final int maxNum;

    private final Chicken[] chickens;

    private final Chicken fake = new Chicken(-1);

    private volatile int count = 0;


    public SynContainer2(int maxNum) {
        this.maxNum = maxNum;
        this.chickens = new Chicken[maxNum];
    }


    public synchronized void push(Chicken c) {
        while (maxNum == count) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        chickens[count] = c;
        count++;
        this.notifyAll();
    }

    public synchronized Chicken pop() {
        Chicken c;
        while (count == 0) {
            try {
                this.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        c = chickens[count - 1];
        count--;
        this.notifyAll();
        return c;
    }

}
