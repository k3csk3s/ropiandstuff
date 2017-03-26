package hu.kecskesk.solver;

import hu.kecskesk.domain.DPErtekeles;
import hu.kecskesk.domain.DualisProgram;
import hu.kecskesk.domain.LPErtekeles;
import hu.kecskesk.domain.LinearisProgram;
import hu.kecskesk.helper.MatrixUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class LP megoldására
 *
 * Created by kkrisz on 2017.01.05..
 */
public class FourierMotzkinSolver {
    public LPErtekeles solveAndEvaluateLP(LinearisProgram lp, String lpId) {
        LinearisProgram lpNext = solveLP(lp);
        fourierOsztas(lpNext);
        //System.out.println(lpId + " eredmény:\n" + lpNext.toString());

        LPErtekeles ertekeles;
        ertekeles = ertekel(lpNext);

        //System.out.println(lpId + " eredmény érékelése:\n" + ertekeles.toString());

        return ertekeles;
    }

    public DPErtekeles solveAndEvaluateDP(DualisProgram dp, LPErtekeles lpEredmeny) {
        DPErtekeles ertekeles = new DPErtekeles(lpEredmeny);
        if (lpEredmeny.sikeres) {
            try {
                solveDP(dp);
                ertekeles.sikeres = true;
                ertekeles.yb = MatrixUtility.multiply(dp.x, dp.b);
                ertekeles.ertekeles = "DP megoldása: y=" + dp.x.toString();
            } catch (RuntimeException e) {
                ertekeles.sikeres = false;
                ertekeles.ertekeles = e.getMessage();
                return ertekeles;
            }

            System.out.println(ertekeles.toString());

            if (doubleSmaller(lpEredmeny.x1, ertekeles.yb)) {
                if (doubleSmaller(ertekeles.yb, lpEredmeny.xMin)) {
                    ertekeles.sikeres = false;
                    ertekeles.ertekeles += "A dp eredménye nem esik bele az lp eredményhalmazába";
                } else {
                    ertekeles.x1 = ertekeles.yb;
                    ertekeles.ertekeles += "A dp eredménye lefelé korrigálta lp eredményét, mert a határok közé esett";
                }
            } else if (doubleSmaller(ertekeles.yb, lpEredmeny.x1)) {
                if (doubleSmaller(lpEredmeny.xMax, ertekeles.yb)) {
                    ertekeles.sikeres = false;
                    ertekeles.ertekeles += "A dp eredménye nem esik bele az lp eredményhalmazába";
                } else {
                    ertekeles.x1 = ertekeles.yb;
                    ertekeles.ertekeles += "A dp eredménye felfelé korrigálta lp eredményét, mert a határok közé esett";
                }
            } else {
                ertekeles.x1 = ertekeles.yb;
                ertekeles.ertekeles += "A dp eredménye megerősítette lp eredményét.";
            }
        }
        return ertekeles;
    }

    private void solveDP(DualisProgram dp) {
        if (dp.m < dp.n) {
            throw new RuntimeException("aluldefiniált egyenletrendszer");
        }

        // diagonális alak
        for (dp.idx=0; dp.idx != dp.n; dp.idx++) {
            gauss(dp);
        }

        // extra sorok törlése
        for (int i = dp.m - 1; i != dp.n - 1; i--) {
            dp.A.remove(i);
            if (!doubleEquals(dp.b.get(i), 0.0)) {
                throw new RuntimeException("nemnull = null egyenlet");
            }
            dp.b.remove(i);
        }

        System.out.println("Gauss után:\n" + dp.toString());

        dp.x.addAll(dp.b);
    }

