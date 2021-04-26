package rm.project.juc;

/**
 * 判斷打印 "one" or "two"?
 *
 * 1. 兩個普通方法，兩個線程，標準打印為  one two
 * 2. 新增Thread.sleep()給getOne打印為 one two
 * 3. 新增普通方法給getOne打印為 three one two
 * 4. 兩個普通方法，兩個對象 two one
 * 5. getOne改成static two one
 * 6. getOne getTwo都為靜態static one two
 * 7. 一個靜態一個非靜態，兩個對象 two one
 * 7. 兩個靜態，兩個對象 one two
 *
 * 線程八鎖的關鍵：
 * 1. 非靜態方法的鎖的默認為this，靜態方法的鎖對應的class實例
 * 2. 某一個時刻內，只能有一個線程持有鎖
 */
public class TestThread8Monitor {

    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();
        new Thread(()->{
           number.getOne();
        }).start();
        new Thread(()->{
//            number.getTwo();
            number2.getTwo();
        }).start();
//        new Thread(()->{
//            number.getThree();
//        }).start();
    }
}


class Number {
    public static synchronized void getOne() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}