package hu.kecskesk.domain;

/**
 * LP ertekeles szoveg és boolean
 *
 * Created by kkrisz on 2017.01.05..
 */
public class LPErtekeles {
    public String ertekeles;
    public Double x1;
    public Double xMin;
    public Double xMax;
    public boolean sikeres;

    public LPErtekeles() {
    }

    public LPErtekeles(String ertekeles, boolean sikeres) {
        this.ertekeles = ertekeles;
        this.sikeres = sikeres;
        x1 = null;

        if (sikeres) {
            System.out.println("WARNING: sikeres, de nem tudjuk mennyivel??");
        }
    }

    public LPErtekeles(String ertekeles, Double x1) {
        this.ertekeles = ertekeles;
        this.x1 = x1;
        sikeres = true;
    }

    public LPErtekeles(String ertekeles, Double x1, Double xMin, Double xMax) {
        this.ertekeles = ertekeles;
        this.x1 = x1;
        this.xMin = xMin;
        this.xMax = xMax;
        sikeres = true;
    }

    @Override
    public String toString() {
        return "\nLPErtekeles{" +
                "ertekeles='" + ertekeles + '\'' +
                ", yb=" + x1 +
                ", sikeres=" + sikeres +
                '}';
    }
}
