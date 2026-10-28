public class Main {

    private static final int NUM_FILOSOFOS = 5;
    private static final int REFEICOES = 3;

    public static void main(String[] args) throws InterruptedException {
        Garfo[] garfos = new Garfo[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos[i] = new Garfo(i);
        }

        Garcom garcom = new Garcom(NUM_FILOSOFOS);

        Thread[] threads = new Thread[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            Filosofo filosofo = new Filosofo(i, garfos[i], garfos[(i + 1) % NUM_FILOSOFOS], garcom, REFEICOES);
            threads[i] = new Thread(filosofo, "Filosofo-" + i);
        }

        System.out.println("===== Jantar iniciado =====");

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("===== Jantar encerrado =====");
    }
}
