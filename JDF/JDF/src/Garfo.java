import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Garfo {

    private final int id;
    private  final Lock lock = new ReentrantLock(true);

    public Garfo(int id){
        this.id = id;
    }


    public void pegar(){
        lock.lock();
    }
    public void soltar(){
        lock.unlock();
    }

    public int getId(){
        return id;
    }





}
