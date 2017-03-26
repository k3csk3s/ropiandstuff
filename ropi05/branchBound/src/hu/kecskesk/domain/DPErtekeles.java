package hu.kecskesk.domain;

/**
 * LP ertekeles szoveg és boolean
 *
 * Created by kkrisz on 2017.01.05..
 */
public class DPErtekeles {
    public String ertekeles;
    public Double yb;
    public Double x1;
    public boolean sikeres;

    public DPErtekeles() {
    }

    public DPErtekeles(LPErtekeles lpErtekeles) {
        this.ertekeles = lpErtekeles.ertekeles;
        yb = null;
        x1 = null;
        sikeres = lpErtekeles.sikeres;
    }

    @Override
    public String toString() {
        return "DPErtekeles{" +
                "ertekeles='" + ertekeles + '\'' +
                ", yb=" + yb +
                ", x1=" + x1 +
                ", sikeres=" + sikeres +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DPErtekeles that = (DPErtekeles) o;

        if (sikeres != that.sikeres) return false;
        if (ertekeles != null ? !ertekeles.equals(that.ertekeles) : that.ertekeles != null) return false;
        if (yb != null ? !yb.equals(that.yb) : that.yb != null) return false;
        return x1 != null ? x1.equals(that.x1) : that.x1 == null;
    }

    @Override
    public int hashCode() {
        int result = ertekeles != null ? ertekeles.hashCode() : 0;
        result = 31 * result + (yb != null ? yb.hashCode() : 0);
        result = 31 * result + (x1 != null ? x1.hashCode() : 0);
        result = 31 * result + (sikeres ? 1 : 0);
        return result;
    }
}
