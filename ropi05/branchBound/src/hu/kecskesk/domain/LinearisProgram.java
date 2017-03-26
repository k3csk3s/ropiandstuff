package hu.kecskesk.domain;

import hu.kecskesk.helper.MatrixUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Linearis program POJO
 *
 * Created by kkrisz on 2017.01.04..
 */
public class LinearisProgram {
    public int n;
    public int m;

    public List<List<Double>> A;
    public List<Double> x;
    public List<Double> b;
    public List<Double> c;

    public LinearisProgram(int n, int m) {
        this.n = n;
        this.m = m;

        A = new ArrayList<>();
        x = new ArrayList<>();
        b = new ArrayList<>();
        c = new ArrayList<>();
    }

    public LinearisProgram(LinearisProgram original) {
        this.n = original.n;
        this.m = original.m;
        A = new ArrayList<>();
        x = new ArrayList<>();
        b = new ArrayList<>();
        c = new ArrayList<>();

        for (List<Double> row : original.A) {
            A.add(new ArrayList<>(row));
        }

        x.addAll(original.x);
        b.addAll(original.b);
        c.addAll(original.c);
    }

    public LinearisProgram(int n, int m, List<List<Double>> a, List<Double> b) {
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

        if (n != that.n) return false;
        if (m != that.m) return false;
        if (A != null ? !A.equals(that.A) : that.A != null) return false;
        if (x != null ? !x.equals(that.x) : that.x != null) return false;
        if (b != null ? !b.equals(that.b) : that.b != null) return false;
        return c != null ? c.equals(that.c) : that.c == null;
    }

    @Override
    public int hashCode() {
        int result = n;
        result = 31 * result + m;
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
                ", A=" + MatrixUtility.draw(A) +
                ", x=" + x +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
