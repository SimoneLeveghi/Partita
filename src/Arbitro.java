import java.util.concurrent.Semaphore;

public class Arbitro {

    public static void main(String[] args) {
        Semaphore s1 = new Semaphore(0);
        Semaphore s2 = new Semaphore(0);
        Semaphore calciatoreS1 = new Semaphore(1);
        Semaphore calciatoreS2 = new Semaphore(0);

        Squadra sq1 = new Squadra(s1, s2, calciatoreS1);
        Squadra sq2 = new Squadra(s2, s1, calciatoreS2);

        sq1.setAvversaria(sq2);
        sq2.setAvversaria(sq1);

        new Thread(sq1).start();
        new Thread(sq2).start();
    }
}