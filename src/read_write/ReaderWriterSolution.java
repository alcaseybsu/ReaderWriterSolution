package read_write;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
In Java, you can use a ReadWriteLock, which allows multiple readers 
to access a resource at the same time. Only one writer at a 
time can access it, and only when no readers are accessing it.
This program reads the file first, then writes to it, then 
reads it again. The output should be: 

"Dog
Cat
Bird
Dog
Cat
Bird
Fish"

*/

public class ReaderWriterSolution {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private List<String> data = new ArrayList<>();

    public ReaderWriterSolution() {
        data.add("Dog");
        data.add("Cat");
        data.add("Bird");
    }

    public static void main(String[] args) {
        final ReaderWriterSolution rw = new ReaderWriterSolution();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                rw.read();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                rw.write();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                rw.read();
            }
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
    }

    public void read() {
        rwLock.readLock().lock();
        try {
            for (String animal : data) {
                System.out.println(animal);
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void write() {
        rwLock.writeLock().lock();
        try {
            data.add("Fish");
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
