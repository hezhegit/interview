package pattern;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC2 {
    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();
    private int number = 0;
    public void a() {
        while (true) {
            lock.lock();
            try {
                while (number %3 != 0){
                    conditionA.await();;
                }
                System.out.println("0-A");
                number++;
                conditionB.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void b() {
        while (true) {
            lock.lock();

            try {
                while (number %3 != 1){
                    conditionB.await();;
                }
                System.out.println("1-B");
                number++;
                conditionC.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void c() {
        while (true) {
            lock.lock();
            try {
                while (number %3 != 2){
                    conditionC.await();;
                }
                System.out.println("2-C");
                number++;
                conditionA.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintABC2 printABC2 = new PrintABC2();
        Thread t1 = new Thread(printABC2::a);
        Thread t2 = new Thread(printABC2::b);
        Thread t3 = new Thread(printABC2::c);
        t1.start();
        t2.start();
        t3.start();
    }
}
