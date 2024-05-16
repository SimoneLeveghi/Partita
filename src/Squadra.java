import java.util.Random;
import java.util.concurrent.Semaphore;

public class Squadra implements Runnable {
    private int nGoal;
    private Semaphore s1;
    private Semaphore s2;
    private Semaphore calciatore;
    private Squadra avversaria;
    private int direzione;

    public Squadra(Semaphore s1, Semaphore s2, Semaphore calciatore) {
        this.s1 = s1;
        this.s2 = s2;
        this.calciatore = calciatore;
    }

    @Override
    public void run() {
        while(this.nGoal < 10 && avversaria.nGoal < 10) {
            compiAzione();
            assegnaPunteggio();
            invertiRuoli();

            s1.release();
            s2.acquireUninterruptibly();
        }
        if(nGoal == 10) {
            System.out.println("Ha vinto la squadra " + Thread.currentThread().getName());
        }
    }

    private void compiAzione() {
        Random r = new Random();
        this.direzione = r.nextInt(2);

        System.out.println("Direzione azione squadra: " + Thread.currentThread().getName() + " " + ((direzione == 0)? "sinistra" : "destra"));
    }

    private void assegnaPunteggio() {
        if(this.direzione != avversaria.direzione) {
            if(calciatore.availablePermits() == 1) {
                nGoal++;
                System.out.println("La squadra " + Thread.currentThread().getName() + " ha segnato");
            }

        }
        else {
            System.out.println("Parata");
        }
    }

    private void invertiRuoli() {
        if(calciatore.availablePermits() == 1) {
            calciatore.acquireUninterruptibly();
        }
        else {
            calciatore.release();
            System.out.println("Calcia la squadra " + Thread.currentThread().getName());
        }
    }

    public int getnGoal() {
        return nGoal;
    }

    public void setAvversaria(Squadra avversaria) {
        this.avversaria = avversaria;
    }


    public void incrementaGoal() {
        nGoal++;
    }
}
