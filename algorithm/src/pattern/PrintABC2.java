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
                if (number %3 == 0) {
                    System.out.println("0-A");
                    number++;
                }
                conditionB.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void b() {
        while (true) {
            lock.lock();
            try {
                if (number %3 == 1) {
                    System.out.println("1-B");
                    number++;
                }
                conditionC.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void c() {
        while (true) {
            lock.lock();
            try {
                if (number %3 == 2) {
                    System.out.println("2-C");
                    number++;
                }
                conditionA.signal();
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
