package hu.kecskesk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kkrisz on 2017.01.04..
 */
public class LinearisProgram {
    public Integer n;
    public Integer m;

    public List<List<Double>> A;
    public List<Double> x;
    public List<Double> b;
    public List<Double> c;

    public LinearisProgram(Integer n, Integer m) {
        this.n = n;
        this.m = m;

        A = new ArrayList<>();
        x = new ArrayList<>();
        b = new ArrayList<>();
        c = new ArrayList<>();
    }

    public LinearisProgram(Integer n, Integer m, List<List<Double>> a, List<Double> b) {
        this.n = n;
        this.m = m;
        A = a;
        this.b = b;

        x = new ArrayList<>();
        c = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinearisProgram that = (LinearisProgram) o;

        if (n != null ? !n.equals(that.n) : that.n != null) return false;
        if (m != null ? !m.equals(that.m) : that.m != null) return false;
        if (A != null ? !A.equals(that.A) : that.A != null) return false;
        if (x != null ? !x.equals(that.x) : that.x != null) return false;
        if (b != null ? !b.equals(that.b) : that.b != null) return false;
        return c != null ? c.equals(that.c) : that.c == null;
    }

    @Override
    public int hashCode() {
        int result = n != null ? n.hashCode() : 0;
        result = 31 * result + (m != null ? m.hashCode() : 0);
        result = 31 * result + (A != null ? A.hashCode() : 0);
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LinearisProgram{" +
                "n=" + n +
                ", m=" + m +
                ", At=" + drawA() +
                ", y=" + x +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    private String drawA() {
        String drawnA = "\n";
        for (List<Double> row : A) {
            drawnA += row + "\n";
        }

        return drawnA;
    }
}
