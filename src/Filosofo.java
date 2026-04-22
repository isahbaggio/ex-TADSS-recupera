import java.util.Random;

public class Filosofo extends Thread {
    private final int id;
    private final Garfo esquerdo;
    private final Garfo direito;
    private final boolean usarHierarquia;
    private final int rodadas;
    private final Random random = new Random();

    public Filosofo(int id, Garfo esquerdo, Garfo direito, boolean usarHierarquia, int rodadas) {
        this.id = id;
        this.esquerdo = esquerdo;
        this.direito = direito;
        this.usarHierarquia = usarHierarquia;
        this.rodadas = rodadas;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < rodadas && !isInterrupted(); i++) {
                pensar();
                System.out.println("Filósofo " + id + " está com fome");

                Garfo primeiro = esquerdo;
                Garfo segundo = direito;

                if (usarHierarquia && id == Mesa.N - 1) {
                    primeiro = direito;
                    segundo = esquerdo;
                }

                primeiro.pegar();
                System.out.println("Filósofo " + id + " pegou garfo " + primeiro.getId());

                // pequena pausa para deixar o deadlock mais fácil na versão ingênua
                dormir(120, 220);

                segundo.pegar();
                try {
                    System.out.println("Filósofo " + id + " está comendo");
                    dormir(200, 500);
                } finally {
                    segundo.soltar();
                    primeiro.soltar();
                    System.out.println("Filósofo " + id + " terminou de comer");
                }
            }
        } catch (InterruptedException e) {
            interrupt();
        } finally {
            esquerdo.soltar();
            direito.soltar();
        }
    }

    private void pensar() {
        System.out.println("Filósofo " + id + " está pensando");
        dormir(150, 400);
    }

    private void dormir(int min, int max) {
        int tempo = min + random.nextInt(max - min + 1);
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            interrupt();
        }
    }
}
