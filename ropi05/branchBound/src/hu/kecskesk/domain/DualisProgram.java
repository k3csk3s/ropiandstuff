package hu.kecskesk.domain;

import hu.kecskesk.helper.MatrixUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Dualis program objektum
 *
 * Created by kkrisz on 2017.01.05..
 */
public class DualisProgram extends LinearisProgram {
    
    public int idx = 0;

    public DualisProgram(int n, int m) {
        super(n, m);
    }

    public DualisProgram(int n, int m, List<List<Double>> a, List<Double> b) {
        super(n, m, a, b);
    }

    public DualisProgram(LinearisProgram lp, boolean yPozitiv) {
        super(lp.m, lp.n);
        A = MatrixUtility.transpose(lp.A);
        b = new ArrayList<>();
        b.addAll(lp.c);
        c = new ArrayList<>();
        c.addAll(lp.b);

        /*b = lp.c;
        c = lp.b;*/

        // lp.n db y van, az A alá fel kell venni, és a b-jét megnövelni
        for (int i = 0; i < lp.m; i++) {
            m++;
            b.add(0.0);
            List<Double> yI = new ArrayList<>();
            for (int j = 0; j < lp.m; j++) {
                if (i == j) {
                    yI.add(-1.0);
                } else {
                    yI.add(0.0);
                }
            }
            A.add(yI);
        }
    }
}