    private void gauss(DualisProgram dp) {
        // sort cserélünk
        if (doubleEquals(dp.A.get(dp.idx).get(dp.idx), 0.0)) {
            Integer nonZeroBelow = nonZeroBelow(dp);
            if (nonZeroBelow == null) {
                throw new RuntimeException("nincs mire cserélni");
            } else {
                List<Double> newIdxRow = new ArrayList<>();
                List<Double> newNonZeroBelowRow = new ArrayList<>();
                newNonZeroBelowRow.addAll(dp.A.get(nonZeroBelow));
                newIdxRow.addAll(dp.A.get(dp.idx));

                dp.A.set(nonZeroBelow, newIdxRow);
                dp.A.set(dp.idx, newNonZeroBelowRow);
            }
        }

        // kiegyezzük az oszlopot
        gaussOsztas(dp);

        // kivonogatjuk a sorokat
        for (int rowIdx = 0; rowIdx < dp.m; rowIdx++) {
            if (rowIdx != dp.idx) {
                if (!doubleEquals(dp.A.get(rowIdx).get(dp.idx), 0.0)) {
                    for (int colIdx = 0; colIdx < dp.n; colIdx++) {
                        dp.A.get(rowIdx).set(colIdx, dp.A.get(rowIdx).get(colIdx) - dp.A.get(dp.idx).get(colIdx));
                    }
                }
            }
        }
    }

    private Integer nonZeroBelow(DualisProgram dp) {
        for (int i = dp.idx + 1; i < dp.m; i++) {
            if (!doubleEquals(dp.A.get(i).get(dp.idx), 0.0)) {
                return i;
            }
        }

        return null;
    }

    private void sortToPlusMinusZero(LinearisProgram dp,
                                     List<List<Double>> aZero, List<List<Double>> aPlus, List<List<Double>> aMinus,
                                     List<Double> bZero, List<Double> bPlus, List<Double> bMinus) {
        for (int m = 0; m < dp.m; m++) {
            if (doubleEquals(dp.A.get(m).get(0), 0)) {
                aZero.add(dp.A.get(m).subList(1, dp.n));
                bZero.add(dp.b.get(m));
            } else if (doubleEquals(dp.A.get(m).get(0),1)) {
                aPlus.add(dp.A.get(m).subList(1, dp.n));
                bPlus.add(dp.b.get(m));
            } else if (doubleEquals(dp.A.get(m).get(0),-1)) {
                aMinus.add(dp.A.get(m).subList(1, dp.n));
                bMinus.add(dp.b.get(m));
            } else {
                throw new RuntimeException("hiba a FMben, érték: " + dp.A.get(m).get(0));
            }
        }
    }

    private LinearisProgram solveLP(LinearisProgram lp) {
        if (lp.n > 1) {
            LinearisProgram lpNext = fourierMotzin(lp);
            while (lpNext.n > 1) {
                lpNext = fourierMotzin(lpNext);
            }
            return lpNext;
        } else {
            return lp;
        }
    }

