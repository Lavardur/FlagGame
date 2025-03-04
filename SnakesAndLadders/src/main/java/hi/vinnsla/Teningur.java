package hi.vinnsla;

import java.util.Random;

public class Teningur {
    private final Random random = new Random();
    private int nuverandiTala;

    public int kasta() {
        nuverandiTala = random.nextInt(6) + 1;
        return nuverandiTala;
    }

    public int getNuverandiTala() {
        return nuverandiTala;
    }
}
