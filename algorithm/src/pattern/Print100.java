package pattern;

import java.util.Random;

public class Print100 {

    private static final Object lock = new Object();
    private static int number = 0;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number %2 == 0) {
                        int i = new Random().nextInt(100);
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        number++;
                        lock.notify();
                    }else {
                        try {
                            lock.wait();
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (number %2 == 1) {
                        int i = new Random().nextInt(100);
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                        number++;
                        lock.notify();
                    }else {
                        try {
                            lock.wait();
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}
