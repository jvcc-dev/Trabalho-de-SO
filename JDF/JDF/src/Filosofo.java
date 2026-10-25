import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;


public class Filosofo implements Runnable {

    // ===== Constantes =====
    private static final int TEMPO_MIN = 1000;  // 1 segundo
    private static final int TEMPO_MAX = 3000;  // 3 segundos
    private static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // ===== Atributos =====
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Garcom garcom;
    private final int totalRefeicoes;


    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito,
                    Garcom garcom, int totalRefeicoes) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
        this.garcom = garcom;
        this.totalRefeicoes = totalRefeicoes;
    }

    // ===== Rotina principal da thread =====
    @Override
    public void run() {
        try {
            for (int refeicao = 1; refeicao <= totalRefeicoes; refeicao++) {
                pensar();
                pegarGarfos();
                try {
                    comer(refeicao);
                } finally {
                    // Garante que garfos e vaga voltam para a mesa,
                    // mesmo se algo der errado durante a refeição
                    devolverGarfos();
                }
            }
            log("TERMINOU de jantar.");
        } catch (InterruptedException e) {
            log("foi INTERROMPIDO.");
            // Restaura o status de interrupção da thread
            Thread.currentThread().interrupt();
        }
    }

    // ===== Ações do filósofo =====

    private void pensar() throws InterruptedException {
        log("está PENSANDO.");
        esperarTempoAleatorio();
    }

    private void pegarGarfos() throws InterruptedException {
        log("está com fome e pediu lugar ao garçom.");
        garcom.pedirPermissao();  // estratégia anti-deadlock: no máximo 4 na disputa

        log("TENTANDO pegar os garfos " + garfoEsquerdo.getId()
                + " e " + garfoDireito.getId() + ".");
        garfoEsquerdo.pegar();
        garfoDireito.pegar();
    }

    private void comer(int refeicao) throws InterruptedException {
        log("está COMENDO com os garfos " + garfoEsquerdo.getId()
                + " e " + garfoDireito.getId()
                + " (refeição " + refeicao + "/" + totalRefeicoes + ").");
        esperarTempoAleatorio();
    }

    private void devolverGarfos() {
        // Solta na ordem inversa à que pegou
        garfoDireito.soltar();
        garfoEsquerdo.soltar();
        garcom.liberarLugar();

        log("DEVOLVEU os garfos " + garfoEsquerdo.getId()
                + " e " + garfoDireito.getId() + ".");
    }



    // Simula uma atividade com duração aleatória entre TEMPO_MIN e TEMPO_MAX
    private void esperarTempoAleatorio() throws InterruptedException {
        int tempo = ThreadLocalRandom.current().nextInt(TEMPO_MIN, TEMPO_MAX + 1);
        Thread.sleep(tempo);
    }

    // Monta a linha completa antes de imprimir, para não misturar
    // mensagens de threads diferentes
    private void log(String mensagem) {
        String hora = LocalTime.now().format(FORMATO_HORA);
        System.out.println("[" + hora + "] Filósofo " + id + " " + mensagem);
    }
}