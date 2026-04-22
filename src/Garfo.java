import java.util.concurrent.locks.ReentrantLock;

public class Garfo {
    private final int id;
    private final ReentrantLock lock = new ReentrantLock();

    public Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void pegar() throws InterruptedException {
        lock.lockInterruptibly();
    }

    public void soltar() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
