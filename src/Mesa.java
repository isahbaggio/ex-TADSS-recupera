import java.util.ArrayList;
import java.util.List;

public class Mesa {
    public static final int N = 5;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("===== CENÁRIO 1: solução ingênua =====");
        rodarSimulacao(false, 100, 6000);

        System.out.println();
        System.out.println("===== CENÁRIO 2: hierarquia de recursos =====");
        rodarSimulacao(true, 12, 15000);
    }

    private static void rodarSimulacao(boolean usarHierarquia, int rodadas, long tempoLimiteMs) throws InterruptedException {
        List<Garfo> garfos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            garfos.add(new Garfo(i));
        }

        List<Filosofo> filosofos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Garfo esquerdo = garfos.get(i);
            Garfo direito = garfos.get((i + 1) % N);
            filosofos.add(new Filosofo(i, esquerdo, direito, usarHierarquia, rodadas));
        }

        filosofos.forEach(Thread::start);

        long inicio = System.currentTimeMillis();
        while (System.currentTimeMillis() - inicio < tempoLimiteMs) {
            if (filosofos.stream().noneMatch(Thread::isAlive)) {
                break;
            }
            Thread.sleep(300);
        }

        if (!usarHierarquia) {
            System.out.println("\nSe os filósofos travaram após pegar um garfo cada, isso é o deadlock clássico.");
            System.out.println("(Nesse caso, interrompemos para continuar a demonstração.)\n");
        } else {
            System.out.println("\nExecução com hierarquia finalizada sem impasse geral.\n");
        }

        for (Filosofo f : filosofos) {
            f.interrupt();
        }
        for (Filosofo f : filosofos) {
            f.join(800);
        }
    }
}
