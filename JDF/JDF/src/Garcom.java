import java.util.concurrent.Semaphore;

public class Garcom {


    private final Semaphore vagas;

    public Garcom(int totalDeFilosofos){
        this.vagas=new Semaphore(totalDeFilosofos - 1,true);
    }

    public void pedirPermissao() throws InterruptedException {
        vagas.acquire();
    }
    public void liberarLugar(){
        vagas.release();
    }

}