    private LPErtekeles ertekel(LinearisProgram lpNext) {
        List<List<Double>> aZero = new ArrayList<>();
        List<List<Double>> aPlus = new ArrayList<>();
        List<List<Double>> aMinus = new ArrayList<>();

        List<Double> bZero = new ArrayList<>();
        List<Double> bPlus = new ArrayList<>();
        List<Double> bMinus = new ArrayList<>();

        sortToPlusMinusZero(lpNext, aZero, aPlus, aMinus, bZero, bPlus, bMinus);

        if (bZero.stream().anyMatch(aDouble -> doubleSmaller(aDouble, 0.0))) {
            return new LPErtekeles("bZeroban vna negativ tag, nem megoldható", false);
        }

        if (aPlus.isEmpty() && aMinus.isEmpty()) {
            return new LPErtekeles("aPlus aMinusz üres, megoldható", 200000.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        if (aPlus.isEmpty()) {

            Optional<Double> oMax = bMinus.stream().max((o1, o2) -> o1.compareTo(o2) * -1);
            if (!oMax.isPresent()) {
                throw new RuntimeException("Van bMinus de nem létezik maximuma.");
            }
            return new LPErtekeles("aPlus üres, alsó korlát "+ oMax.get() + ", megoldható",
                    200000.0, oMax.get(), Double.POSITIVE_INFINITY);
        }

        if (aMinus.isEmpty()) {
            Optional<Double> oMin = bPlus.stream().min(Double::compareTo);
            if (!oMin.isPresent()) {
                throw new RuntimeException("Van bPlus de nem létezik minimuma.");
            }

            return new LPErtekeles("aMinusz üres, felső korlát "+ oMin.get() + ", megoldható", oMin.get(),
                     Double.NEGATIVE_INFINITY, oMin.get());
        }

        if (bPlus.isEmpty() || bMinus.isEmpty()) {
            throw new RuntimeException("bPlus bMinusz üres, ??? talán elhagytunk egy returnt?");
        }

        Optional<Double> oMin = bPlus.stream().min(Double::compareTo);
        Optional<Double> oMax = bMinus.stream().max((o1, o2) -> o1.compareTo(o2) * -1);

        if (!oMin.isPresent() || !oMax.isPresent()) {
            throw new RuntimeException("hiba a kiértékelésben: A min vagy max érték nem létezik!");
        }

        double min = oMin.get();
        double max = oMax.get();

        String legkisebbBPlus = "a legkisebb bPlusz: " + min;
        String legnagyobbBMinus = ", mint a legnagyobb bMinusz negáltja: " + -max;

        if (!doubleSmaller(min, -max)) {
            String ertekeles = legkisebbBPlus + " nagyobb" + legnagyobbBMinus + ". megoldható.";
            return new LPErtekeles(ertekeles, min, min, -max);
        } else {
            return new LPErtekeles(legkisebbBPlus + " kisebb" + legnagyobbBMinus + ". nem megoldható.", false);
        }
    }

    private LinearisProgram fourierMotzin(LinearisProgram lp) {
        //System.out.println("FM " + lp.n + "változós:\n" + lp.toString());

        fourierOsztas(lp);

        //System.out.println("fourierOsztas utan:\n" + lp.toString());

        List<List<Double>> aZero = new ArrayList<>();
        List<List<Double>> aPlus = new ArrayList<>();
        List<List<Double>> aMinus = new ArrayList<>();


        List<Double> bZero = new ArrayList<>();
        List<Double> bPlus = new ArrayList<>();
        List<Double> bMinus = new ArrayList<>();


        sortToPlusMinusZero(lp, aZero, aPlus, aMinus, bZero, bPlus, bMinus);

        List<List<Double>> aAdded = new ArrayList<>();
        List<Double> bAdded = new ArrayList<>();

        for (List<Double> rowInPlus : aPlus) {
            for (List<Double> rowInMinus : aMinus) {
                List<Double> added = IntStream.range(0, rowInPlus.size())
                        .mapToObj(i -> rowInPlus.get(i) + rowInMinus.get(i))
                        .collect(Collectors.toList());

                aAdded.add(added);


                Double added_b = bPlus.get(aPlus.indexOf(rowInPlus)) + bMinus.get(aMinus.indexOf(rowInMinus));
                bAdded.add(added_b);
            }
        }

        LinearisProgram lpNext = new LinearisProgram(lp.n - 1, aAdded.size() + aZero.size());
        lpNext.A.addAll(aAdded);
        lpNext.A.addAll(aZero);
        lpNext.b.addAll(bAdded);
        lpNext.b.addAll(bZero);

        lpNext.c = lp.c;

        lpNext.m = lpNext.A.size();
        lpNext.n = lpNext.A.isEmpty() ? 0 : lpNext.A.get(0).size();

        return lpNext;
    }

    private boolean doubleEquals(double d0, double d1) {
        double epsilon = 0.0001;
        return d0 > d1 - epsilon && d0 < d1 + epsilon;
    }

    private boolean doubleSmaller(double d0, double d1) {
        double epsilon = 0.0001;
        return d0 + epsilon < d1;
    }

    private void fourierOsztas(LinearisProgram lp) {
        for (int m = 0; m < lp.m; m++) {
            double divider = Math.abs(lp.A.get(m).get(0));
            if (divider != 0) {
                lp.b.set(m, lp.b.get(m) / divider);
            }
            for (int n = 0; n < lp.n; n++) {
                if (divider != 0) {
                    lp.A.get(m).set(n, lp.A.get(m).get(n) / divider);
                }
            }
        }
    }

    private void gaussOsztas(DualisProgram dp) {
        for (int m = 0; m < dp.m; m++) {
            double divider = dp.A.get(m).get(dp.idx);

            if (divider != 0) {
                dp.b.set(m, dp.b.get(m) / divider);
            } else {
                // TODO
            }
            for (int n = dp.idx; n < dp.n; n++) {
                if (divider != 0) {
                    dp.A.get(m).set(n, dp.A.get(m).get(n) / divider);
                }
            }
        }
    }
}
