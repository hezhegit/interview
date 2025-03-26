package pattern;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {

    private int state = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition conditionA = lock.newCondition();
    private final Condition conditionB = lock.newCondition();
    private final Condition conditionC = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            while (state %3 != 0) conditionA.await(); // 进入等待
            System.out.println("A");
            state++;
            conditionB.signal(); // 唤醒B
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (state % 3 != 1) conditionB.await();
            System.out.println("B");
            state++;
            conditionC.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
    public void printC() {
        lock.lock();
        try {
            while (state % 3 != 2) conditionC.await();
            System.out.println("C");
            state++;
            conditionA.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();
        new Thread(printABC::printA).start();
        new Thread(() -> {
            while (true) printABC.printB();
        }).start();
        new Thread(printABC::printC).start();
    }
}
