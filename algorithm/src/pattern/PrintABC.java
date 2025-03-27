package pattern;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC {
    private static final Object lock = new Object();
    private static int number = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number %3 == 0) {
                        System.out.println(Thread.currentThread().getName()+" "+ "A");
                        number++;
                        lock.notify();
                    }else {
                        lock.notifyAll();
                    }
                }
            }

        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number %3 == 1) {
                        System.out.println(Thread.currentThread().getName()+" "+ "B");
                        number++;
                        lock.notifyAll();
                    }else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        });

        Thread t3 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number %3 == 2) {
                        System.out.println(Thread.currentThread().getName()+" "+ "C");
                        number++;
                        lock.notifyAll();
                    }else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        });

        t1.start();
        t2.start();
        t3.start();
    }
}
