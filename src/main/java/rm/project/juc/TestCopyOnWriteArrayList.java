package rm.project.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArray(寫入並複製)
 * 添加操作多時，效率低，因為每次添加時都會進行複製，開銷非常的大
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        HelloThread ht = new HelloThread();
        for (int i = 0; i < 10 ; i++) {
            new Thread(ht).start();
        }
    }
}

class HelloThread implements Runnable {
//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(); // 適合在迭代時拿來使用

    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
    }
    @Override
    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("aa");
        }
    }
}